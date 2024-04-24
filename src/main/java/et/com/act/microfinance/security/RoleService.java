package et.com.act.microfinance.security;

import et.com.act.microfinance.utils.RolesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepo roleRepo;

    public void create(String name) {
        if (Objects.isNull(roleRepo.findByName(name))) {
            Role role = Role.builder()
                    .name(name)
                    .build();
            roleRepo.save(role);
        }
    }


    public void onInit() {
        Arrays.stream(RolesEnum.values())
                .forEach(rolesEnum -> create(rolesEnum.getName()));
    }
}
