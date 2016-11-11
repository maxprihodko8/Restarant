package com.restarant.model.dish;

import com.restarant.model.dish.someDishes.drink.Compote;
import com.restarant.model.dish.someDishes.drink.Tea;
import com.restarant.model.dish.someDishes.first.FishWithNothing;
import com.restarant.model.dish.someDishes.first.MeatWithPotato;
import com.restarant.model.dish.someDishes.first.Soup;
import com.restarant.model.dish.someDishes.first.VegetablesWithShrimps;
import com.restarant.model.dish.someDishes.second.FriedPotatoWithChop;
import com.restarant.model.dish.someDishes.second.Salad;
import org.junit.Test;
import org.springframework.security.access.method.P;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;


public class DishList {
    HashSet <Dish> dishes = new HashSet<Dish>();

    public DishList(){

        //dishes.add(new Compote());
        dishes.add(new Tea());
        dishes.add(new FishWithNothing());
        dishes.add(new MeatWithPotato());
        dishes.add(new Soup());
        dishes.add(new VegetablesWithShrimps());
        dishes.add(new FishWithNothing());
        dishes.add(new FriedPotatoWithChop());
        dishes.add(new Salad());
    }

    public HashSet<Dish> getDishes(){
        return dishes;
    }




    @Test
    public void getListForTest(){
        HashSet <Dish> dishes = new HashSet<Dish>();
        File f = null;
        File [] paths;
        File [] results = new File[100];
        ArrayList list = new ArrayList();
        System.out.println(System.getProperty("user.dir"));
        try{
            f = new File("src/main/java/com/restarant/model/dish/someDishes");
            paths = f.listFiles();

            for(File f1 : paths){
                if(f1.isDirectory()){
                    list.add(f1.listFiles());
                }
            }
            for(int i=0; i < list.size(); i++) {

            }

        } catch (Exception e){
            System.out.println("Files not found in Dish directory");
        }
    }
}
