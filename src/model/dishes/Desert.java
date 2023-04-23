package model.dishes;

public class Desert extends Dish{

    public Desert(String name, double price) {
        super(name, "Desert", price, 20);
    }

    public Desert(String name, double price, boolean isPrepared) {
        super(name, "Desert", price, isPrepared, 20);
    }

}
