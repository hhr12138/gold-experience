package basic.blog.goldexperience.handler;

import basic.blog.goldexperience.configuration.AppConfiguration;
import basic.blog.goldexperience.entity.GoldRequest;
import basic.blog.goldexperience.loader.AuthorLoader;
import basic.blog.goldexperience.loader.BlogLoader;
import basic.blog.goldexperience.loader.CollectCntLoader;
import basic.blog.goldexperience.loader.CommentLoader;
import common.entity.valhalla.handler.LoaderHandler;
import common.entity.valhalla.loader.BasicLoader;
import common.log.scholar_of_yore.service.LogTemplate;
import common.storage.king.service.GoldClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class CommonLoaderHandler extends LoaderHandler<GoldRequest> {
    @Resource
    private GoldClient goldClient;
    @Resource
    private LogTemplate logTemplate;
    public CommonLoaderHandler() {
        super(AppConfiguration.PSM);
    }

    @Override
    protected List<BasicLoader> getAllLoaders(ConcurrentHashMap<String, Object> attributes, GoldRequest request) {
        List<BasicLoader> loaders = new ArrayList<>();
        loaders.add(new BlogLoader(attributes,request,goldClient,logTemplate));
        loaders.add(new CollectCntLoader(attributes,request,logTemplate,goldClient));
        loaders.add(new CommentLoader(attributes,logTemplate,request,goldClient));

        loaders.add(new AuthorLoader(attributes,request,logTemplate,goldClient,BlogLoader.NAME));
        return null;
    }
}
