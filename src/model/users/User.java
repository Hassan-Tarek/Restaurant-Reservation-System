package model.users;

public abstract class User {

    private String firstName;
    private String lastName;
    private final String userName;
    private String password;
    private final String role;

    public User(String firstName, String lastName, String userName,
                String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof User))
            return false;

        User user = (User) obj;

        return user.getFirstName().equals(this.firstName) && user.getLastName().equals(this.lastName) &&
                user.getUserName().equals(this.userName) && user.getPassword().equals(this.password) &&
                user.getRole().equals(this.role);
    }
}
