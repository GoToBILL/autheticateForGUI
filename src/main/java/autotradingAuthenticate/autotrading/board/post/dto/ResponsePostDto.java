package autotradingAuthenticate.autotrading.board.post.dto;

import autotradingAuthenticate.autotrading.board.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ResponsePostDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdDate;

    // 날짜 포맷터 정의 (년/월/일 시간:분 형식)
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public ResponsePostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor().getUsername();
        this.createdDate = post.getCreatedDate().format(DATE_FORMATTER);
    }
}