package basic.blog.goldexperience.setter;


import basic.blog.goldexperience.entity.Comment;
import basic.blog.goldexperience.entity.GoldItem;
import basic.blog.goldexperience.loader.CommentLoader;
import common.entity.valhalla.setter.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentSetter implements Setter<GoldItem> {
    @Override
    public void setToItem(Map<String, Object> attributes, GoldItem item) {
        Object o = attributes.get(CommentLoader.NAME);
        Map<Long, List<Comment>> commentsMap = (Map<Long, List<Comment>>) o;
        List<Comment> comments = commentsMap.getOrDefault(item.getId(), new ArrayList<>());
        item.setComments(comments);
    }
}
