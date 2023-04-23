package controller;

import model.Restaurant;
import model.Table;
import model.users.Customer;
import view.CustomerView;
import view.SelectTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class SelectTableViewController {

    private final Restaurant restaurant;
    private final SelectTableView selectTableView;
    private final CustomerView customerView;

    public SelectTableViewController() {
        restaurant = Restaurant.getInstance();
        selectTableView = SelectTableView.getInstance();
        customerView = CustomerView.getInstance();
    }

    public ObservableList<Table> getTableItems() {
        return FXCollections.observableArrayList(restaurant.getAvailableTables());
    }

    public Callback<TableView<Table>, TableRow<Table>> detectedDoubleClicks() {
        return tableViewCallback -> {
            TableRow<Table> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(isDoubleClicks(event) && !row.isEmpty()) {
                    int index = restaurant.findUser(customerView.getController().getCustomerUsername());
                    ((Customer) restaurant.getUsers().get(index)).setTable(row.getItem());
                    row.getItem().setBooked(true);

                    // save data into xml file
                    restaurant.save();

                    // switch scene
                    selectTableView.getStage().close();
                    customerView.getStage().show();
                }
            });

            return row;
        };
    }

    private boolean isDoubleClicks(MouseEvent event) {
        return event.getClickCount() == 2;
    }

    public EventHandler<ActionEvent> bookTable() {
        return event -> {
            if(selectTableView.getTablesTableView().getSelectionModel().getSelectedItem() != null) {
                int index = restaurant.findUser(customerView.getController().getCustomerUsername());
                ((Customer) restaurant.getUsers().get(index)).
                        setTable(selectTableView.getTablesTableView().getSelectionModel().getSelectedItem());
                selectTableView.getTablesTableView().getSelectionModel().getSelectedItem().setBooked(true);
                restaurant.save();
            }
        };
    }

    public EventHandler<ActionEvent> switchScene() {
        return event -> {
            // switch scene
            selectTableView.getStage().close();
            customerView.getStage().show();
        };
    }

}
