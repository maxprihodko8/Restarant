package com.restarant.model.order;

import com.restarant.model.dish.Dish;

public class OrderWithDish {
    private Dish dish;
    private int count;

    public OrderWithDish (Dish dish, int count){
        this.dish = dish;
        this.count = count;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
