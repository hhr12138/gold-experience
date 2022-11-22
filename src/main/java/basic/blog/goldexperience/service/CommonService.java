package basic.blog.goldexperience.service;

import basic.blog.goldexperience.configuration.AppConfiguration;
import basic.blog.goldexperience.entity.*;
import basic.blog.goldexperience.factory.MappingFactory;
import basic.blog.goldexperience.mapping.Mapping;
import common.entity.valhalla.bo.LoaderHandlerResp;
import common.entity.valhalla.handler.LoaderHandler;
import common.entity.valhalla.handler.SetterHandler;
import common.entity.valhalla.vo.RestResponse;
import common.log.scholar_of_yore.service.LogTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService {
    @Resource
    private LoaderHandler<GoldRequest> loaderHandler;
    @Resource
    private SetterHandler<GoldItem> setterHandler;
    @Resource
    private LogTemplate logTemplate;
    private String psm = AppConfiguration.PSM;

    public RestResponse<GoldResp> pack(GoldRequest request) {
        LoaderHandlerResp resp = loaderHandler.run(request);
        List<Exception> exceptions = resp.getExceptions();
        if(exceptions != null && exceptions.size() != 0){
            for(Exception e : exceptions){
                logTemplate.error(request.getRequestId(),psm,e.getMessage());
            }
            return RestResponse.fail("[CommonService] pack error");
        }
        GoldResp goldResp = new GoldResp();
        goldResp.setRequestId(request.getRequestId());
        List<ContentItem> goldItems = new ArrayList<>();
        for(int i = 0; i < request.getContents().size(); i++){
            GoldItem goldItem = GoldItem.emptyGoldItem();
            setterHandler.run(resp.getAttributes(),goldItem);
            MappingFactory mappingFactory = MappingFactory.getFactory();
            Mapping mapping = mappingFactory.getMapping();
            ContentItem contentItem = mapping.run(goldItem);
            goldItems.add(contentItem);
        }
        goldResp.setItems(goldItems);
        return RestResponse.success(goldResp);
    }
}
