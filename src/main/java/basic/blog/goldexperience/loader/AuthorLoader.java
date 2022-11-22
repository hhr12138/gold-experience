package basic.blog.goldexperience.loader;

import basic.blog.goldexperience.configuration.AppConfiguration;
import basic.blog.goldexperience.entity.Blog;
import basic.blog.goldexperience.entity.GoldRequest;
import basic.blog.goldexperience.entity.User;
import common.entity.valhalla.loader.Impl.AbstractLoader;
import common.entity.valhalla.vo.PreHookResp;
import common.entity.valhalla.vo.RestResponse;
import common.log.scholar_of_yore.service.LogTemplate;
import common.storage.king.service.GoldClient;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthorLoader extends AbstractLoader {
    public static String NAME = "AuthorLoader";
    private GoldRequest request;
    private LogTemplate logTemplate;
    private String psm = AppConfiguration.PSM;
    private GoldClient goldClient;
    private List<Long> authorIds;
    private List<common.storage.king.entity.User> authors;
    private Map<Long, Long> authorToBlog;

    public AuthorLoader(ConcurrentHashMap<String, Object> map, GoldRequest request, LogTemplate logTemplate, GoldClient goldClient, String... preLoader) {
        super(map, NAME, preLoader);
        this.authorToBlog = new HashMap<>();
        this.request = request;
        this.logTemplate = logTemplate;
        this.goldClient = goldClient;
    }

    @Override
    public PreHookResp preHook() {
        Object o = map.get(BlogLoader.NAME);
        Map<Long, Blog> blogMap = (Map<Long,Blog>)o;
        List<Long> authorIds = new ArrayList<>();
        for(Map.Entry<Long,Blog> blogEntry: blogMap.entrySet()){
            Blog blog = blogEntry.getValue();
            Long authorId = blog.getAuthorId();
            authorIds.add(authorId);
            authorToBlog.put(authorId,blogEntry.getKey());
        }
        this.authorIds = authorIds;
        return PreHookResp.success();
    }

    @Override
    public Exception loadData() {
        if(this.authorIds == null) return null;
        try{
            RestResponse<List<common.storage.king.entity.User>> resp =  goldClient.getUsersById(this.authorIds);
            if(!resp.isSuccess()){
                if(!resp.isSuccess()) throw new Exception(resp.getMsg());
            }
            this.authors = resp.getData();
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    public Exception postHook() {
        Map<Long, User> userMap = new HashMap<>();
        List<User> authors = new ArrayList<>();
        BeanUtils.copyProperties(this.authorIds,authors);
        for(User author : authors){
            Long id = author.getId();
            Long blogId = authorToBlog.get(id);
            userMap.put(blogId,author);
        }
        map.put(this.getName(),userMap);
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
