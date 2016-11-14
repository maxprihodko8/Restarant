package com.restarant.model.dish;

public class Dish {
    public int id;
    public String name;
    public int price;
    public String type;

    public Dish(){
        id = 0;
        name = "";
        price = 0;
        type = "";
    }

    public Dish(int id, String name, int price, String type){
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Dish(String name, int price, String type){
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
