# JWT 
Android에서 카카오 로그인을 한 후, 카카오에서 전달한 개인 정보로 서버에서 자체적인 JWT 토큰을 발급하여 로그인 기능만들기  

사용 언어
```
- JAVA 8
```

사용 기술
```
- springboot
- spring-security
- JWT
- H2
```

라이브러리
```
- lombok
- gradle
```


구현 순서도 
1. 안드로이드를 사용해서 카카오에게 회원 정보를 요청합니다.
2. 카카오 회원 정보를 스프링 서버로 받습니다.
3. 카카오에게 정보를 전달한 후, 카카오 토큰을 받습니다.
4. 카카오 토큰의 카카오 ID를 기반으로 스프링 서버에서 자체적인 JWT 토큰을 발급합니다. 
5. 토큰은 총 두 가지로 accessToken, refreshToken입니다. 
6. accessToken으로 로그인 
7. accessToken 만료 시 refreshToken으로 재 로그인 

* 카카오에서 제공하는 토큰을 사용하는 것이 아닌 저희 서버에서 자제척으로 만든 jwt 토큰으로 로그인을 합니다.


## 로그인 시퀀스 다이어그램
![Untitled](https://user-images.githubusercontent.com/87289562/216900238-a2d36691-515b-4e78-bdf9-ee72db70f87d.png)

토큰 발급
```java
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

    // 토큰 검증 
    public JWTVerifier verifierToken(){
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier;
    }

```

사용자 인증
```java
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletExceptio{
        LoginDto loginDto = om.readValue(request.getReader(), LoginDto.class);
        if(StringUtils.isEmpty(loginDto.getSnsId()) || StringUtils.isEmpty(loginDto.getName())){
            throw new IllegalAccessException("사용자 입력값이 없습니다.");
        }
        
        //인증 전 token 객체 생성
        JwtUsernamePasswordAuthenticationToken token = new JwtUsernamePasswordAuthenticationToken(loginDto.getSnsId(),loginDto.getName());
        
        //인증 처리를 위해 AuthenticationManager 에게 위임.
        return getAuthenticationManager().authenticate(token);
    }
```


사용자 인증 후, jwt 토큰 발급
```java

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
```


jwt 토큰 예시
```
accessToken  : eyJhbGgcegfgeffcedftqazwscdrdsfgsdsevbgyrbnccuiojnmbvbvhyurffdcdvvzxcvwedfuuvgrtvfmJLWlJMRDVhRhU3NDEzNDl9.JDPsm5LUHtc1TzrFmuIxKCBG5hnZ-B7YYXpumg4-bCF-sfdffdfgdftg6IlFSNzBUS2ZacjVfcWp4U3J5VHQ5R

refreshToken : eyiJ9US2ZacjVfc4U3J.eyatc1TzrFmuKCBG5hn2tzODlkVk5pamJGGFTRLWlJMRDVhRTgiLCB7YYXpumg4JleHAiOjE2NzU3NDEzNDl9.zbnNJZCgrrtthytfdgbfdgm5LUHJ-ThfID2QDPs-bCF-DFGFddrafgdftg6IlVHQ5RFSNzB5aaGciOiJIUzUxM
```

로그인 화면 app
<img width="258" alt="늘솔길" src="https://user-images.githubusercontent.com/87289562/218096760-5e32d131-f486-44f4-bf7f-15c6243f07fa.PNG">


