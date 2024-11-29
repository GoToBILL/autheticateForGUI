package autotradingAuthenticate.autotrading.board.bet.dto;

import autotradingAuthenticate.autotrading.board.bet.entity.Bet;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BetRequestDto {
    private String title;
    private String description;
    private LocalDate endDate;

    public Bet toBet(Member creator) {
        return new Bet(this.title, this.description, this.endDate, creator.getUsername());
    }
}
