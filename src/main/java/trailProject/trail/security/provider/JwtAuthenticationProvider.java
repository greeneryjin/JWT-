package trailProject.trail.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import trailProject.trail.security.service.JwtAccountService;
import trailProject.trail.security.token.JwtUsernamePasswordAuthenticationToken;
import trailProject.trail.security.user.AccountContext;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    JwtAccountService jwtAccountService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String id = (String) authentication.getPrincipal();
        AccountContext accountContext = (AccountContext) jwtAccountService.loadUserByUsername(id);
        JwtUsernamePasswordAuthenticationToken authenticationToken = new JwtUsernamePasswordAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
