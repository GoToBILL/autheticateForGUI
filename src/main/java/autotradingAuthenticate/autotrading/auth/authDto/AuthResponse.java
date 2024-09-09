package autotradingAuthenticate.autotrading.auth.authDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String status;
    private String authToken;

    public AuthResponse(String status) {
        this.status = status;
    }
}
