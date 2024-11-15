package autotradingAuthenticate.autotrading.board.bet.service;

import autotradingAuthenticate.autotrading.board.bet.entity.Bet;
import autotradingAuthenticate.autotrading.board.bet.entity.BetParticipation;
import autotradingAuthenticate.autotrading.board.bet.repository.BetParticipationRepository;
import autotradingAuthenticate.autotrading.board.bet.repository.BetRepository;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BetService {

    private final BetRepository betRepository;
    private final BetParticipationRepository betParticipationRepository;

    // 내기 참여
    public void participateInBet(Long betId, Member member) {
        Optional<Bet> optionalBet = betRepository.findById(betId);
        if (optionalBet.isPresent()) {
            Bet bet = optionalBet.get();
            if (!bet.isConfirmed()) {
                Optional<BetParticipation> existingParticipation = betParticipationRepository.findByBetAndMember(bet, member);

                // 이미 참여하지 않은 경우에만 참여 추가
                if (!existingParticipation.isPresent()) {
                    BetParticipation participation = new BetParticipation(bet, member, true);
                    bet.addParticipation(participation);
                    member.addParticipation(participation);

                    // BetParticipation을 저장
                    betParticipationRepository.save(participation);
                } else {
                    throw new IllegalStateException("Member already participating in this bet.");
                }
            } else {
                throw new IllegalStateException("Bet has already been confirmed.");
            }
        }
    }

    // 내기 참여 취소
    public void cancelParticipationInBet(Long betId, Member member) {
        Optional<Bet> optionalBet = betRepository.findById(betId);
        if (optionalBet.isPresent()) {
            Bet bet = optionalBet.get();
            if (!bet.isConfirmed()) {
                Optional<BetParticipation> participation = betParticipationRepository.findByBetAndMember(bet, member);
                if (participation.isPresent()) {
                    // BetParticipation을 삭제
                    betParticipationRepository.delete(participation.get());

                    // Bet의 participations 리스트에서도 제거
                    bet.getParticipations().remove(participation.get());
                    member.getBetParticipations().remove(participation.get());
                } else {
                    throw new IllegalStateException("Member is not participating in this bet.");
                }
            } else {
                throw new IllegalStateException("Bet has already been confirmed.");
            }
        }
    }

    // 내기 확정
    public void confirmBet(Long betId, Member creator) {
        Optional<Bet> optionalBet = betRepository.findById(betId);
        if (optionalBet.isPresent()) {
            Bet bet = optionalBet.get();
            if (bet.getAuthor().equals(creator.getUsername())) {
                bet.setConfirmed(true);
            } else {
                throw new IllegalStateException("Only the creator can confirm the bet.");
            }
        }
    }

    // 내기 생성
    public void createBet(Bet bet, Member creator) {
        // Bet의 생성자를 설정
        bet.setAuthor(creator.getUsername());

        // Bet 저장
        Bet savedBet = betRepository.save(bet);

        // BetParticipation 생성 및 저장 (내기 생성자는 기본적으로 참여자로 추가됨)
        BetParticipation participation = new BetParticipation(savedBet, creator, true);
        savedBet.addParticipation(participation);
        creator.addParticipation(participation);

        betParticipationRepository.save(participation);
    }

    // 내기 삭제
    public void deleteBet(Long betId, Member creator) {
        Optional<Bet> optionalBet = betRepository.findById(betId);
        if (optionalBet.isPresent()) {
            Bet bet = optionalBet.get();
            if (bet.getAuthor().equals(creator.getUsername())) {
                // 관련된 BetParticipation 모두 가져오기
                List<BetParticipation> participations = betParticipationRepository.findByBet(bet);

                // 각 BetParticipation에서 해당 Member의 participation 리스트에서 제거
                for (BetParticipation participation : participations) {
                    Member participant = participation.getMember();
                    participant.getBetParticipations().remove(participation);  // Member의 participations에서 제거
                }

                // 관련된 BetParticipation 모두 삭제
                betParticipationRepository.deleteAll(participations);

                // Bet 삭제
                betRepository.delete(bet);
            } else {
                throw new IllegalStateException("Only the creator can delete the bet.");
            }
        }
    }
}