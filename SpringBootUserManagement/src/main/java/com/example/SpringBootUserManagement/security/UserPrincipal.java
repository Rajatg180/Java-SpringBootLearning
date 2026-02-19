package com.example.SpringBootUserManagement.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.SpringBootUserManagement.constants.Role;
import com.example.SpringBootUserManagement.entity.User;

// UserDeatils interface is comming from spring security core package. It provides core user information. 
// Spring Security uses this information to perform authentication and authorization.
public class UserPrincipal implements UserDetails {

    // we store user entity inside this class , beacuse we need to access user information like email and password for authentication and authorization purposes.
    // spring scurity works with user details usersdetailed , not with your JPA entity direclty
    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    // this method is used to get the authorities (roles) of the user. It retrieves the roles from the User entity, converts them into a collection of GrantedAuthority objects, and returns that collection. 
    // The "ROLE_" prefix is added to each role name to follow Spring Security's convention for role names.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                // ROLE_USER / ROLE_ADMIN
                .collect(Collectors.toSet());
    }

    // this method is used to get the password of the user. It retrieves the password hash from the User entity and returns it. 
    // The @Nullable annotation indicates that this method may return null, which can be useful for handling cases where a user might not have a password (e.g., if they are authenticated through an external provider).
    @Override
    public @Nullable String getPassword() {
       return user.getPasswordHash();
    }

    // 
    @Override
    public String getUsername() {
        return user.getEmail();
    }


    // not implemented these methods because we are not using them in our application. We are assuming that all accounts are non-expired, non-locked, credentials are non-expired, and enabled. If you want to implement these features, you can add additional fields to the User entity and update these methods accordingly.
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
