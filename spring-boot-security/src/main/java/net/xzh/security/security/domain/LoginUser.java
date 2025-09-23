package net.xzh.security.security.domain;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  登录用户身份权限
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class LoginUser implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4927179906055151748L;
	private String username;
    private String password;
    private Set<String> permissions;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
