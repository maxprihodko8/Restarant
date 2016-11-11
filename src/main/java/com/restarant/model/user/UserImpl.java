package com.restarant.model.user;

import org.springframework.stereotype.Component;

@Component
public class UserImpl implements User{
    private int id;
    private String name;
    private String password;
    private boolean admin;

    public void setNullParams(){
        id = -1;
        name = "";
        password = "";
        admin = false;
    }

    public void setParams(int id, String name, String password, boolean gender){
        this.id = id;
        this.name = name;
        this.password = password;
        this.admin = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin){
        this.admin = admin;
    }


}
