package trailProject.trail.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import trailProject.trail.account.entity.Account;
import trailProject.trail.account.entity.enums.Role;
import trailProject.trail.account.repository.AccountRepository;
import trailProject.trail.security.user.AccountContext;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtAccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        Account account = accountRepository.findBySnsId(id);

        if(account == null){
            throw new UsernameNotFoundException("유저 정보가 디비에 없습니다.");
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        AccountContext accountContext = new AccountContext(account, roles);
        return accountContext;
    }
}