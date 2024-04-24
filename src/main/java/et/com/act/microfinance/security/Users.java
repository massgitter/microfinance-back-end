package et.com.act.microfinance.security;

import et.com.act.microfinance.models.Shared;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Users extends Shared implements UserDetails {
    private String username;
    private String password;
    private String fullName;
    private boolean enabled = true;

    @ManyToOne
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public UsersResponse usersResponse() {
        return UsersResponse.builder()
                .id(getId())
                .userName(username)
                .fullName(fullName)
                .role(role.getName())
                .build();
    }

    public LoginResponse loginResponse() {
        return LoginResponse.builder()
                .id(getId())
                .userName(username)
                .fullName(fullName)
                .role(role.getName())
                .build();
    }
}
