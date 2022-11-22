package basic.blog.goldexperience.loader;

import basic.blog.goldexperience.configuration.AppConfiguration;
import basic.blog.goldexperience.entity.Blog;
import basic.blog.goldexperience.entity.Content;
import basic.blog.goldexperience.entity.GoldRequest;
import common.entity.valhalla.loader.BasicLoader;
import common.entity.valhalla.loader.Impl.AbstractLoader;
import common.entity.valhalla.vo.PreHookResp;
import common.entity.valhalla.vo.RestResponse;
import common.log.scholar_of_yore.service.LogTemplate;
import common.storage.king.service.GoldClient;
import org.springframework.beans.BeanUtils;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlogLoader extends AbstractLoader {
    public static String NAME = "BlogLoader";
    private GoldRequest request;
    private LogTemplate logTemplate;
    private String psm;
    private GoldClient goldClient;
    private List<common.storage.king.entity.Blog> blogs;
    private List<Long> blogIds;
    public BlogLoader(ConcurrentHashMap<String, Object> map, GoldRequest request, GoldClient goldClient, LogTemplate logTemplate, String ...preLoader){
        super(map,NAME, preLoader);
        this.request = request;
        this.logTemplate = logTemplate;
        this.goldClient = goldClient;
        this.psm = AppConfiguration.PSM;
    }
    @Override
    public PreHookResp preHook() {
        if(request.getContents() == null || request.getContents().size() == 0){
            String msg = "[BlogLoader] err: err=contents is empty";
            logTemplate.error(request.getRequestId(),psm,msg);
            return new PreHookResp(false,new Exception(msg));
        }
        List<Content> contents = request.getContents();
        List<Long> blogIds = new ArrayList<>();
        for(Content content: contents){
            blogIds.add(content.getId());
        }
        this.blogIds = blogIds;
        return PreHookResp.success();
    }

    @Override
    public Exception loadData() {
        if(this.blogIds == null) return null;
        try{
            RestResponse<List<common.storage.king.entity.Blog>> resp = goldClient.getBlogsById(blogIds);
            if(!resp.isSuccess()) throw new Exception(resp.getMsg());
            this.blogs = resp.getData();
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    public Exception postHook() {
        Map<Long, Blog> blogMap = new HashMap<>();
        List<Blog> blogs = new ArrayList<>();
        BeanUtils.copyProperties(this.blogIds,blogMap);
        for(Blog blog: blogs){
            blogMap.put(blog.getId(),blog);
        }
        map.put(this.getName(),blogMap);
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public long getExeTime() {
        return 0;
    }

    @Override
    public List<String> getPreLoader() {
        return null;
    }
}
