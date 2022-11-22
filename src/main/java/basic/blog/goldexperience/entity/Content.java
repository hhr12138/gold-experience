package basic.blog.goldexperience.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private Long id;
    private Long styleId;
    private int commentSize;
    private int start;
    private List<Long> componentIds;
}
