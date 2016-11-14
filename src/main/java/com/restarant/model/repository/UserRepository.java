package com.restarant.model.repository;

import com.restarant.model.sql.userSql.UserDAO;
import com.restarant.model.user.UserImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

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

    public UserImpl[] getUserList(){
        UserImpl[] users = new UserImpl[userList.size()];
        Iterator i = userList.iterator();
        int nextUser = 0;
        while (i.hasNext()){
            users[nextUser] = (UserImpl) i.next();
            nextUser++;
        }
        return users;
    }

    public Map<UserImpl, Boolean> getOnlineUsers(List <UserImpl> listUsersFromDatabase){
        Map <UserImpl, Boolean> users = new HashMap<UserImpl, Boolean>();
        List <UserImpl> allUsersInDatabase = listUsersFromDatabase;
        for (UserImpl user : allUsersInDatabase){
            try {
                this.getUserByName(user.getName());
                users.put(user, true);
            } catch (UsernameNotFoundException e){
                users.put(user, false);
            }
        }
        return users;
    }

}
