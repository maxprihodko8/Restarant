package com.restarant.model.repository;

import com.restarant.model.user.UserImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
@Component
public class UserRepository {
    private Set <UserImpl> userList = new HashSet<UserImpl>();

    public UserImpl getUserByName(String name) throws UsernameNotFoundException{
        for (UserImpl s : userList){
            if(name.equals(s.getName()))
                return s;
        }
        throw new UsernameNotFoundException("User not found in list");
    }

    public void addUser(UserImpl user){
        userList.add(user);
    }

    public void deleteUser(String name){
        Iterator i = userList.iterator();
        while (i.hasNext()){
            UserImpl nextUser = (UserImpl) i.next();
            if(nextUser.getName().equals(name)){
                i.remove();
            }
        }
    }

}
