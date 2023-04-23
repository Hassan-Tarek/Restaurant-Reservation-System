package model.users;

import model.Bill;
import model.Menu;
import model.Order;
import model.Table;

public abstract class Customer extends User {

    private Menu menu;
    private Table table;
    private Order order;
    private Bill bill;
    private double discount;

    public Customer(String firstName, String lastName, String userName,
                    String password, String role) {
        super(firstName, lastName, userName, password, role);
        table = null;
        order = null;
    }

    public Customer(String firstName, String lastName, String userName,
                    String password, String role, Menu menu,
                    Table table, Order order, Bill bill) {
        super(firstName, lastName, userName, password, role);
        setMenu(menu);
        setTable(table);
        setOrder(order);
        setBill(bill);
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Bill getBill() {
//        if(this.order == null)
//            this.bill = null;
//        else if(bill == null) {
//            this.setBill(new Bill(this.table.getTableNumber(), this.order.getDishes(), discount));
//            Restaurant.addBill(bill);
//        }
//        return this.bill;
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Bill askForBill() {
        if(order == null)
            return null;
        else if(bill == null)
            this.bill = new Bill(table.getTableNumber(), order.getDishes(), discount);
        return bill;
    }
}
