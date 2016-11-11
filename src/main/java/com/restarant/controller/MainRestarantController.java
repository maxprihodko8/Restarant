
package com.restarant.controller;
import com.restarant.controller.security.SecurityService;
import com.restarant.controller.service.UserService;
import com.restarant.model.repository.UserRepository;
import com.restarant.model.sql.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.restarant.model.user.UserImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.List;

@Controller
public class MainRestarantController {

        @RequestMapping(value = "/", method = RequestMethod.GET)
        public ModelAndView main() {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("userData", new UserImpl());
            modelAndView.setViewName("mainPage");
            return modelAndView;
        }

        @RequestMapping(value = "/user/userMainPage")
        public ModelAndView userMainPage(){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/user/userMainPage");
            modelAndView.addObject("userData", new UserImpl());
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


}
