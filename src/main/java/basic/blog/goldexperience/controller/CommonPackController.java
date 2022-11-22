package basic.blog.goldexperience.controller;

import basic.blog.goldexperience.entity.CommonResp;
import basic.blog.goldexperience.entity.GoldRequest;
import basic.blog.goldexperience.entity.GoldResp;
import basic.blog.goldexperience.service.CommonService;
import common.entity.valhalla.vo.RestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CommonPackController {
    @Resource
    private CommonService commonService;
    @GetMapping("/pack")
    public RestResponse<GoldResp> pack(GoldRequest request){
        if(request.getContents() == null || request.getContents().size() == 0) return RestResponse.fail("contents is empty");
        return commonService.pack(request);
    }
}
