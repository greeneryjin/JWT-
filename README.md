# JWT
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


## 로그인 시퀀스 다이어그램
![Untitled](https://user-images.githubusercontent.com/87289562/216900238-a2d36691-515b-4e78-bdf9-ee72db70f87d.png)


사용자 인증
''' 
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
'''
