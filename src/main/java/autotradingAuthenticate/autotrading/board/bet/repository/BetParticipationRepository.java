package autotradingAuthenticate.autotrading.board.bet.repository;

import autotradingAuthenticate.autotrading.board.bet.entity.Bet;
import autotradingAuthenticate.autotrading.board.bet.entity.BetParticipation;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BetParticipationRepository extends JpaRepository<BetParticipation, Long> {
    List<BetParticipation> findByBetId(Long betId);

    // 특정 참여자 조회
    Optional<BetParticipation> findByBetIdAndMemberId(Long betId, Long memberId);
}
