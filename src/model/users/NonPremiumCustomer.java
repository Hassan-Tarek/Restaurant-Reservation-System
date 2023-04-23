package model.users;

import model.Bill;
import model.Menu;
import model.Order;
import model.Table;

public class NonPremiumCustomer extends Customer{

    public NonPremiumCustomer(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password, "NonPremium Customer");
    }

    public NonPremiumCustomer(String firstName, String lastName, String userName, String password,
                              Menu menu, Table table, Order order, Bill bill) {
        super(firstName, lastName, userName, password, "NonPremium Customer", menu, table, order, bill);
    }
}
