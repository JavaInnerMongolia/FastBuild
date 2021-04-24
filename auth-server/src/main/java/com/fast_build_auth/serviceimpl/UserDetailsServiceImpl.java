package com.fast_build_auth.serviceimpl;

import com.fast_build_auth.domain.UserInfo;
import com.fast_build_auth.unit.MD5Util;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据名称去查数据库
        UserInfo clUser = new UserInfo();
        clUser.setUserName(username);
        clUser.setPassword("123456");

        // 权限列表
        List<String> user_permission = new ArrayList<>();
        user_permission.add("admin");
        String user_permission_string = StringUtils.join(user_permission.toArray(), ",");

        return new User(username, MD5Util.getStringMD5(clUser.getPassword()), AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));
    }
}
