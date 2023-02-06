package trailProject.trail.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trailProject.trail.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findBySnsId(String snsId);
}
