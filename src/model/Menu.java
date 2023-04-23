package model;

import model.dishes.Dish;

import java.util.List;

public class Menu {

    private List<Dish> dishes;

    public Menu(List<Dish> dishes) {
        setDishes(dishes);
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Menu: " + "\n");
        for(Dish dish : getDishes()) {
            str.append("Dish Name: ").append(dish.getName()).append("     ").append("Price: ").append(dish.getPrice()).append("\n");
        }
        return str.toString();
    }
}
