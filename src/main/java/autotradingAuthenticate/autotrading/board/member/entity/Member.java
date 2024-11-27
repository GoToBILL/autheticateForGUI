package autotradingAuthenticate.autotrading.board.member.entity;

import autotradingAuthenticate.autotrading.board.bet.entity.Bet;
import autotradingAuthenticate.autotrading.board.bet.entity.BetParticipation;
import autotradingAuthenticate.autotrading.board.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member {
    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 카카오 사용자 ID (OAuth 사용자만 사용)
    private String kakaoId;

    // 카카오 프로필 정보
    private String nickname;
    private String profileImageUrl;


    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

//     사용자가 참여한 내기 리스트 (내기 참여자)
    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<BetParticipation> betParticipations;

    public Member(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    // 카카오 사용자 생성자
    public Member(String kakaoId, String email, String nickname, String profileImageUrl, Role role) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    public void addParticipation(BetParticipation betParticipation) {
        betParticipations.add(betParticipation);
        betParticipation.setMember(this);
    }
}