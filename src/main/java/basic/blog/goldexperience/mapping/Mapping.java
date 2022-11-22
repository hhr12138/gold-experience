package basic.blog.goldexperience.mapping;

import basic.blog.goldexperience.entity.ContentItem;
import basic.blog.goldexperience.entity.GoldItem;
import basic.blog.goldexperience.entity.GoldResp;

public interface Mapping {
    ContentItem run(GoldItem item);
}
