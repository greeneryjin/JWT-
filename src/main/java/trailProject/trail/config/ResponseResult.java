package trailProject.trail.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import trailProject.trail.security.token.JwtProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ResponseResult extends HttpServlet {

    private ObjectMapper ob = new ObjectMapper();

    public void expiredResponse(HttpServletResponse response) throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        Result<String> result = Result.res(StatusEnum.NOT_FOUND, "토큰이 만료되었습니다.", "false");
        String string = ob.writeValueAsString(result);
        response.getWriter().write(string);
    }

    public void createResponse(HttpServletResponse response, Map tokens, JwtProperties jwtProperties) throws ServletException, IOException {

        response.addHeader(JwtProperties.HEADER_STRING, jwtProperties.TOKEN_PREFIX);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        Result<String> result = Result.res(StatusEnum.OK, "토큰이 발급되었습니다.", "success", tokens);
        String string = ob.writeValueAsString(result);
        response.getWriter().write(string);
    }
}