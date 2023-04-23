package model;

import model.dishes.Dish;
import model.users.Cooker;
import model.users.Customer;
import model.users.Employee;
import model.users.Manager;
import model.users.NonPremiumCustomer;
import model.users.PremiumCustomer;
import model.users.User;
import model.users.Waiter;
import model.filehandler.Load;
import model.filehandler.Save;

import java.util.LinkedList;
import java.util.List;

public class Restaurant {

    private final String filePath = "src//data.xml";
    private static Menu menu;
    private static List<User> users;
    private static List<Table> tables;
    private static List<Order> orders;
    private static List<Bill> bills;

    private static Restaurant restaurant;
    private Restaurant() {
    }
    public static Restaurant getInstance() {
        if (restaurant == null)
            restaurant = new Restaurant();
        return restaurant;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        Restaurant.menu = menu;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        Restaurant.users = users;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        Restaurant.tables = tables;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        Restaurant.orders = orders;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        Restaurant.bills = bills;
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new LinkedList<>();
        for(User user : this.getUsers()) {
            if(user instanceof Customer)
                customers.add((Customer) user);
        }
        return customers;
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = new LinkedList<>();
        for(User user : this.getUsers()) {
            if(user instanceof Employee)
                employees.add((Employee) user);
        }
        return employees;
    }

    public List<Order> getUnPreparedOrders() {
        List<Order> orders = new LinkedList<>();
        for(Order order : getOrders()) {
            if(!order.isPrepared())
                orders.add(order);
        }

        return orders;
    }

    public List<Dish> getUnPreparedDishes(int tableNumber) {
        List<Dish> dishes = new LinkedList<>();
        for(Order order : getOrders()) {
            if(order.getTableNumber() == tableNumber) {
                dishes.addAll(order.getUnPreparedDishes());
            }
        }

        return dishes;
    }

    public List<Order> getOrdersToBeReserved() {
        List<Order> orders = new LinkedList<>();
        for(Order order : getOrders()) {
            if(!order.isReserved() && order.isPrepared()) {
                orders.add(order);
            }
        }

        return orders;
    }

    public double getTotalEarnedMoney() {
        double total = 0;
        for(Bill bill : getBills()) {
            total += bill.getTotalPrice();
        }
        return total;
    }

    public static Customer getCustomer(int tableNumber) {
        for(User user : users) {
            if(user instanceof Customer) {
                Customer customer = (Customer) user;
                if(customer.getTable().getTableNumber() == tableNumber) {
                    return customer;
                }
            }
        }
        return null;
    }

    public static Table getTable(int tableNumber) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableNumber)
                return table;
        }
        return null;
    }

    public List<Table> getAvailableTables() {
        List<Table> tables = new LinkedList<>();
        for(Table table : getTables()) {
            if(!table.isBooked())
                tables.add(table);
        }

        return tables;
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void addUser(String firstname, String lastname, String username, String password, String role) {
        User user;
        user = switch (role) {
            case "Manager" -> new Manager(firstname, lastname, username, password);
            case "Waiter" -> new Waiter(firstname, lastname, username, password);
            case "Cooker" -> new Cooker(firstname, lastname, username, password);
            case "Premium Customer" -> new PremiumCustomer(firstname, lastname, username, password);
            case "NonPremium Customer" -> new NonPremiumCustomer(firstname, lastname, username, password);
            default -> throw new IllegalStateException("Unexpected value: " + role);
        };

        Restaurant.users.add(user);
    }

    public Table findTable(int tableNumber) {
        for(Table table : getTables()) {
            if(table.getTableNumber() == tableNumber)
                return table;
        }
        return null;
    }

    public int findUser(String username, String password) {
        int index = -1;
        for(int i = 0; i < getUsers().size(); i++) {
            if(getUsers().get(i).getUserName().equals(username) && getUsers().get(i).getPassword().equals(password)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int findUser(String username) {
        int index = -1;
        for(int i = 0; i < getUsers().size(); i++) {
            if(getUsers().get(i).getUserName().equals(username)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public Order findOrder(int tableNumber) {
        for(Order order : getOrders()) {
            if(order.getTableNumber() == tableNumber) {
                return order;
            }
        }
        return null;
    }

    public Bill findBill(int tableNumber) {
        for(Bill bill : getBills()) {
            if(bill.getTableNumber() == tableNumber) {
                return bill;
            }
        }
        return null;
    }

    public void load() {
        Load load = new Load(filePath, this);
        load.loadData();
    }

    public void save() {
        Save save = new Save(filePath, this);
        save.saveData();
    }

}
