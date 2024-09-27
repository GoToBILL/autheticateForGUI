package autotradingAuthenticate.autotrading.board.post.dto;

import autotradingAuthenticate.autotrading.board.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PostDto {
    private String title;
    private String content;
}