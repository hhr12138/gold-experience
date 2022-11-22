package basic.blog.goldexperience.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoldItem {
    private Long id;
    private Blog blog;
    private User author;
    private Long CollectCnt;
    private List<Comment> comments;
    private Long commentCnt;

    public static GoldItem emptyGoldItem() {
        GoldItem goldItem = new GoldItem();
        goldItem.setBlog(new Blog());
        goldItem.setAuthor(new User());
        goldItem.setCollectCnt(0L);
        goldItem.setComments(new ArrayList<>());
        return goldItem;
    }
}
