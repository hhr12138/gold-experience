package basic.blog.goldexperience.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Blog {
    private Long id;
    private Long authorId;
    private User author;
    private List<Comment> comments;
    private Long like;
    private Long collect;
    private String title;
    private String subTitle;
    private Long contentId;
    private Long commentCnt;
}
