package com.sheryians.major.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//for Spring  security - userdetails import
public class CustomUserDetail extends  User implements UserDetails
{//ctrl+o to overide methods of UserDetails


    public CustomUserDetail(User user)
    {
   super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        // Authority --> Roles of users. List<Role> user can have multiple roles (user.java)
        List<GrantedAuthority> authorityList= new ArrayList<>();
        super.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority(role.getName())));
        //super.getRoles() will fetch the list of roles then iterate using for each
        return authorityList;
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
     public String getPassword() {
        return super.getPassword();
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
