package com.restarant.controller.service;

import com.restarant.model.repository.UserRepository;
import com.restarant.model.sql.UserDAO;
import com.restarant.model.sql.UserDAOImpl;
import com.restarant.model.user.UserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;

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
}
