package basic.blog.goldexperience.setter;

import basic.blog.goldexperience.entity.GoldItem;
import basic.blog.goldexperience.entity.User;
import basic.blog.goldexperience.loader.AuthorLoader;
import common.entity.valhalla.setter.Setter;

import java.util.Map;

public class AuthorSetter implements Setter<GoldItem> {
    @Override
    public void setToItem(Map<String, Object> attributes, GoldItem item) {
        Object o = attributes.get(AuthorLoader.NAME);
        if(o == null) return;
        Map<Long, User> userMap = (Map<Long, User>)o;
        User user = userMap.get(item.getId());
        item.setAuthor(user);
    }
}
