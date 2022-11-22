package basic.blog.goldexperience.setter;

import basic.blog.goldexperience.entity.GoldItem;
import basic.blog.goldexperience.loader.CommentLoader;
import common.entity.valhalla.setter.Setter;

import java.util.Map;

public class CommentCntSetter implements Setter<GoldItem> {
    @Override
    public void setToItem(Map<String, Object> attributes, GoldItem item) {
        Object o = attributes.get(CommentLoader.NAME);
        if(o == null) return;
        Map<Long, Long> commentCntMap = (Map<Long, Long>) o;
        item.setCommentCnt(commentCntMap.getOrDefault(item.getId(),0L));
    }
}
