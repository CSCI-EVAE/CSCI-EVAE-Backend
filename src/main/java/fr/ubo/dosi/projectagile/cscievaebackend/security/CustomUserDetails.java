package fr.ubo.dosi.projectagile.cscievaebackend.security;

import fr.ubo.dosi.projectagile.cscievaebackend.Model.Authentification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends Authentification implements UserDetails {

    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Authentification byUsername) {
        this.username = byUsername.getLoginConnection();
        this.password = byUsername.getMotPasse();
        List<GrantedAuthority> auths = new ArrayList<>();

        auths.add(new SimpleGrantedAuthority(byUsername.getRole().toUpperCase()));
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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