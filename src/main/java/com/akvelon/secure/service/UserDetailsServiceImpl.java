package com.akvelon.secure.service;

import com.akvelon.secure.entity.User;
import com.akvelon.secure.service.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    AdminDao adminDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUser(email);
        Set<GrantedAuthority> roles = new HashSet();
        String role = adminDao.getUserRole(user.getLogin());
        roles.add(new SimpleGrantedAuthority(role));

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roles);

        return userDetails;
    }

}
