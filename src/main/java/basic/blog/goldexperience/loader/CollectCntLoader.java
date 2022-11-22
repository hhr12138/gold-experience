package basic.blog.goldexperience.loader;

import basic.blog.goldexperience.configuration.AppConfiguration;
import basic.blog.goldexperience.entity.Content;
import basic.blog.goldexperience.entity.GoldRequest;
import common.entity.valhalla.loader.BasicLoader;
import common.entity.valhalla.loader.Impl.AbstractLoader;
import common.entity.valhalla.vo.PreHookResp;
import common.entity.valhalla.vo.RestResponse;
import common.log.scholar_of_yore.service.LogTemplate;
import common.storage.king.service.GoldClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//收藏数
public class CollectCntLoader extends AbstractLoader {
    public static String NAME = "CollectCntLoader";
    private GoldRequest request;
    private LogTemplate logTemplate;
    private String psm = AppConfiguration.PSM;
    private GoldClient goldClient;
    private List<Long> blogIds;
    private Map<Long, Long> blogIdToCollectCntMap;

    public CollectCntLoader(ConcurrentHashMap<String, Object> map, GoldRequest request, LogTemplate logTemplate, GoldClient goldClient, String... preLoader) {
        super(map, NAME, preLoader);
        this.request = request;
        this.logTemplate = logTemplate;
        this.goldClient = goldClient;
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
        }
        this.blogIds = blogIds;
        return PreHookResp.success();
    }

    @Override
    public Exception loadData() {
        if(this.blogIds == null) return null;
        String requestId = request.getRequestId();
        try{
            RestResponse<Map<Long, Long>>resp = goldClient.getCollectCntByBlogIds(blogIds);
            if(!resp.isSuccess()){
                String errMsg = "[CollectCntLoader]loadData error: err="+resp.getMsg();
                logTemplate.error(requestId,psm,errMsg);
                throw new Exception(errMsg);
            }
            Map<Long, Long> data = resp.getData();
            this.blogIdToCollectCntMap = data;
            return null;
        } catch (Exception e){
            return e;
        }
    }

    @Override
    public Exception postHook() {
        map.put(this.getName(),this.blogIdToCollectCntMap);
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
