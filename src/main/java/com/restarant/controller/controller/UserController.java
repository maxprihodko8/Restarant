package com.restarant.controller.controller;

import com.restarant.controller.service.OrderService;
import com.restarant.controller.service.UserService;
import com.restarant.model.order.Order;
import com.restarant.model.order.SimpleOrder;
import com.restarant.model.sql.dishSql.DishDAOImpl;
import com.restarant.model.sql.userSql.UserDAO;
import com.restarant.model.user.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.NameNotFoundException;
import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@RestController
@Controller
public class UserController {

    @Autowired
    DishDAOImpl dishDAO;

    @Autowired
    OrderService orderService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/userMainPage")
    public ModelAndView userMainPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/mainPage");
        modelAndView.addObject("userData", new UserImpl());
        modelAndView.addObject("dishList", dishDAO.list());
        return modelAndView;
    }

    @RequestMapping(value = "/user/course")
    public ModelAndView getCourse(){
        return new ModelAndView();
    }

    @RequestMapping(value = "/user/multipleCource")
    public ModelAndView getMultipleCourse(){
        return new ModelAndView();
    }

    @RequestMapping(value = "/user/userOrders/{id}", method = RequestMethod.GET)
    public List<Order> getUserOrders(@PathVariable int id){
        try {
            return orderService.getOrdersOfUser(userDAO.get(id));
        } catch (NameNotFoundException e){
            return new ArrayList<Order>();
        }
    }

    @RequestMapping(value = "/user/userOrders", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Order> getUserOrders(){
        orderService.addData();
        UserImpl user =  userService.getCurrentUser();
        try {
            List<Order> orders = orderService.getOrdersOfUser(user);
            return orders;
        } catch (NameNotFoundException e){
            return new ArrayList<Order>();
        }
    }

    @RequestMapping(value = "/user/getSubmitPrice", method = RequestMethod.GET)
    public int getSubmitPrice(){
        try {
            return orderService.submitPriceForOrders(userService.getCurrentUser());
        } catch (NameNotFoundException e){
            return 0;
        }
    }

    @RequestMapping(value = "/user/saveOrder", method = RequestMethod.POST, consumes = "application/json")
    public void saveOrder(@RequestBody SimpleOrder[] simpleOrders){
        UserImpl user =  userService.getCurrentUser();
        orderService.addMultipleItemsToOneOrder(user, simpleOrders);
    }

    @RequestMapping(value = "/user/deleteOrder/{id}", method = RequestMethod.GET)
    public void DeleteOrder(@PathVariable Integer id){
        orderService.removeOrder(userService.getCurrentUser(), id);
    }

}
