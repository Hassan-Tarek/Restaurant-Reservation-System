package controller;

import model.Bill;
import model.Restaurant;
import view.BillListView;
import view.ManagerView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class BillListViewController {

    private final Restaurant restaurant;
    private final BillListView billListView;
    private final ManagerView managerView;

    public BillListViewController() {
        this.restaurant = Restaurant.getInstance();
        this.billListView = BillListView.getInstance();
        this.managerView = ManagerView.getInstance();
    }

    public ObservableList<Bill> getTableItems() {
        return FXCollections.observableArrayList(restaurant.getBills());
    }

    public EventHandler<ActionEvent> switchView(Button button) {
        return event -> {
          if(button.getId().equals("back")) {
              billListView.getStage().close();
              managerView.getStage().show();
          }
        };
    }

    public String getTotalPrice() {
        return String.format("%.2f", restaurant.getTotalEarnedMoney());
    }

}
