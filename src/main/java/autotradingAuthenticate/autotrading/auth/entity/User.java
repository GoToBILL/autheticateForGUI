package autotradingAuthenticate.autotrading.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String authToken;

    @Column
    private String ipAddress;

    public User(String username, String authToken, String ipAddress) {
        this.username = username;
        this.authToken = authToken;
        this.ipAddress = ipAddress;
    }
}