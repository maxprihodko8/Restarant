package com.restarant.model.order;

import com.restarant.model.dish.Dish;

import javax.naming.NameNotFoundException;
import java.util.*;

public class Order {
    //private Map<Dish, Integer> dishesForOrder = new HashMap<Dish, Integer>();
    private List<OrderWithDish> dishesForOrder = new ArrayList<>();
    private Integer id = new Random().nextInt();
    private String creator;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<OrderWithDish> getDishesForOrder() {
        return dishesForOrder;
    }

    public void setDishesForOrder(List<OrderWithDish> order) {
        this.dishesForOrder = order;
    }

    public void addDish(Dish dish, int number) {
        for(OrderWithDish o : dishesForOrder){
            if(o.getDish().equals(dish)) {
                dishesForOrder.remove(o);
            }
        }
        dishesForOrder.add(new OrderWithDish(dish, number));
    }

    public void removeDish(Dish dish){
        dishesForOrder.remove(dish);
    }

    public Integer getDishVal(Dish dish) throws NameNotFoundException {
        for(OrderWithDish o : dishesForOrder){
            if(o.getDish().equals(dish))
                return o.getCount();
        }
        throw new NameNotFoundException();
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
        } else if(object instanceof Order){
            if(this.id.equals(((Order) object).getId())){
                for(OrderWithDish o1 : ((Order) object).dishesForOrder){
                    boolean found = false;
                    for(OrderWithDish o2 : this.dishesForOrder){
                        if(o1.getDish().equals(o2.getDish()) && o1.getCount() == o2.getCount()){
                            found = true;
                        }
                        if(!found)
                            return false;
                    }
                }
                return true;
            }
        }
        return false;
        /* else if (object instanceof Order){
            Order o = (Order) object;
            if(o.getId().equals(this.getId())){
                for(Map.Entry<Dish, Integer> thisEntry : this.dishesForOrder.entrySet()){
                    boolean found = false;
                    for(Map.Entry<Dish, Integer> objEntry : o.dishesForOrder.entrySet()){
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
        return false;*/
    }


    // @TODO only uniq vals, HAshmap not th best variant
}

