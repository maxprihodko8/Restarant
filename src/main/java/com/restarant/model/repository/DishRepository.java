package com.restarant.model.repository;

import com.restarant.model.dish.Dish;
import com.restarant.model.sql.dishSql.DishDAOImpl;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Service
public class DishRepository {
    private HashSet <Dish> dishList = new HashSet<Dish>();

    public DishRepository(){
    }

    public void init(List<Dish> list){
        updateDishList(list);
    }

    public HashSet<Dish> getDishList(){
        return dishList;
    }

    public Dish getDishByName(String dish) throws NameNotFoundException {
        Iterator i = dishList.iterator();
        while (i.hasNext()){
            Dish d = (Dish) i.next();
            if( d.name.equals(dish) )
                return d;
        }
        throw new NameNotFoundException();
    }

    public Dish getDish(int id) throws NameNotFoundException {
        Iterator i = dishList.iterator();
        while (i.hasNext()){
            Dish d = (Dish) i.next();
            if( d.id == id )
                return d;
        }
        throw new NameNotFoundException();
    }

    public void addDish(Dish dish){
        try {
            getDishByName(dish.name);
        } catch (NameNotFoundException e){
            dishList.add(dish);
        }
    }

    public void updateDish(String name, Dish dish) {
        Iterator i = dishList.iterator();
        while (i.hasNext()){
            Dish d = (Dish) i.next();
            if (d.name == name) {
                dishList.remove(d);
                dishList.add(dish);
                break;
            }
        }
    }

    public void updateDishList(List<Dish> dishes){
        if(dishes != null){
            Iterator i = dishes.iterator();
            HashSet <Dish> newDishList = new HashSet<Dish>();
            while (i.hasNext()){
                newDishList.add((Dish)i.next());
            }
        }
    }

    public void deleteDish(String name){
        Iterator i = dishList.iterator();
        while (i.hasNext()){
            Dish d = (Dish) i.next();
            if (d.name.equals(name)){
                dishList.remove(d);
            }
        }
    }

    public void fillSomeData(){
        dishList.add(new Dish("Soup", 20, "first"));
        dishList.add(new Dish("VegetablesWithShrimps", 55, "first"));
        dishList.add(new Dish("MeatWithPotato", 30, "first"));
        dishList.add(new Dish("Fish", 15, "first"));

        dishList.add(new Dish("FishAndChips", 25, "second"));
        dishList.add(new Dish("FriedPotatoWithChop", 20, "second"));
        dishList.add(new Dish("Salad", 20, "second"));

        dishList.add(new Dish("Compote", 20, "third"));
        dishList.add(new Dish("Tea", 20, "third"));
        dishList.add(new Dish("Juice", 20, "first"));
    }

}
