package com.restarant.controller.service;

import com.restarant.model.repository.UserRepository;
import com.restarant.model.sql.userSql.UserDAOImpl;
import com.restarant.model.user.UserImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private UserDAOImpl userDAO;
    private UserRepository userRepository;

    UserService(UserDAOImpl userDAO, UserRepository userRepository){
        this.userDAO = userDAO;
        this.userRepository = userRepository;
    }

    public UserImpl getUser(int id) throws UsernameNotFoundException{
        try {
            return userDAO.get(id);
        } catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    public UserImpl getUser(String name){
        try{
            return userDAO.getByName(name);
        } catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    public void saveUser(UserImpl user){
        userDAO.saveOrUpdate(user);
        userRepository.addUser(user);
    }

    public void logOutUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            userRepository.deleteUser(authentication.getName());
        }
    }

    public void logInUser(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userRepository.addUser(userDAO.getByName(authentication.getName()));
    }

    public UserImpl getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByName(authentication.getName());
    }

    public List<UserImpl> getCurrentLoggedUserList(){
        return userDAO.list();
        //return userRepository.getUserList();
    }

    public boolean isUserAdmin(){
        Collection <SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for(SimpleGrantedAuthority s : authorities){
            if(s.getAuthority() == "ROLE_ADMIN"){
                return true;
            }
        }
        return false;
    }

    public Map<UserImpl, Boolean> getUsersListWithOnlineTag(){
        return userRepository.getOnlineUsers(userDAO.list());
    }
}
