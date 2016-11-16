package com.restarant.controller.service;

import com.restarant.model.order.ListOrders;
import com.restarant.model.order.Order;
import com.restarant.model.order.SimpleOrder;
import com.restarant.model.user.UserImpl;

import javax.naming.NameNotFoundException;
import java.util.List;

public class OrderService {

    private ListOrders listOrders;
    boolean ff = false;

    public OrderService(ListOrders listOrders){
        this.listOrders = listOrders;
    }

    public void addOrder(UserImpl user, Order order){
        listOrders.addOrder(user, order);
    }

    public void addMultipleItemsToOneOrder(UserImpl user, SimpleOrder[] simpleOrder){
        Order order = new Order();
        for (SimpleOrder s : simpleOrder){
            order.addDish(s.getName(), s.getCount());
        }
        addOrder(user, order);
    }

    public List<Order> getOrdersOfUser(UserImpl user) throws NameNotFoundException{
        try {
            return listOrders.getOrdersOfUser(user);
        } catch (NameNotFoundException e){
            throw new NameNotFoundException();
        }
    }

    public int submitPriceForOrders(UserImpl user) throws NameNotFoundException {
        return listOrders.submitPriceForOrders(user);
    }

    public void removeOrder(UserImpl user, Order order)  {
        try {
            listOrders.removeOrder(user, order);
        } catch (NameNotFoundException e){
        }
    }

    public void removeOrder(UserImpl user, int id){
        listOrders.removeOrder(user, id);
    }

    public void removeAllOrdersFromUser(UserImpl user){
        listOrders.removeOrdersOfUser(user);
    }

    public void addData() {
        if (!ff) {
            listOrders.addSomeData();
            ff = true;

        }
    }
}
