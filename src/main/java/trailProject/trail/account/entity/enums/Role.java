package trailProject.trail.account.entity.enums;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");
    private final String grantedAuthority;
}
