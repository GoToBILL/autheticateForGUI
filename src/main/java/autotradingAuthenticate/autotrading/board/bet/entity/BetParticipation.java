package autotradingAuthenticate.autotrading.board.bet.entity;

import autotradingAuthenticate.autotrading.board.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class BetParticipation {

    @Id @Column(name = "bet_participation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bet_id")
    private Bet bet;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean isParticipating; // 참여 여부
    // 생성자
    public BetParticipation(Bet bet, Member member, boolean isParticipating) {
        this.bet = bet;
        this.member = member;
        this.isParticipating = isParticipating;
    }
}