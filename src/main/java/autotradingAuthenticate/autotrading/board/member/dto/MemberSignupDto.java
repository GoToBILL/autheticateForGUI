package autotradingAuthenticate.autotrading.board.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberSignupDto {
    private String username;
    private String password;
    private String email;
}