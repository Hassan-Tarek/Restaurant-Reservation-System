package controller;

import model.Restaurant;
import model.users.Employee;
import view.RaiseSalaryView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class RaiseSalaryViewController {

    private final Restaurant restaurant;
    private final RaiseSalaryView raiseSalaryView;

    public RaiseSalaryViewController() {
        this.restaurant = Restaurant.getInstance();
        this.raiseSalaryView = RaiseSalaryView.getInstance();
    }

    public EventHandler<ActionEvent> raise() {
        return event -> {
            String username = raiseSalaryView.getUsernameTextField().getText();
            double raise = Double.parseDouble(raiseSalaryView.getRaiseTextField().getText());
            int userIndex = restaurant.findUser(username);

            if(userIndex != -1) {
                ((Employee) restaurant.getUsers().get(userIndex)).raiseSalary(raise);
            }
            // save and close view
            restaurant.save();
            raiseSalaryView.getStage().close();
        };
    }

    public EventHandler<ActionEvent> close() {
        return event -> raiseSalaryView.getStage().close();
    }

}
