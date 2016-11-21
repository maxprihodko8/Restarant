package com.restarant.model.sql.groupSql;

import com.restarant.model.group.UserGroup;
import com.restarant.model.order.Order;
import com.restarant.model.user.UserImpl;

import javax.naming.NameNotFoundException;
import java.util.List;

public interface GroupOrderDAO {
    void save(String user, Order order, Integer userGroup);
    void deleteGroup(int groupId);
    void deleteOrder(int orderId);
    UserGroup get(int groupId);
    List<UserGroup> list();
}
