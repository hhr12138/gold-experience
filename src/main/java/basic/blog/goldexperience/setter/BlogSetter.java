package basic.blog.goldexperience.setter;

import basic.blog.goldexperience.entity.Blog;
import basic.blog.goldexperience.entity.GoldItem;
import basic.blog.goldexperience.loader.BlogLoader;
import common.entity.valhalla.setter.Setter;

import java.util.Map;

public class BlogSetter implements Setter<GoldItem> {
    @Override
    public void setToItem(Map<String, Object> attributes, GoldItem item) {
        Object o = attributes.get(BlogLoader.NAME);
        if(o == null) return;
        Map<Long, Blog> blogMap = (Map<Long, Blog>)o;
        Blog blog = blogMap.get(item.getId());
        item.setBlog(blog);
    }
}
