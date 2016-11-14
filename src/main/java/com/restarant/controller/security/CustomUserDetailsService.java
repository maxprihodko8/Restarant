package com.restarant.controller.security;

import com.restarant.model.repository.UserRepository;
import com.restarant.model.user.UserImpl;
import com.restarant.model.user.UserRolesEnum;
import com.restarant.model.sql.userSql.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository repository;
    private UserDAOImpl userDAO;


    @Autowired
    CustomUserDetailsService(UserRepository userRepository, UserDAOImpl userDAO){
        this.repository = userRepository;
        this.userDAO = userDAO;
    }


    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            //UserImpl user = repository.getUserByName(userName);
            UserImpl user = userDAO.getByName(userName);
            Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
            if (!user.isAdmin())
                roles.add(new SimpleGrantedAuthority(UserRolesEnum.ROLE_USER.name()));
            else
                roles.add(new SimpleGrantedAuthority(UserRolesEnum.ROLE_ADMIN.name()));
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getName(),
                    user.getPassword(), roles);
            return userDetails;
        } catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
