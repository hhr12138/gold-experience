package basic.blog.goldexperience.loader;

import basic.blog.goldexperience.configuration.AppConfiguration;
import basic.blog.goldexperience.entity.Content;
import basic.blog.goldexperience.entity.GoldRequest;
import common.entity.valhalla.loader.Impl.AbstractLoader;
import common.entity.valhalla.vo.PreHookResp;
import common.entity.valhalla.vo.RestResponse;
import common.log.scholar_of_yore.service.LogTemplate;
import common.storage.king.entity.Comment;
import common.storage.king.service.GoldClient;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommentLoader extends AbstractLoader {
    public static final String NAME = "CommentLoader";
    private List<Long> blogIds;
    private LogTemplate logTemplate;
    private GoldRequest request;
    private GoldClient goldClient;
    private String psm = AppConfiguration.PSM;
    private Map<Long, List<Comment>> blogIdToCommentsMap;
    private int commentSize;
    private int start;

    public CommentLoader(ConcurrentHashMap<String, Object> map, LogTemplate logTemplate, GoldRequest request, GoldClient goldClient, String... preLoader) {
        super(map, NAME, preLoader);
        this.logTemplate = logTemplate;
        this.request = request;
        this.psm = psm;
    }

    @Override
    public PreHookResp preHook() {
        List<Content> contents = request.getContents();
        if(contents == null || contents.size() == 0){
            String msg = "[BlogLoader] err: err=contents is empty";
            logTemplate.error(request.getRequestId(),psm,msg);
            return new PreHookResp(false,new Exception(msg));
        }
        List<Long> blogIds = new ArrayList<>();
        for(Content content: contents){
            blogIds.add(content.getId());
            this.commentSize = content.getCommentSize();
            this.start = content.getStart();
        }
        this.blogIds = blogIds;
        return PreHookResp.success();
    }

    @Override
    public Exception loadData() {
        if(this.blogIds == null) return null;
        String requestId = request.getRequestId();
        try{
            RestResponse<Map<Long, List<common.storage.king.entity.Comment>>> resp = goldClient.getCommentByBlogIds(blogIds, start, commentSize);
            if(!resp.isSuccess()){
                String errMsg = "[CommentLoader] loadData error: err="+resp.getMsg();
                logTemplate.error(requestId,psm,errMsg);
                throw new Exception(errMsg);
            }
            this.blogIdToCommentsMap = resp.getData();
            return null;
        } catch (Exception e){
            return e;
        }
    }

    @Override
    public Exception postHook() {
        Map<Long,List<Comment>> commentMap = new HashMap<>();
        BeanUtils.copyProperties(this.blogIdToCommentsMap,commentMap);
        this.map.put(this.getName(),commentMap);
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
