package basic.blog.goldexperience.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoldResp {
    private String requestId;
    private List<ContentItem> items;
}
