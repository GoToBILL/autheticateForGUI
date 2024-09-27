package autotradingAuthenticate.autotrading.board.post.entity;

import autotradingAuthenticate.autotrading.auth.entity.BaseTimeEntity;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {
    @Id @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY 로딩 설정
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member author;

    public Post(String title, String content, Member author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}