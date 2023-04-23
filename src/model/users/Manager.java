package model.users;

public class Manager extends Employee{

    public Manager(String firstName, String lastName, String userName,
                   String password) {
        super(firstName, lastName, userName, password, "Manager");
    }

}
