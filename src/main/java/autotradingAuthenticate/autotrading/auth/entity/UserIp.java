package autotradingAuthenticate.autotrading.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserIp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserIp(String ipAddress, User user) {
        this.ipAddress = ipAddress;
        this.user = user;
    }
}