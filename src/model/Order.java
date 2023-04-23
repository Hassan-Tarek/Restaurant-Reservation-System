package model;

import model.dishes.Dish;

import java.util.LinkedList;
import java.util.List;

public class Order {

    private final int tableNumber;
    private final List<Dish> dishes;
    private boolean isReserved;

    public Order(int tableNumber, List<Dish> dishes, boolean isReserved) {
        this.tableNumber = tableNumber;
        this.dishes = dishes;
        this.isReserved = isReserved;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public List<Dish> getPreparedDishes() {
        List<Dish> preparedDishes = new LinkedList<>();
        for (Dish dish : getDishes()) {
            if(dish.isPrepared())
                preparedDishes.add(dish);
        }
        return preparedDishes;
    }

    public List<Dish> getUnPreparedDishes() {
        List<Dish> unPreparedDishes = new LinkedList<>();
        for (Dish dish : getDishes()) {
            if(!dish.isPrepared())
                unPreparedDishes.add(dish);
        }
        return unPreparedDishes;
    }

    private boolean checkIsPrepared() {
        boolean isPrepared = true;
        if(getPreparedDishes().size() != getDishes().size())
            isPrepared = false;

        return isPrepared;
    }

    public boolean isPrepared () {
        return checkIsPrepared();
    }

}
