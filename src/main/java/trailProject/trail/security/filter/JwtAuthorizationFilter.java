package trailProject.trail.security.filter;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import trailProject.trail.config.ResponseResult;
import trailProject.trail.security.service.JwtAccountService;
import trailProject.trail.security.token.JwtProperties;
import trailProject.trail.security.token.JwtUsernamePasswordAuthenticationToken;
import trailProject.trail.security.user.AccountContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtAccountService jwtAccountService;
    private JwtProperties jwtProperties;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtAccountService jwtAccountService,
                                  JwtProperties jwtProperties) {
        super(authenticationManager);
        this.jwtAccountService = jwtAccountService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(JwtProperties.HEADER_STRING);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                String id = checkToken(token);

                if(id != null){
                    AccountContext accountContext = (AccountContext) jwtAccountService.loadUserByUsername(id);
                    JwtUsernamePasswordAuthenticationToken authenticationToken = new JwtUsernamePasswordAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(request, response);
                }
            }catch (Exception e) {
                ResponseResult result = new ResponseResult();
                result.expiredResponse(response);
            }
        }else {
            chain.doFilter(request, response);
        }
    }
    public String checkToken(String token) {
        JWTVerifier jwtVerifier = jwtProperties.verifierToken();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String id = decodedJWT.getSubject();
        return id;
    }
}
