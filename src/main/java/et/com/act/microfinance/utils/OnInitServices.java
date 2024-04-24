package et.com.act.microfinance.utils;

import et.com.act.microfinance.security.JwtUserDetailsService;
import et.com.act.microfinance.security.RoleService;
import et.com.act.microfinance.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class OnInitServices {
    private final RoleService roleService;
    private final SectorService sectorService;
    private final StatusService statusService;
    private final PenaltyService penaltyService;
    private final SavingPeriodService savingPeriodService;
    private final OnBoardingFeeService onBoardingFeeService;
    private final JwtUserDetailsService userDetailsService;

    @Bean
    public CommandLineRunner onInit() {
        return args -> {
            statusService.onInit();
            sectorService.onInit();
            onBoardingFeeService.onInit();
            roleService.onInit();
            userDetailsService.onInit();
            penaltyService.onInit();
        };
    }
}
