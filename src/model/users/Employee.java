package model.users;

public abstract class Employee extends User{

    private double salary;

    public Employee(String firstName, String lastName, String userName,
                    String password, String role) {
        super(firstName, lastName, userName, password, role);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void raiseSalary(double raise) {
        this.salary += raise;
    }

}
