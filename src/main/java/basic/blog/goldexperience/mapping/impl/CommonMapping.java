package basic.blog.goldexperience.mapping.impl;

import basic.blog.goldexperience.entity.Blog;
import basic.blog.goldexperience.entity.ContentItem;
import basic.blog.goldexperience.entity.GoldItem;
import basic.blog.goldexperience.entity.User;
import basic.blog.goldexperience.mapping.Mapping;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CommonMapping implements Mapping {
    @Override
    public ContentItem run(GoldItem item) {
        ContentItem contentItem = new ContentItem();
        Blog blog = item.getBlog();
        User author = item.getAuthor();
        if(author != null){
            blog.setAuthor(author);
            blog.setAuthorId(author.getId());
        }
        blog.setCollect(item.getCollectCnt());
        blog.setComments(item.getComments());
        blog.setCommentCnt(item.getCommentCnt());
        contentItem.setBlog(blog);
        return contentItem;
    }
}
