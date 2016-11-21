package com.restarant.controller.controller;

import com.restarant.controller.service.DishService;
import com.restarant.controller.service.GroupOrderService;
import com.restarant.controller.service.UserService;
import com.restarant.model.dish.Dish;
import com.restarant.model.group.UserGroup;
import com.restarant.model.user.UserImpl;
import com.sun.javafx.sg.prism.NGShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.NameNotFoundException;
import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@RestController
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    DishService dishService;

    @Autowired
    GroupOrderService groupOrderService;

    @RequestMapping(value = "/admin/mainPage")
    public ModelAndView getAdminMainPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/mainPage");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/userList")
    public ModelAndView getUserList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/userList");
        List <UserImpl> users = userService.getCurrentLoggedUserList();
        //modelAndView.addObject("userList", users);
        modelAndView.addObject("userListWithOnlineTag", userService.getUsersListWithOnlineTag());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/userGroups", method = RequestMethod.GET)
    public ModelAndView userGroupsList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/userGroups");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/listAllUserGroups", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<UserGroup> getListOfuserGroups(){
         return  groupOrderService.list();
    }

    @RequestMapping(value = "/admin/edit/{id}")
    public ModelAndView editDish(@PathVariable("id") int id, @ModelAttribute Dish dish){
        dishService.updateDish(dish.name, dish);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/dishList");
        modelAndView.addObject("dishList", dishService.getDishList());
        modelAndView.addObject("error", "Изменения сохранены!");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/delete")
    public ModelAndView deleteDish(@RequestParam String name){
        dishService.deleteDish(name);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/dishList");
        modelAndView.addObject("error", "Удалено успешно");
        return modelAndView;
    }

    @RequestMapping(value = "/dishList", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Dish> getDishes(){
        List <Dish> dishes = new ArrayList<Dish>();
        dishes = dishService.getDishList();
        return dishes;
    }

    @RequestMapping(value = "/admin/dishList", method = RequestMethod.GET)
    public ModelAndView getDishList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/dishList");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/dish/{id}",method = RequestMethod.GET, headers = "Accept=application/json")
    public Dish getDishById(@PathVariable int id){
        try {
            return dishService.getDish(id);
        } catch (NameNotFoundException e){
            return dishService.getDefaultDish();
        }
    }

    @RequestMapping(value = "/admin/addDish", method = RequestMethod.POST, headers = "Accept=application/json")
    public Dish addDish(@RequestBody Dish dish){
        dishService.addDish(dish);
        return dish;
    }


    @RequestMapping(value = "/admin/dishEdit", method = RequestMethod.POST, headers = "Accept=application/json")
    public Dish editDish(@RequestBody Dish dish){
        dishService.updateDish(dish);
        return dish;
    }

    @RequestMapping(value = "/admin/dishDel/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public void deleteDish(@PathVariable("id") int id){
        dishService.deleteDish(id);
    }

}
