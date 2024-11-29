package autotradingAuthenticate.autotrading.board.bet.controller;

import autotradingAuthenticate.autotrading.board.bet.dto.BetRequestDto;
import autotradingAuthenticate.autotrading.board.bet.entity.Bet;
import autotradingAuthenticate.autotrading.board.bet.service.BetService;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import autotradingAuthenticate.autotrading.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bet")
@RequiredArgsConstructor
public class BetController {

    private final BetService betService;
    private final MemberService memberService;

    // 내기 참여
    @PostMapping("/{betId}/participate")
    public ResponseEntity<String> participateInBet(@PathVariable Long betId, @AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findByUsername(userDetails.getUsername()); // 인증된 사용자 정보로 멤버 조회
        betService.participateInBet(betId, member);
        return ResponseEntity.ok("참여 완료");
    }

    // 내기 참여 취소
    @PostMapping("/{betId}/cancel")
    public ResponseEntity<String> cancelParticipationInBet(@PathVariable Long betId, @AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findByUsername(userDetails.getUsername()); // 인증된 사용자 정보로 멤버 조회
        betService.cancelParticipationInBet(betId, member);
        return ResponseEntity.ok("참여 취소 완료");
    }

    // 내기 확정
    @PostMapping("/{betId}/confirm")
    public ResponseEntity<String> confirmBet(@PathVariable Long betId, @AuthenticationPrincipal UserDetails userDetails) {
        Member creator = memberService.findByUsername(userDetails.getUsername()); // 인증된 사용자 정보로 멤버 조회
        betService.confirmBet(betId, creator);
        return ResponseEntity.ok("내기 확정 완료");
    }

    // 내기 생성
    @PostMapping("/create")
    public ResponseEntity<String> createBet(@RequestBody BetRequestDto betRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        Member creator = memberService.findByUsername(userDetails.getUsername()); // 인증된 사용자 정보로 멤버 조회
        Bet bet = betRequestDto.toBet(creator);
        betService.createBet(bet, creator);
        return ResponseEntity.ok("내기 생성 완료");
    }

    // 내기 삭제
    @DeleteMapping("/{betId}/delete")
    public ResponseEntity<String> deleteBet(@PathVariable Long betId, @AuthenticationPrincipal UserDetails userDetails) {
        Member creator = memberService.findByUsername(userDetails.getUsername()); // 인증된 사용자 정보로 멤버 조회
        betService.deleteBet(betId, creator);
        return ResponseEntity.ok("내기 삭제 완료");
    }
}