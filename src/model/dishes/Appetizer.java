package model.dishes;

public class Appetizer extends Dish{

    public Appetizer(String name, double price) {
        super(name, "Appetizer", price, 10);
    }

    public Appetizer(String name, double price, Boolean isPrepared) {
        super(name, "Appetizer", price, isPrepared, 10);
    }

}
