package trailProject.trail.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import trailProject.trail.account.dto.AccountDto;
import trailProject.trail.account.dto.LoginDto;
import trailProject.trail.account.entity.Account;
import trailProject.trail.account.entity.enums.Role;
import trailProject.trail.account.repository.AccountRepository;
import trailProject.trail.account.service.AccountService;
import trailProject.trail.config.Result;
import trailProject.trail.config.StatusEnum;

import java.util.List;

@RestController
@RequestMapping("/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //회원가입
    @PostMapping("signUp")
    public Result<Long> signUpAccount(@RequestBody LoginDto loginDto){
        String user = Role.USER.getGrantedAuthority();
        Account account = new Account(loginDto.getSnsId(), loginDto.getName());
        account.roleSave(user);
        Account saveAccount = accountService.saveAccount(account);
        return Result.res(StatusEnum.OK, "회원가입이 완료되었습니다.", "success", saveAccount.getId());
    }

    //로그인
    @PostMapping("signIn")
    public void signInAccount(){ }

    @GetMapping("user/view/myPage")
    public Result<AccountDto> userPage() {
        Account account = accountService.findUser();
        AccountDto accountDto = new AccountDto(account.getName(), account.getLastWorkDate(), account.getDistanceTotal(), account.getTimeTotal(), account.getStepCountTotal());
        return Result.res(StatusEnum.OK, "", "success", accountDto);
    }

    @PostMapping("userLocation")
    public Result<String> userLocation(@RequestParam String location) {
        accountService.saveLocation(location);
        return Result.res(StatusEnum.OK, "위치가 저장되었습니다.", "success",location);
    }
}