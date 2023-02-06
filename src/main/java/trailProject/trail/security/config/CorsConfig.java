package trailProject.trail.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //자바스크립트로 요청이 오면 처리
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //ip 응답 허용
        config.addAllowedOrigin("*");
        //헤더에 응답 허용
        config.addAllowedHeader("*");
        //crud 응답  허용
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/trail/**", config);
        return new CorsFilter(source);
    }
}
