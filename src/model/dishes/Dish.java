package model.dishes;

public abstract class Dish {

    private String name;
    private final String type;
    private double price;
    private boolean isPrepared;
    private final int tax;

    public Dish(String name, String type, double price, int tax) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.isPrepared = false;
        this.tax = tax;
    }

    public Dish(String name, String type, double price, boolean isPrepared, int tax) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.isPrepared = isPrepared;
        this.tax = tax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public void setPrepared(boolean prepared) {
        isPrepared = prepared;
    }

    public int getTax() {
        return tax;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", type='" + type +
                '}';
    }
}
