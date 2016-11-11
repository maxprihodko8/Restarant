package com.restarant.model.user;

public interface User {
     int getId();
     void setId(int id);
     String getName();
     void setName(String name);
     String getPassword();
     void setPassword(String password);
     boolean isAdmin();
     void setAdmin(boolean admin);
}
