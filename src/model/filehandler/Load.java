package model.filehandler;

import model.Bill;
import model.dishes.Appetizer;
import model.dishes.Desert;
import model.dishes.Dish;
import model.dishes.MainCourse;
import model.Menu;
import model.Order;
import model.Restaurant;
import model.Table;
import model.users.Cooker;
import model.users.Customer;
import model.users.Employee;
import model.users.Manager;
import model.users.NonPremiumCustomer;
import model.users.PremiumCustomer;
import model.users.User;
import model.users.Waiter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Load {

    private final Restaurant restaurant;
    private Document document;

    public Load(String path, Restaurant restaurant) {
        this.restaurant = restaurant;
        File xmlFile = new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        restaurant.setTables(loadTables());

        restaurant.setMenu(new Menu(loadDishes()));

        restaurant.setOrders(loadOrders());

        restaurant.setBills(loadBills());

        restaurant.setUsers(loadUsers());
    }

    private List<User> loadUsers() {
        List<User> users = new LinkedList<>();
        users.addAll(loadEmployees());
        users.addAll(loadCustomers());

        return users;
    }

    private List<Employee> loadEmployees() {
        List<Employee> employees = new LinkedList<>();

        NodeList list = document.getElementsByTagName("employee");
        for(int i = 0; i < list.getLength(); ++i) {
            Node node = list.item(i);

            String firstName, lastName, userName, password, role;
            double salary;
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                firstName = element.getElementsByTagName("firstname").item(0).getTextContent();
                lastName = element.getElementsByTagName("lastname").item(0).getTextContent();
                userName = element.getElementsByTagName("username").item(0).getTextContent();
                password = element.getElementsByTagName("password").item(0).getTextContent();
                role = element.getElementsByTagName("role").item(0).getTextContent();
                salary = Double.parseDouble(element.getElementsByTagName("salary").item(0).getTextContent());

                Employee employee = null;
                switch (role) {
                    case "Manager" -> employee = new Manager(firstName, lastName, userName, password);
                    case "Waiter" -> employee = new Waiter(firstName, lastName, userName, password);
                    case "Cooker" -> employee = new Cooker(firstName, lastName, userName, password);
                }
                assert employee != null;
                employee.setSalary(salary);

                employees.add(employee);
            }
        }

        return employees;
    }

    private List<Customer> loadCustomers() {
        List<Customer> customers = new LinkedList<>();

        NodeList list = document.getElementsByTagName("customer");
        for(int i = 0; i < list.getLength(); ++i) {
            Node node = list.item(i);

            String firstName, lastName, userName, password, role;
            int tableNumber;
            double discount;
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                firstName = element.getElementsByTagName("firstname").item(0).getTextContent();
                lastName = element.getElementsByTagName("lastname").item(0).getTextContent();
                userName = element.getElementsByTagName("username").item(0).getTextContent();
                password = element.getElementsByTagName("password").item(0).getTextContent();
                role = element.getElementsByTagName("role").item(0).getTextContent();
                String s = element.getElementsByTagName("table_number").item(0).getTextContent();
                tableNumber = s.equals("") ? -1 : Integer.parseInt(s);
                discount = Double.parseDouble(element.getElementsByTagName("discount").item(0).getTextContent());

                Customer customer;
                if(tableNumber == -1) {
                    customer = switch (role) {
                        case "Premium Customer" -> new PremiumCustomer(firstName, lastName, userName, password);
                        case "NonPremium Customer" -> new NonPremiumCustomer(firstName, lastName, userName, password);
                        default -> throw new IllegalStateException("Unexpected value: " + role);
                    };
                }
                else {
                    customer = switch (role) {
                        case "Premium Customer" -> new PremiumCustomer(firstName, lastName, userName, password,
                                restaurant.getMenu(), restaurant.findTable(tableNumber),
                                restaurant.findOrder(tableNumber), restaurant.findBill(tableNumber));
                        case "NonPremium Customer" -> new NonPremiumCustomer(firstName, lastName, userName, password,
                                restaurant.getMenu(), restaurant.findTable(tableNumber),
                                restaurant.findOrder(tableNumber), restaurant.findBill(tableNumber));
                        default -> throw new IllegalStateException("Unexpected value: " + role);
                    };
                }

                customer.setDiscount(discount);

                customers.add(customer);
            }
        }

        return customers;
    }

    private List<Dish> loadDishes() {
        List<Dish> dishes = new LinkedList<>();

        NodeList list = document.getElementsByTagName("dish");
        for(int i = 0; i < list.getLength(); ++i) {
            Node node = list.item(i);

            String name, type;
            double price;
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                name = element.getElementsByTagName("name").item(0).getTextContent();
                type = element.getElementsByTagName("type").item(0).getTextContent();
                price = Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent());

                Dish dish = null;
                switch (type) {
                    case "Main Course" -> dish = new MainCourse(name, price);
                    case "Appetizer" -> dish = new Appetizer(name, price);
                    case "Desert" -> dish = new Desert(name, price);
                }

                if(dish != null)
                    dishes.add(dish);
            }
        }

        return dishes;
    }

    private List<Bill> loadBills() {
        List<Bill> bills = new LinkedList<>();

        NodeList list = document.getElementsByTagName("bill");
        for(int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            String date;
            int tableNumber;
            double discount;
            NodeList dishesList = document.getElementsByTagName("bill_dish");
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                tableNumber = Integer.parseInt(element.getElementsByTagName("table_number").item(0).getTextContent());
                date = element.getElementsByTagName("date").item(0).getTextContent();
                discount = Double.parseDouble(element.getElementsByTagName("bill_discount").item(0).getTextContent());

                List<Dish> dishes = new LinkedList<>();
                for(int j = 0; j < dishesList.getLength(); j++) {
                    Node dishNode = dishesList.item(j);

                    String name, type;
                    double price;
                    if(dishNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element dishElement = (Element) dishNode;

                        // check if this dish belong to this customer if not it will not be added
                        if(!dishElement.getAttribute("id").equals(String.valueOf(tableNumber)))
                            continue;

                        name = dishElement.getElementsByTagName("name").item(0).getTextContent();
                        type = dishElement.getElementsByTagName("type").item(0).getTextContent();
                        price = Double.parseDouble(dishElement.getElementsByTagName("price").item(0).getTextContent());

                        Dish dish = null;
                        switch (type) {
                            case "Main Course" -> dish = new MainCourse(name, price);
                            case "Appetizer" -> dish = new Appetizer(name, price);
                            case "Desert" -> dish = new Desert(name, price);
                        }

                        if(dish != null)
                            dishes.add(dish);
                    }
                }

                Bill bill = new Bill(tableNumber, dishes, date, discount);
                bills.add(bill);
            }
        }

        return bills;
    }

    private List<Order> loadOrders() {
        List<Order> orders = new LinkedList<>();

        NodeList list = document.getElementsByTagName("order");
        for(int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            int tableNumber;
            NodeList dishesList = document.getElementsByTagName("order_dish");
            boolean is_reserved;
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                tableNumber = Integer.parseInt(element.getElementsByTagName("table_number").item(0).getTextContent());
                is_reserved = Boolean.parseBoolean(element.getElementsByTagName("is_reserved").item(0).getTextContent());

                List<Dish> dishes = new LinkedList<>();
                for(int j = 0; j < dishesList.getLength(); j++) {
                    Node dishNode = dishesList.item(j);

                    String name, type;
                    double price;
                    boolean isPrepared;
                    if(dishNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element dishElement = (Element) dishNode;

                        // check if this dish belong to this customer if not it will not be added
                        if(!dishElement.getAttribute("id").equals(String.valueOf(tableNumber)))
                            continue;

                        name = dishElement.getElementsByTagName("name").item(0).getTextContent();
                        type = dishElement.getElementsByTagName("type").item(0).getTextContent();
                        price = Double.parseDouble(dishElement.getElementsByTagName("price").item(0).getTextContent());
                        isPrepared = Boolean.parseBoolean(dishElement.getElementsByTagName("is_prepared").item(0).getTextContent());

                        Dish dish = null;
                        switch (type) {
                            case "Main Course" -> dish = new MainCourse(name, price, isPrepared);
                            case "Appetizer" -> dish = new Appetizer(name, price, isPrepared);
                            case "Desert" -> dish = new Desert(name, price, isPrepared);
                        }

                        if(dish != null)
                            dishes.add(dish);
                    }
                }

                Order order = new Order(tableNumber, dishes, is_reserved);
                orders.add(order);
            }
        }

        return orders;
    }

    private List<Table> loadTables() {
        List<Table> tables = new LinkedList<>();

        NodeList list = document.getElementsByTagName("table");
        for(int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            int tableNumber, numberOfSeats;
            boolean isSmoking, isBooked;
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                tableNumber = Integer.parseInt(element.getElementsByTagName("table_number").item(0).getTextContent());
                numberOfSeats = Integer.parseInt(element.getElementsByTagName("number_of_seats").item(0).getTextContent());
                isSmoking = Boolean.parseBoolean(element.getElementsByTagName("smoking").item(0).getTextContent());
                isBooked = Boolean.parseBoolean(element.getElementsByTagName("booked").item(0).getTextContent());

                Table table = new Table(tableNumber, numberOfSeats, isSmoking, isBooked);
                tables.add(table);
            }
        }

        return tables;
    }

}
