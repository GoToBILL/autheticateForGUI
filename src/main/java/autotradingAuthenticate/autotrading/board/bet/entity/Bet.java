package autotradingAuthenticate.autotrading.board.bet.entity;

import autotradingAuthenticate.autotrading.board.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Bet {

    @Id @Column(name = "bet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate endDate;
    private String author; // 내기 생성자

    private boolean isConfirmed = false; // 내기 확정 여부

    @OneToMany(mappedBy = "bet", fetch = FetchType.LAZY)
    private List<BetParticipation> participations;


    // 생성자
    public Bet(String title, String description, LocalDate endDate, String creator) {
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.author = creator;
    }

    public void addParticipation(BetParticipation betParticipation) {
        participations.add(betParticipation);
        betParticipation.setBet(this);
    }
}