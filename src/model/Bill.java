package model;

import model.dishes.Dish;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Bill {

    private final int tableNumber;
    private final String orderDate;
    private List<Dish> orderDishes;
    private final double discount;
    private final double totalPrice;

    public Bill(int tableNumber, List<Dish> orderDishes, double discount) {
        this.tableNumber = tableNumber;
        this.orderDishes = orderDishes;
        this.discount = discount;
        this.totalPrice = calcTotalPrice();

        String patten = "E, dd MMM yyyy HH:mm:ss";
        DateFormat format = new SimpleDateFormat(patten);
        this.orderDate = format.format(new Date());
    }

    public Bill(int tableNumber, List<Dish> orderDishes, String orderDate, double discount) {
        this.tableNumber = tableNumber;
        this.discount = discount;
        setOrderDishes(orderDishes);
        this.orderDate = orderDate;
        this.totalPrice = calcTotalPrice();
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public List<Dish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<Dish> orderDishes) {
        this.orderDishes = orderDishes;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private Double calcTotalPrice() {
        double totalPrice = 0;
        for(Dish dish : getOrderDishes()) {
            totalPrice += dish.getPrice() * dish.getTax() / 100f;
        }
        totalPrice -= totalPrice * this.discount / 100f;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        totalPrice = Double.parseDouble(decimalFormat.format(totalPrice));

        return totalPrice;
    }

    @Override
    public String toString() {
        StringBuilder str;
        str = new StringBuilder("Customer Name: " + Restaurant.getCustomer(tableNumber) + "\n"
                + getOrderDate() + "\n");
        for(Dish dish : getOrderDishes()) {
            str.append(dish.getName()).append(", ").append(dish.getType()).append(", ").append(dish.getPrice()).append("\n");
        }
        str.append(String.format("%.2f", getTotalPrice()));

        return str.toString();
    }

}
