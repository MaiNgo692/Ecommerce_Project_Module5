package com.ra.model.dto.request;

import com.ra.model.entity.UsersEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsAdapter implements UserDetails {
    private final UsersEntity usersEntity;
    public UserDetailsAdapter(UsersEntity usersEntity){
        this.usersEntity = usersEntity;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        usersEntity.getUserRoleEntities().forEach(ur ->{
            roles.add(new SimpleGrantedAuthority( ur.getRoleByRoleId().getRoleName()));
        });
        return roles;
    }
    @Override
    public String getPassword() {
        return this.usersEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.usersEntity.getUsername();
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
        return this.usersEntity.getStatus();
    }
}
