package autotradingAuthenticate.autotrading.board.bet.entity;

import autotradingAuthenticate.autotrading.board.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class BetParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bet_id", nullable = false)
    private Bet bet;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    private BetRole role; // CREATOR or PARTICIPANT

    @Enumerated(EnumType.STRING)
    private ParticipationStatus status; // INVITED, ACCEPTED, REJECTED, etc.

    public BetParticipation(Bet bet, Member member, BetRole role, ParticipationStatus status) {
        this.bet = bet;
        this.member = member;
        this.role = role;
        this.status = status;
    }

    public boolean isCreator() {
        return this.role == BetRole.CREATOR;
    }

    public void changeStatus(ParticipationStatus newStatus) {
        this.status = newStatus;
    }
}
