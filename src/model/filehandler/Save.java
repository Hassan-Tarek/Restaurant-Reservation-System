package model.filehandler;

import model.Bill;
import model.dishes.Dish;
import model.Order;
import model.Restaurant;
import model.Table;
import model.users.Customer;
import model.users.Employee;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class Save {

    private final Restaurant restaurant;
    private final String path;
    private Document document;

    public Save(String path, Restaurant restaurant) {
        this.restaurant = restaurant;
        this.path = path;
    }

    public void saveData() {
        File XMLFile = new File(path);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();

            // create root
            Element restaurantElement = document.createElement("restaurant");
            document.appendChild(restaurantElement);

            Element usersElement = document.createElement("users");
            Element dishesElement = document.createElement("dishes");
            Element tablesElement = document.createElement("tables");
            Element billsElement = document.createElement("bills");
            Element ordersElement = document.createElement("orders");

            restaurantElement.appendChild(usersElement);
            restaurantElement.appendChild(dishesElement);
            restaurantElement.appendChild(tablesElement);
            restaurantElement.appendChild(billsElement);
            restaurantElement.appendChild(ordersElement);

            // save data
            saveEmployees(usersElement);
            saveCustomers(usersElement);
            saveDishes(dishesElement);
            saveTables(tablesElement);
            saveBills(billsElement);
            saveOrders(ordersElement);

            // write from document into XMLFile
            DOMSource source = new DOMSource(document);
            Result result = new StreamResult(XMLFile);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // write data into file
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void saveEmployee(Element parent, Employee employee) {
        // create employee element
        Element employeeElement = document.createElement("employee");

        Element firstNameElement = document.createElement("firstname");
        firstNameElement.setTextContent(employee.getFirstName());

        Element lastNameElement = document.createElement("lastname");
        lastNameElement.setTextContent(employee.getLastName());

        Element userNameElement = document.createElement("username");
        userNameElement.setTextContent(employee.getUserName());

        Element passwordElement = document.createElement("password");
        passwordElement.setTextContent(employee.getPassword());

        Element roleElement = document.createElement("role");
        roleElement.setTextContent(employee.getRole());

        Element salaryElement = document.createElement("salary");
        salaryElement.setTextContent(String.valueOf(employee.getSalary()));

        // add child elements to employeeElement
        employeeElement.appendChild(firstNameElement);
        employeeElement.appendChild(lastNameElement);
        employeeElement.appendChild(userNameElement);
        employeeElement.appendChild(passwordElement);
        employeeElement.appendChild(roleElement);
        employeeElement.appendChild(salaryElement);

        // add employeeElement to parent node
        parent.appendChild(employeeElement);
    }

    private void saveEmployees(Element parent) {
        List<Employee> employees = restaurant.getEmployees();
        Element employeesElement = document.createElement("employees");

        for (Employee employee : employees)
            saveEmployee(employeesElement, employee);

        // add employees Element to parent
        parent.appendChild(employeesElement);
    }

    private void saveCustomer(Element parent, Customer customer) {
        // create customer element
        Element customerElement = document.createElement("customer");

        Element firstNameElement = document.createElement("firstname");
        firstNameElement.setTextContent(customer.getFirstName());

        Element lastNameElement = document.createElement("lastname");
        lastNameElement.setTextContent(customer.getLastName());

        Element userNameElement = document.createElement("username");
        userNameElement.setTextContent(customer.getUserName());

        Element passwordElement = document.createElement("password");
        passwordElement.setTextContent(customer.getPassword());

        Element roleElement = document.createElement("role");
        roleElement.setTextContent(customer.getRole());

        Element tableNumberElement = document.createElement("table_number");
        tableNumberElement.setTextContent(String.valueOf(customer.getTable() == null ? "" : customer.getTable().getTableNumber()));

        Element discountElement = document.createElement("discount");
        discountElement.setTextContent(String.valueOf(customer.getDiscount()));

        // add child elements to customerElement node
        customerElement.appendChild(firstNameElement);
        customerElement.appendChild(lastNameElement);
        customerElement.appendChild(userNameElement);
        customerElement.appendChild(passwordElement);
        customerElement.appendChild(roleElement);
        customerElement.appendChild(tableNumberElement);
        customerElement.appendChild(discountElement);

        // add customerElement to parent
        parent.appendChild(customerElement);
    }

    private void saveCustomers(Element parent) {
        List<Customer> customers = restaurant.getCustomers();
        Element customersElement = document.createElement("customers");

        for (Customer customer : customers)
            saveCustomer(customersElement, customer);

        // add customersElement to parent node
        parent.appendChild(customersElement);
    }

    private void saveDish(Element parent, Dish dish, String tagNode, String id, boolean isOrderDish) {
        Element dishElement = document.createElement(tagNode);

        Element nameElement = document.createElement("name");
        nameElement.setTextContent(dish.getName());

        Element typeElement = document.createElement("type");
        typeElement.setTextContent(dish.getType());

        Element priceElement = document.createElement("price");
        priceElement.setTextContent(String.valueOf(dish.getPrice()));

        // add child elements to dishElement node
        dishElement.appendChild(nameElement);
        dishElement.appendChild(typeElement);
        dishElement.appendChild(priceElement);

        // add is_prepared tag if the dish is instance of order
        if(isOrderDish) {
            Element isPreparedElement = document.createElement("is_prepared");
            isPreparedElement.setTextContent(String.valueOf(dish.isPrepared()));
            dishElement.appendChild(isPreparedElement);
        }

        // set the id attribute of the dish for bills & orders
        if(id != null)
            dishElement.setAttribute("id", id);

        // add dishElement node to the parent node
        parent.appendChild(dishElement);
    }

    public void saveDishes(Element parent) {
        List<Dish> dishes = restaurant.getMenu().getDishes();
        for(Dish dish : dishes)
            saveDish(parent, dish, "dish", null, false);
    }

    private void saveTable(Element parent, Table table) {
        Element tableElement = document.createElement("table");

        Element tableNumberElement = document.createElement("table_number");
        tableNumberElement.setTextContent(String.valueOf(table.getTableNumber()));

        Element numberOfSeatsElement = document.createElement("number_of_seats");
        numberOfSeatsElement.setTextContent(String.valueOf(table.getNumOfSeats()));

        Element isSmokingElement = document.createElement("smoking");
        isSmokingElement.setTextContent(String.valueOf(table.isSmokingArea()));

        Element isBookedElement = document.createElement("booked");
        isBookedElement.setTextContent(String.valueOf(table.isBooked()));

        // add child elements to tableElement node
        tableElement.appendChild(tableNumberElement);
        tableElement.appendChild(numberOfSeatsElement);
        tableElement.appendChild(isSmokingElement);
        tableElement.appendChild(isBookedElement);

        // add tableElement node to the parent node
        parent.appendChild(tableElement);
    }
    private void saveTables(Element parent) {
        for(Table table : restaurant.getTables())
            saveTable(parent, table);
    }

    private void saveBill(Element parent, Bill bill) {
        Element billElement = document.createElement("bill");

        Element tableNumberElement = document.createElement("table_number");
        tableNumberElement.setTextContent(String.valueOf(bill.getTableNumber()));

        Element dateElement = document.createElement("date");
        dateElement.setTextContent(bill.getOrderDate());

        Element discountElement = document.createElement("bill_discount");
        discountElement.setTextContent(String.valueOf(bill.getDiscount()));

        Element billDishes = document.createElement("bill_dishes");
        for(Dish dish : bill.getOrderDishes())
            saveDish(billDishes, dish, "bill_dish", String.valueOf(bill.getTableNumber()), false);

        // add child elements to billElement node
        billElement.appendChild(tableNumberElement);
        billElement.appendChild(dateElement);
        billElement.appendChild(discountElement);
        billElement.appendChild(billDishes);

        // add billElement node to the parent node
        parent.appendChild(billElement);
    }

    private void saveBills(Element parent) {
        for(Bill bill : restaurant.getBills())
            saveBill(parent, bill);
    }

    private void saveOrder(Element parent, Order order) {
        Element orderElement = document.createElement("order");

        Element tableNumberElement = document.createElement("table_number");
        tableNumberElement.setTextContent(String.valueOf(order.getTableNumber()));

        Element isReservedElement = document.createElement("is_reserved");
        isReservedElement.setTextContent(String.valueOf(order.isReserved()));

        Element orderDishes = document.createElement("order_dishes");
        for(Dish dish : order.getDishes())
            saveDish(orderDishes, dish, "order_dish", String.valueOf(order.getTableNumber()), true);

        // add child elements to orderElement node
        orderElement.appendChild(tableNumberElement);
        orderElement.appendChild(isReservedElement);
        orderElement.appendChild(orderDishes);

        // add orderElement node to the parent node
        parent.appendChild(orderElement);
    }

    private void saveOrders(Element parent) {
        for(Order order : restaurant.getOrders())
            saveOrder(parent, order);
    }

}
