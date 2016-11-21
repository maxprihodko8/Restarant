package com.restarant.controller.service;

import com.restarant.model.group.UserGroup;
import com.restarant.model.order.Order;
import com.restarant.model.order.SimpleOrder;
import com.restarant.model.sql.groupSql.GroupOrderDAOImpl;
import com.restarant.model.user.UserImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupOrderService {
    private GroupOrderDAOImpl groupOrderDAO;
    private DishService dishService;

    GroupOrderService(GroupOrderDAOImpl groupOrderDAO, DishService dishService) {
        this.groupOrderDAO = groupOrderDAO;
        this.dishService = dishService;
    }

    public void save(String user, Order order, Integer userGroup){
        groupOrderDAO.save(user, order, userGroup);
    }

    public void addGroup(UserImpl user, String name){
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setCreator(user.getName());
        groupOrderDAO.addGroup(userGroup);
    }

    public void addReqForEnterGroup(UserImpl user, String groupName){
        groupOrderDAO.addReqForEnterGroup(user, groupName);
    }

    public List<String> getGroupNames(UserImpl user){
        return groupOrderDAO.getGroupNamesList(user);
    }

    public void acceptAddingUserToGroup(UserImpl user, int groupId, String userNameToAdd){
        if(groupOrderDAO.get(groupId).getCreator().equals(user.getName())){
            groupOrderDAO.addUserToGroup(groupId, userNameToAdd);
        }
    }

    public void rejectUserFromAdddingToGroup(UserImpl user, int groupId, String userNameToAdd){
        if(groupOrderDAO.get(groupId).getCreator().equals(user.getName())){
            groupOrderDAO.rejectAdddingToGroup(groupId, userNameToAdd);
        }
    }

    public void deleteGroup(UserImpl user, int groupId){
        if(groupOrderDAO.get(groupId).getCreator().equals(user.getName()))
            groupOrderDAO.deleteGroup(groupId);
    }

    public void deleteOrder(int orderId){
        groupOrderDAO.deleteOrder(orderId);
    }

    public void leftUserFromGroup(UserImpl user, int groupId){
        groupOrderDAO.leftUserFromGroup(user, groupId);
    }

    public UserGroup get(int groupId){
        return groupOrderDAO.get(groupId);
    }

    public List<UserGroup> list(){
        return groupOrderDAO.list();
    }

    public List<UserGroup> listGroupsOfUser(UserImpl user){
        return groupOrderDAO.getByUser(user);
    }

    public void addMultipleOrdersToUserInGroup(UserImpl user, SimpleOrder[] simpleOrder, String groupName) {
        Order order = new Order();
        int groupId = groupOrderDAO.getGroupIdByName(groupName);
        for (SimpleOrder s : simpleOrder) {
            order.addDish(dishService.getDishByName(s.getName()), s.getCount());
        }
        save(user.getName(), order, groupId);
    }
}
