package autotradingAuthenticate.autotrading.board.bet.repository;

import autotradingAuthenticate.autotrading.board.bet.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
}