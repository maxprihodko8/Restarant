package com.restarant.controller.service;

import com.restarant.model.repository.ListOrders;
import com.restarant.model.order.Order;
import com.restarant.model.order.SimpleOrder;
import com.restarant.model.sql.dishSql.DishDAOImpl;
import com.restarant.model.sql.orderSql.OrderDAOImpl;
import com.restarant.model.user.UserImpl;

import javax.naming.NameNotFoundException;
import java.util.List;

public class OrderService {

    private ListOrders listOrders;
    private OrderDAOImpl orderDAO;
    private DishDAOImpl dishDAO;

    public OrderService(ListOrders listOrders, OrderDAOImpl orderDAO, DishDAOImpl dishDAO) {
        this.listOrders = listOrders;
        this.orderDAO = orderDAO;
        this.dishDAO = dishDAO;
    }

    public void addOrder(UserImpl user, Order order) {
        //listOrders.addOrder(user, order);
        orderDAO.saveOrUpdate(user, order);
    }

    public void addMultipleItemsToOneOrder(UserImpl user, SimpleOrder[] simpleOrder) {
        Order order = new Order();
        for (SimpleOrder s : simpleOrder) {
            order.addDish(dishDAO.getByName(s.getName()), s.getCount());
        }
        addOrder(user, order);
    }

    public List<Order> getOrdersOfUser(UserImpl user) throws NameNotFoundException {
        try {
            //return listOrders.getOrdersOfUser(user);
            return orderDAO.getByUser(user);
        } catch (NameNotFoundException e) {
            throw new NameNotFoundException();
        }
    }

    public List<Order> getOrders(int id) throws NameNotFoundException {
        return orderDAO.get(id);
    }

    public int submitPriceForOrders(UserImpl user) throws NameNotFoundException {
        ///return listOrders.submitPriceForOrders(user);
        return listOrders.submitPriceForOrders(orderDAO.getByUser(user));
    }

    public void removeOrder(Order order){
        /*try {
            listOrders.removeOrder(user, order);
        } catch (NameNotFoundException e){
        }*/
        orderDAO.delete(order);
    }

    public void removeOrder(int orderId) {
        orderDAO.delete(orderId);
    }


    public void removeAllOrdersFromUser(UserImpl user) {
        orderDAO.deleteAllOrdersOfUser(user);
        //listOrders.removeOrdersOfUser(user);
    }

}
