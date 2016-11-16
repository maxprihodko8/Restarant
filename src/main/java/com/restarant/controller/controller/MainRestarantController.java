
package com.restarant.controller.controller;
import com.restarant.model.sql.dishSql.DishDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.restarant.model.user.UserImpl;

@Controller
public class MainRestarantController {
    @Autowired
    DishDAOImpl dishDAO;

        @RequestMapping(value = "/", method = RequestMethod.GET)
        public ModelAndView main() {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("userData", new UserImpl());
            modelAndView.addObject("dishList", dishDAO.list());
            modelAndView.setViewName("mainPage");
            return modelAndView;
        }
}
