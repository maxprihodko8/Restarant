package com.restarant.model.sql.orderSql;


import com.restarant.model.order.Order;
import com.restarant.model.user.UserImpl;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface OrderDAO {
    void saveOrUpdate(UserImpl user, Order order);
    void delete(Order order);
    void deleteAllOrdersOfUser(UserImpl user);
    List<Order> get(int userId) throws NameNotFoundException;
    List<Order> getByUser(UserImpl user) throws NameNotFoundException;
    List<Order> list();
}
