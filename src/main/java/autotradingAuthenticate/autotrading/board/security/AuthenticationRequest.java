package autotradingAuthenticate.autotrading.board.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;
}
