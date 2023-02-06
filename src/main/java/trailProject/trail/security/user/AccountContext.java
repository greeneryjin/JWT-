package trailProject.trail.security.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import trailProject.trail.account.entity.Account;

import java.util.Collection;

@Getter
public class AccountContext extends User {

    private Account account;

    public AccountContext(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getSnsId(), account.getName(), authorities);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
