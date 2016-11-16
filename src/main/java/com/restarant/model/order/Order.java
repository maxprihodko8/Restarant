package com.restarant.model.order;

import javax.naming.NameNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Order {
    private HashMap<String, Integer> dishesForOrder = new HashMap<String, Integer>();
    private Integer id = new Random().nextInt();

    public void addDish(String dish, int number){
        Integer integer =  dishesForOrder.get(dish);
        if (integer == null)
            dishesForOrder.put(dish,number);
    }

    public void removeDish(String dish){
        dishesForOrder.remove(dish);
    }

    public HashMap <String, Integer> getMapOfDishes(){
        return dishesForOrder;
    }

    public Integer getDishVal(String dish) throws NameNotFoundException {
        Integer integer =  dishesForOrder.get(dish);
        if (integer != null){
            return integer;
        }
        else {
            throw new NameNotFoundException();
        }
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }


    // @TODO only uniq vals, HAshmap not th best variant
}

