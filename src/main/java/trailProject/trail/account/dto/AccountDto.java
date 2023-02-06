package trailProject.trail.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {

    private String name;
    private long lastWorkDate;
    private Integer distanceTotal;
    private Integer timeTotal;
    private Integer stepCountTotal;

    public AccountDto(String name, long lastWorkDate, Integer distanceTotal,
                      Integer timeTotal, Integer stepCountTotal) {
        this.name = name;
        this.lastWorkDate = lastWorkDate;
        this.distanceTotal = distanceTotal;
        this.timeTotal = timeTotal;
        this.stepCountTotal = stepCountTotal;
    }
}