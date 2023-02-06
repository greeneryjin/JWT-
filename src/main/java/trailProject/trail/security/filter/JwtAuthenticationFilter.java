package trailProject.trail.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import trailProject.trail.account.dto.LoginDto;
import trailProject.trail.account.entity.Account;
import trailProject.trail.config.ResponseResult;
import trailProject.trail.security.token.JwtProperties;
import trailProject.trail.security.token.JwtUsernamePasswordAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private JwtProperties jwtProperties;

    private ObjectMapper  om = new ObjectMapper();

    public JwtAuthenticationFilter(JwtProperties jwtProperties) {
        super(new AntPathRequestMatcher("/signIn"));
        this.jwtProperties = jwtProperties;
    }

    //로그인 요청 시 로그인 시도를 위해 실행됨
    //필터를 사용해서 Request Header 토큰 유효성 검사.
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        LoginDto loginDto = om.readValue(request.getReader(), LoginDto.class);
        if(StringUtils.isEmpty(loginDto.getSnsId()) || StringUtils.isEmpty(loginDto.getName())){
            throw new IllegalAccessException("사용자 입력값이 없습니다.");
        }
        //인증 전 token 객체 생성
        JwtUsernamePasswordAuthenticationToken token = new JwtUsernamePasswordAuthenticationToken(loginDto.getSnsId(),loginDto.getName());
        //인증 처리를 위해 AuthenticationManager 에게 위임.
        return getAuthenticationManager().authenticate(token);
    }

    //인증 완료 후 response jwt 토큰 발행
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Account principal = (Account) authResult.getPrincipal();
        String accessToken = jwtProperties.createAccessToken(principal.getSnsId());
        String refreshToken = jwtProperties.createRefreshToken(principal.getSnsId());

        //맵에 넣기.
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        //반환에 값을 넣어줌.
        ResponseResult result = new ResponseResult();
        result.createResponse(response, tokens, jwtProperties);
    }
}