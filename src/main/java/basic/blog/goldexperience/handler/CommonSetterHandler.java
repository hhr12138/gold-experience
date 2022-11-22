package basic.blog.goldexperience.handler;

import basic.blog.goldexperience.configuration.AppConfiguration;
import basic.blog.goldexperience.entity.Blog;
import basic.blog.goldexperience.entity.GoldItem;
import basic.blog.goldexperience.setter.AuthorSetter;
import basic.blog.goldexperience.setter.BlogSetter;
import basic.blog.goldexperience.setter.CollectCntSetter;
import basic.blog.goldexperience.setter.CommentSetter;
import common.entity.valhalla.handler.SetterHandler;
import common.entity.valhalla.setter.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommonSetterHandler extends SetterHandler<GoldItem> {
    public CommonSetterHandler() {
        super(AppConfiguration.PSM);
    }

    @Override
    protected List<Setter<GoldItem>> getSetters() {
        List<Setter<GoldItem>> setters = new ArrayList<>();
        setters.add(new AuthorSetter());
        setters.add(new BlogSetter());
        setters.add(new CollectCntSetter());
        setters.add(new CommentSetter());
        return setters;
    }
}
