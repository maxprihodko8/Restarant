package com.restarant.controller.controller;

import com.restarant.controller.service.GroupOrderService;
import com.restarant.controller.service.UserService;
import com.restarant.model.group.UserGroup;
import com.restarant.model.order.SimpleOrder;
import com.restarant.model.user.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@EnableWebMvc
@RestController
public class GroupUserController {

    @Autowired
    GroupOrderService groupOrderService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/groupOrder", method = RequestMethod.GET)
    public ModelAndView getGroupOrderPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/groupOrder");
        return modelAndView;
    }

    @RequestMapping(value = "/user/listGroupsOfUser", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<UserGroup> listOfGroups(){
        UserImpl user = userService.getCurrentUser();
        return groupOrderService.listGroupsOfUser(user);
    }

    @RequestMapping(value = "/user/leftGroup/{id}", method = RequestMethod.GET)
    public void leftUserFromGroup(@PathVariable int id){
        UserImpl user = userService.getCurrentUser();
        groupOrderService.leftUserFromGroup(user, id);
    }

    @RequestMapping(value = "/user/currentUser", method = RequestMethod.GET, headers = "Accept=application/json")
    public UserImpl getCurrentUser(){
        return userService.getCurrentUser();
    }

    @RequestMapping(value = "/user/deleteGroup/{id}", method = RequestMethod.GET)
    public void deleteGroup(@PathVariable int id){
        UserImpl user = userService.getCurrentUser();
        groupOrderService.deleteGroup(user, id);
    }

    @RequestMapping(value = "/user/addGroup/{name}", method = RequestMethod.GET)
    public void addGroup(@PathVariable String name){
        UserImpl user = userService.getCurrentUser();
        groupOrderService.addGroup(user, name);
    }

    @RequestMapping(value = "/user/addUserToGroup/{groupId}/{userName}")
    public void addUserToGroup(@PathVariable int groupId, @PathVariable String userName){
        UserImpl user = userService.getCurrentUser();
        groupOrderService.acceptAddingUserToGroup(user, groupId, userName);
    }

    @RequestMapping(value = "/user/rejectUserFromGroup/{groupId}/{userName}")
    public void rejectUserFromGroup(@PathVariable int groupId, @PathVariable String userName){
        UserImpl user = userService.getCurrentUser();
        groupOrderService.rejectUserFromAdddingToGroup(user, groupId, userName);
    }

    @RequestMapping(value = "/user/sendReq/{groupName}", method = RequestMethod.GET)
    public void addRequestForEnterGroup(@PathVariable String groupName){
        UserImpl user = userService.getCurrentUser();
        groupOrderService.addReqForEnterGroup(user, groupName);
    }

    @RequestMapping(value = "/user/getGroupNames", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getGroupNamesList(){
        UserImpl user = userService.getCurrentUser();
        return groupOrderService.getGroupNames(user);
    }

    @RequestMapping(value = "/user/saveGroupOrder/{groupName}", method = RequestMethod.POST, headers = "Accept=application/json")
    public void saveOrderInGroup(@RequestBody SimpleOrder[] simpleOrders, @PathVariable String groupName){
        UserImpl user = userService.getCurrentUser();
        groupOrderService.addMultipleOrdersToUserInGroup(user, simpleOrders, groupName);
    }

}
