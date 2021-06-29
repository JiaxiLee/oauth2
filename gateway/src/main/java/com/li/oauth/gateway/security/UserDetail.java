package com.li.oauth.gateway.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * description: UserDetail
 * date: 2020/7/29
 * author: lijiaxi-os
 */
@Data
public class UserDetail implements UserDetails {

    private String username;

    private String password;

    private String roleName;

    private Integer roleId;

    private List<Integer> menuCodes;

    public UserDetail(String username, String password, List<Integer> menuCodes) {
        this.username = username;
        this.password = password;
        this.menuCodes = menuCodes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        StringBuilder sb = new StringBuilder();
        String pre = "";
        for (Integer menu : this.menuCodes) {
            sb.append(pre);
            sb.append(menu);
            pre = ",";
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(sb.toString());
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

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(new Date(1618589447000L));
    }
}
