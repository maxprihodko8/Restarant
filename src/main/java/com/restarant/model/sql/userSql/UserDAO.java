package com.restarant.model.sql.userSql;

import com.restarant.model.user.UserImpl;

import java.util.List;
//CRUD
public interface UserDAO {
     void saveOrUpdate(UserImpl user);
     void delete(int userId);
     void delete(String userName);
     UserImpl get(int userId);
     UserImpl getByName(String name);
     List <UserImpl> list();
}
