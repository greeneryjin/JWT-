package trailProject.trail.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Result<T> {

    //상태 코드
    private int status;

    //성공 or 실패
    private String success;

    //메세지
    private String message;

    //토큰 데이터
    private Map dataToken;

    private T data;

    //토큰 데이터
    public static<T> Result<T> res(final int statusCode, final String responseMessage,final String check, final Map t) {
        return (Result<T>) Result.builder()
                .dataToken(t)
                .status(statusCode)
                .message(responseMessage)
                .success(check)
                .build();
    }

    //데이터가 없을 경우
    public static<T> Result<T> res(final int statusCode, final String responseMessage, final String check) {
        return res(statusCode, responseMessage, check, null);
    }

    //데이터가 있을 경우.
    public static<T> Result<T> res(final int statusCode, final String responseMessage, final String check, final T data) {
        return (Result<T>) Result.builder()
                .data(data)
                .status(statusCode)
                .message(responseMessage)
                .success(check)
                .build();
    }

}

