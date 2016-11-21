package com.restarant.model.group;

import com.restarant.model.order.Order;
import com.restarant.model.user.UserImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserGroup {

    private Integer id = new Random().nextInt();
    private String name;
    private String creator;

    private List <String> users = new ArrayList<>();
    private List <Integer> ordersId = new ArrayList<>();
    private List <Order> orders = new ArrayList<>();
    public List<Order> userOrders = new ArrayList<>();

    public void addOrderToUserOrders(Order order) {
        userOrders.add(order);
    }

    public void deleteUserOrder(Order order){
        userOrders.remove(order);
    }

    public List<String> getReqs() {
        return reqs;
    }

    public List<Order> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(List<Order> userOrders) {
        this.userOrders = userOrders;
    }

    public void setReqs(List<String> reqs) {
        this.reqs = reqs;
    }

    private List <String> reqs = new ArrayList<>();

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

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<Integer> getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(List<Integer> ordersId) {
        this.ordersId = ordersId;
    }

    public void setCreator(String user){
        this.creator = user;
    }

    public String getCreator(){
        return this.creator;
    }

    public void addUser(String userName){
        users.add(userName);
    }

    public void addOrderId(int orderId){
        this.ordersId.add(orderId);
    }

    public void deleteUserFromRequestsForEnter(UserImpl user){
        reqs.remove(user.getName());
    }

    public void addUserToRequests(String user){
        reqs.add(user);
    }

}
