package autotradingAuthenticate.autotrading.board.bet.service;

import autotradingAuthenticate.autotrading.board.bet.entity.Bet;
import autotradingAuthenticate.autotrading.board.bet.entity.BetParticipation;
import autotradingAuthenticate.autotrading.board.bet.entity.BetRole;
import autotradingAuthenticate.autotrading.board.bet.entity.ParticipationStatus;
import autotradingAuthenticate.autotrading.board.bet.repository.BetParticipationRepository;
import autotradingAuthenticate.autotrading.board.bet.repository.BetRepository;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import autotradingAuthenticate.autotrading.exception.customException.BadRequestException;
import autotradingAuthenticate.autotrading.exception.customException.NotFoundException;
import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
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
        Bet bet = findBetById(betId);
        if (bet.isConfirmed()) {
            throw new BadRequestException(ErrorMessage.BET_ALREADY_CONFIRMED);
        }

        boolean alreadyParticipating = betParticipationRepository.findByBetIdAndMemberId(betId, member.getId()).isPresent();
        if (alreadyParticipating) {
            throw new BadRequestException(ErrorMessage.BET_MEMBER_ALREADY_PARTICIPATING);
        }

        BetParticipation participation = new BetParticipation(bet, member, BetRole.PARTICIPANT, ParticipationStatus.ACCEPTED);
        bet.addParticipation(participation);
        member.addParticipation(participation);

        betParticipationRepository.save(participation);
    }

    // 내기 참여 취소
    public void cancelParticipationInBet(Long betId, Member member) {
        Bet bet = findBetById(betId);

        if (bet.isConfirmed()) {
            throw new BadRequestException(ErrorMessage.BET_PARTICIPATION_CANNOT_BE_REMOVED);
        }

        BetParticipation participation = betParticipationRepository.findByBetIdAndMemberId(betId, member.getId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BET_PARTICIPATION_NOT_FOUND));

        betParticipationRepository.delete(participation);
        bet.getParticipations().remove(participation);
        member.getBetParticipations().remove(participation);
    }

    // 내기 확정
    public void confirmBet(Long betId, Member creator) {
        Bet bet = findBetById(betId);

        if (!bet.getAuthor().equals(creator.getUsername())) {
            throw new BadRequestException(ErrorMessage.BET_CREATOR_ONLY);
        }
        bet.setConfirmed(true);
    }

    // 내기 생성
    public void createBet(Bet bet, Member creator) {
        Bet savedBet = betRepository.save(bet);

        BetParticipation participation = new BetParticipation(savedBet, creator, BetRole.CREATOR, ParticipationStatus.ACCEPTED);
        savedBet.addParticipation(participation);
        creator.addParticipation(participation);

        betParticipationRepository.save(participation);
    }

    // 내기 삭제
    public void deleteBet(Long betId, Member creator) {
        Bet bet = findBetById(betId);

        if (!bet.getAuthor().equals(creator.getUsername())) {
            throw new BadRequestException(ErrorMessage.BET_DELETION_NOT_ALLOWED);
        }

        List<BetParticipation> participations = betParticipationRepository.findByBetId(bet.getId());

        participations.forEach(participation -> {
            Member participant = participation.getMember();
            participant.getBetParticipations().remove(participation);
        });

        betParticipationRepository.deleteAll(participations);
        betRepository.delete(bet);
    }

    // 내기 조회 (공통 로직으로 분리)
    @Transactional(readOnly = true)
    public Bet findBetById(Long betId) {
        return betRepository.findById(betId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BET_NOT_FOUND));
    }
}
