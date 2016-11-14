package com.restarant.model.user;

public class CheckUser {

    public CheckUser(){

    }

    public boolean checkUserOnLoginAndPassword(UserImpl user){
        if(user != null){
            if (user.getName().length() > 3 && user.getPassword().length() > 3){
                if(user.getName().length() < 30 && user.getPassword().length() < 30)
                    return true;
            }
        }
        return false;
    }
}
