package com.vmo.vmoproject.security.userprincal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmo.vmoproject.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {
    private String id;
    private String name;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> roleName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserPrinciple(String id, String name, String username, String email, String password, Collection<? extends GrantedAuthority> roleName) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleName = roleName;
    }

    public UserPrinciple() {
    }

    public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorities = user.getRoleName().stream().map(role ->
                new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return new UserPrinciple(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleName;
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
