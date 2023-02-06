package trailProject.trail.security.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refreshToken_id")
    private Long id;

    private String refreshToken;

    private Long accountId;

    public RefreshToken(String refreshToken, Long accountId) {
        this.refreshToken = refreshToken;
        this.accountId = accountId;
    }
}
