# JWT-
Android로 카카오 로그인을 한 후, 카카오 정보를 바탕으로 자체적인 JWT 토큰을 사용해서 로그인 기능 만들기  


사용 언어
- JAVA 8

사용 기술
- spring-boot
- spring
- spring-security
- JWT
- H2

라이브러리
- lombok
- gradle

구현 순서도 
1. 안드로이드를 사용해서 카카오에게 회원 정보를 요청합니다.
2. 카카오 회원 정보를 스프링 서버로 받습니다.
3. 카카오 ID를 기반으로 스프링 서버에서 자체적인 JWT 토큰을 발급합니다. 
4. 토큰은 총 두 가지로 accessToken, refreshToken입니다. 

* 카카오에서 제공하는 jwt를 사용하는 것이 아닌 스프링 서버에서 자제척으로 만든 토큰으로 로그인을 합니다.



자세한 설명 
- https://www.notion.so/App-dec7906489934c329c048a63b3351029
