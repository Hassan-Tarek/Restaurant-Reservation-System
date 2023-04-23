package model.dishes;

public class MainCourse extends Dish{

    public MainCourse(String name, double price) {
        super(name, "Main Course", price, 15);
    }

    public MainCourse(String name, double price, boolean isPrepared) {
        super(name, "Main Course", price, isPrepared, 15);
    }

}
