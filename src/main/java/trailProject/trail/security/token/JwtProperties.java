package trailProject.trail.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import trailProject.trail.account.entity.Account;

import java.util.Date;
@Component
public class JwtProperties {

    @Value("${jwt.secret}")
    private String secret;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    //액세스 토큰 발급
    public String createAccessToken(String account){
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String accessToken = JWT.create()
                .withSubject(account)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 9999))
                .sign(algorithm);
        return accessToken;
    }

    //리프레시 토큰 발급
    public String createRefreshToken(String account) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String refreshToken = JWT.create()
                .withSubject(account)
                .withExpiresAt(new Date(System.currentTimeMillis() + + 100000 * 60 * 60 * 1000))
                .sign(algorithm);
        return refreshToken;
    }

    public JWTVerifier verifierToken(){
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier;
    }
}
