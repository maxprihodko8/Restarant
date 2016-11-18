package com.restarant.model.group;

import com.restarant.model.user.UserImpl;

import java.util.ArrayList;
import java.util.List;

public class UserGroup {

    private Integer id;
    private String name;
    private String creator;
    private List <UserImpl> users = new ArrayList<>();
    private List <Integer> ordersId = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserImpl> getUsers() {
        return users;
    }

    public void setUsers(List<UserImpl> users) {
        this.users = users;
    }

    public List<Integer> getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(List<Integer> ordersId) {
        this.ordersId = ordersId;
    }


}
