package com.restarant.model.order;

import com.sun.org.apache.xpath.internal.operations.Or;

import javax.naming.NameNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Order {
    private HashMap<String, Integer> dishesForOrder = new HashMap<String, Integer>();
    private Integer id = new Random().nextInt();

    public void addDish(String dish, int number){
        Integer integer =  dishesForOrder.get(dish);
        if (integer == null)
            dishesForOrder.put(dish,number);
        else {
            dishesForOrder.merge(dish, number, Integer::sum);
        }
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

    public HashMap<String, Integer> returnDishes(){
        return dishesForOrder;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        } else if (object instanceof Order){
            Order o = (Order) object;
            if(o.getId().equals(this.getId())){
                for(Map.Entry<String, Integer> thisEntry : this.dishesForOrder.entrySet()){
                    boolean found = false;
                    for(Map.Entry<String, Integer> objEntry : o.dishesForOrder.entrySet()){
                        if(thisEntry.getValue().equals(objEntry.getValue()) && thisEntry.getKey().equals(objEntry.getKey())){
                            found = true;
                        }
                    }
                    if(!found){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }


    // @TODO only uniq vals, HAshmap not th best variant
}

