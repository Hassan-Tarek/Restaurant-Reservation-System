package controller;

import model.Restaurant;
import view.AlterBox;
import view.RegisterView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class RegisterViewController {

    private final RegisterView registerView;
    private final Restaurant restaurant;

    public RegisterViewController() {
        this.registerView = RegisterView.getInstance();
        this.restaurant = Restaurant.getInstance();
    }

    public EventHandler<ActionEvent> switchStage(Button button) {
        return event -> {
            if (button.getId().equals("sign")) {
                String firstname = registerView.getFirstnameTextField().getText().trim();
                String lastname = registerView.getLastnameTextField().getText().trim();
                String username = registerView.getUsernameTextField().getText().trim();
                String password = registerView.getPasswordTextField().getText().trim();
                String role = registerView.getRole().getValue();

                if (validInputs(firstname, lastname, username, password)) {
                    // add the user and close the registerView stage
                    restaurant.addUser(firstname, lastname, username, password, role);
                    restaurant.save();
                    registerView.getStage().close();
                } else {
                    AlterBox.display("ERROR!", "Enter Valid Data!!");
                }
            } else if (button.getId().equals("cancel")) {
                // close the stage
                registerView.getStage().close();
            }
        };
    }

    private Boolean validInputs(String firstname, String lastname, String username, String password) {
        boolean valid = true;
        if(firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty())
            valid = false;
        if(restaurant.findUser(username) != -1)
            valid = false;

        return valid;
    }

}
