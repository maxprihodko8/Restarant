package com.restarant.controller;

import com.restarant.controller.service.UserService;
import com.restarant.model.user.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegistrationController {


    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userData", new UserImpl());
        modelAndView.addObject("messageError", "");
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public ModelAndView tryLogin(@ModelAttribute("userData") UserImpl user){
        try{
            userService.getUser(user.getName());
            //securityService.autologin(user.getName(), user.getPassword());
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("userData", user);
            modelAndView.setViewName("secondPage");
            return modelAndView;
        } catch (UsernameNotFoundException e){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("userData", new UserImpl());
            modelAndView.setViewName("login");
            modelAndView.addObject("messageError", "Вы не зашли в систему или не зарегистрированы!");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("messageError", "");
        modelAndView.addObject("userData", new UserImpl());
        return modelAndView;
    }

    @RequestMapping(value = "/check-registration", method = RequestMethod.POST)
    public ModelAndView checkRegistration(@ModelAttribute("userData") UserImpl user){
        ModelAndView modelAndView = new ModelAndView();
        try {
            userService.getUser(user.getName());
            modelAndView.addObject("messageError", "Этот пользователь уже зарегистрирован");
            modelAndView.setViewName("registration");
            return modelAndView;
        } catch (UsernameNotFoundException e){
            userService.saveUser(user);
            modelAndView.addObject("messageError", "Можете входить в систему, вы зарегистрированы!");
            modelAndView.setViewName("login");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest httpServletRequest){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/logout");
        userService.logOutUser();
        httpServletRequest.getSession().invalidate();
        return modelAndView;
    }

    @RequestMapping(value = "/successLogin")
    public ModelAndView userPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userData", new UserImpl());
        modelAndView.setViewName("/user/userMainPage");
        userService.logInUser();
        return modelAndView;
    }
}
