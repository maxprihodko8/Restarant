package com.restarant.model.repository;

import com.restarant.controller.service.DishService;
import com.restarant.model.dish.Dish;
import com.restarant.model.order.Order;
import com.restarant.model.user.UserImpl;

import javax.naming.NameNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListOrders {

    private DishService dishService;

    ListOrders (DishService dishService){
        this.dishService = dishService;
    }

    private Map <UserImpl, ArrayList<Order>> orders = new HashMap<UserImpl, ArrayList<Order>>();

    public void addOrder(UserImpl user, Order order) {
        try {
            getOrdersOfUser(user).add(order);
        } catch (NameNotFoundException e) {
            orders.get(user).add(order);
        }
    }

    public List<Order> getOrdersOfUser(UserImpl user) throws NameNotFoundException {
        //ArrayList <Order> ordersOfUser = orders.get(user);
        for (Map.Entry<UserImpl, ArrayList<Order>> entry : orders.entrySet()){
            UserImpl nextUser = entry.getKey();
            if(nextUser.getName().equals(user.getName())){
                /*ArrayList <Order> arraToChange =  entry.getValue();
                ArrayList <String> resultArray = new ArrayList<String>();
                for(Order o : arraToChange){

                }*/
                return entry.getValue();
            }
        }
        throw new NameNotFoundException();
        /*if (ordersOfUser != null){
            return ordersOfUser;
        } else {
            throw new NameNotFoundException();
        }*/
    }

    public int submitPriceForOrders(UserImpl user) throws NameNotFoundException {
        try {
            ArrayList<Order> orderArrayList = new ArrayList<Order>();
            orderArrayList.addAll(getOrdersOfUser(user));
            ArrayList<Dish> dishes = new ArrayList<Dish>();
            dishes.addAll(dishService.getDishList());
            return calcSum(orderArrayList, dishes);
        } catch (NameNotFoundException e){
            throw new NameNotFoundException();
        } catch (NullPointerException e){
            throw new NameNotFoundException();
        }
    }

    public int submitPriceForOrders(List<Order> orderList) throws NameNotFoundException {
        try {
            ArrayList<Dish> dishes = new ArrayList<Dish>();
            dishes.addAll(dishService.getDishList());
            return calcSum(orderList, dishes);
        } catch (NullPointerException e){
            throw new NameNotFoundException();
        }
    }

    private int calcSum(List<Order> orderList, List<Dish> dishes){
        int result = 0;
        for (Order o : orderList){
            HashMap<String, Integer> hashMap =  o.getMapOfDishes();
            for(Dish d : dishes){
                for (Map.Entry<String, Integer> entry : hashMap.entrySet()){
                    String dishName = entry.getKey();
                    if(dishName.equals(d.getName())){
                        result += d.getPrice() * entry.getValue();
                    }
                }
            }
        }
        return result;
    }

    public void removeOrder(UserImpl user, Order order) throws NameNotFoundException {
        try {
            getOrdersOfUser(user).remove(order);
        } catch (NullPointerException e){
            throw new NameNotFoundException();
        }
    }

    public void removeOrder(UserImpl user, Integer id){
        try {
            for (Order o : getOrdersOfUser(user)){
                if(o.getId().equals(id)){
                    getOrdersOfUser(user).remove(o);
                    break;
                }
            }
        } catch (NameNotFoundException e){
        }
    }

    public void removeOrdersOfUser(UserImpl user){
        orders.remove(user);
    }

    public void addSomeData(){
        UserImpl user1 = new UserImpl();
        UserImpl user2 = new UserImpl();
        UserImpl user3 = new UserImpl();
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        order1.addDish("Курица", 2);
        order1.addDish("Суп", 1);
        order2.addDish("Суп", 4);
        order2.addDish("Чай", 1);
        order3.addDish("Компот", 3);
        user1.setParams(2,"afaffa", "", false);
        user2.setParams(4,"m", "", false);
        user3.setParams(5,"mfff", "fffff", false);
        ArrayList <Order> a1 = new ArrayList<Order>();
        a1.add(order1);
        a1.add(order2);
        orders.put(user1, a1);

        ArrayList <Order> a2 = new ArrayList<Order>();
        a2.add(order1);
        a2.add(order3);
        orders.put(user2, a2);

        ArrayList <Order> a3 = new ArrayList<Order>();
        a3.add(order3);
        a3.add(order2);
        orders.put(user1, a3);
    }
}
