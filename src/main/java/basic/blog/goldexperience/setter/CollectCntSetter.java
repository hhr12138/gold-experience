package basic.blog.goldexperience.setter;

import basic.blog.goldexperience.entity.GoldItem;
import basic.blog.goldexperience.loader.CollectCntLoader;
import common.entity.valhalla.setter.Setter;

import java.util.Map;

public class CollectCntSetter implements Setter<GoldItem> {
    @Override
    public void setToItem(Map<String, Object> attributes, GoldItem item) {
        Object o = attributes.get(CollectCntLoader.NAME);
        if(o == null) return;
        Map<Long,Long> collectCntMap = (Map<Long, Long>)o;
        Long collectCnt = collectCntMap.getOrDefault(item.getId(), 0L);
        item.setCollectCnt(collectCnt);
    }
}
