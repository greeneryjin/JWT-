package trailProject.trail.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trailProject.trail.account.entity.Account;
import trailProject.trail.account.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;


    public Account saveAccount(Account accountSave) {
        return accountRepository.save(accountSave);
    }

    public Account findUser() {
        //사용자 가지고 오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account accountId = (Account) authentication.getPrincipal();
        Account account = accountRepository.findBySnsId(accountId.getSnsId());
        return account;
    }

    @Transactional
    public void saveLocation(String location) {
        //사용자 가지고 오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account accountId = (Account) authentication.getPrincipal();
        Account account = accountRepository.findBySnsId(accountId.getSnsId());

        account.locationSave(location);
        accountRepository.save(account);
    }
}