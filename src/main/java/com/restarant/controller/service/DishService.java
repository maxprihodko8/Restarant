package com.restarant.controller.service;

import com.restarant.model.dish.Dish;
import com.restarant.model.repository.DishRepository;
import com.restarant.model.sql.dishSql.DishDAOImpl;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;

@Service
public class DishService {

    private DishDAOImpl dishDAO;
    private DishRepository dishRepository;

    public void init(){
        dishRepository.init(dishDAO.list());
    }

    DishService(DishDAOImpl dishDAO, DishRepository dishRepository) {
        this.dishDAO = dishDAO;
        this.dishRepository = dishRepository;
    }

    public void updateDishList(){
        dishRepository.updateDishList(dishDAO.list());
    }

    public Dish getDishByName(String name) throws NameNotFoundException {
        /*try {
            return dishRepository.getDishByName(name);
        } catch (NameNotFoundException e){
            try {
                return dishDAO.getByName(name);
            } catch (NameNotFoundException e1){
                throw new NameNotFoundException();
            }
        }*/
        try {
            return dishDAO.getByName(name);
        } catch (NameNotFoundException e1){
            throw new NameNotFoundException();
        }
    }

    public Dish getDish(int id) throws NameNotFoundException {
        /*try {
            return dishRepository.getDish(id);
        } catch (NameNotFoundException e){
            try {
                return dishDAO.get(id);
            } catch (NameNotFoundException e1){
                throw new NameNotFoundException();
            }
        }*/
        try {
            return dishDAO.get(id);
        } catch (NameNotFoundException e){
            throw new NameNotFoundException();
        }
    }

    public List<Dish> getDishList() {
        return dishDAO.list();   // @TODO change to userRepo
    }

    public void addDish(Dish dish) {
        dishDAO.saveOrUpdate(dish.getName() ,dish);
        //dishRepository.addDish(dish);
    }

    public void updateDish(String name, Dish dish){
        //dishRepository.updateDish(name, dish);
        dishDAO.saveOrUpdate(name, dish);
    }

    public void updateDish(Dish dish){
        //dishRepository.updateDish(name, dish);
        dishDAO.saveOrUpdate(dish);
    }

    public void deleteDish(String name){
        dishDAO.delete(name);
        //dishRepository.deleteDish(name);
    }

    public void deleteDish(int id){
        dishDAO.delete(id);
    }

    public Dish getDefaultDish(){
        Dish dish = new Dish();
        return dish;
    }

}
