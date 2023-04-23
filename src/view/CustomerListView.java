package view;

import controller.CustomerListViewController;
import model.users.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerListView implements IView {

    private CustomerListViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private VBox parentLayout;
    private TableView<Customer> customerTableView;
    private Button backButton;

    private static CustomerListView restaurantCustomers;
    private CustomerListView() {
    }
    public static CustomerListView getInstance() {
        if(restaurantCustomers == null)
            restaurantCustomers = new CustomerListView();
        return restaurantCustomers;
    }

    @Override
    public void buildViewStage() {
//        setController();
        createButtons();
        createCustomerListTable();
        setupParentLayout();

        primaryStage = new Stage();
        primaryStage.setTitle("Customer List");
        primaryStage.setMaxHeight(500);
        primaryStage.setMinWidth(620);
        scene = new Scene(parentLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setController() {
        this.controller = new CustomerListViewController();
    }

    @Override
    public Stage getStage() {
        return primaryStage;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    private void setupParentLayout() {
        // create parent layout
        parentLayout = new VBox();
        parentLayout.getChildren().addAll(setupTableLayout(), setupButtons());
    }

    private GridPane setupTableLayout() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.TOP_CENTER);

        GridPane.setConstraints(customerTableView, 0, 0);
        pane.getChildren().add(customerTableView);
        return pane;
    }

    private GridPane setupButtons() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.BOTTOM_CENTER);

        GridPane.setConstraints(backButton, 1, 0);
        pane.getChildren().addAll(backButton);
        return pane;
    }

    private void createButtons() {
        // create back button
        backButton = new Button();
        backButton.setText("Back");
        backButton.setId("back");
        backButton.setOnAction(controller.switchView(backButton));
    }

    private void createCustomerListTable() {
        customerTableView = new TableView<>();
        customerTableView.setPrefWidth(600);
        customerTableView.setCursor(Cursor.HAND);
        customerTableView.setId("employees");
        customerTableView.setItems(controller.getTableItems());

        // create table columns
        TableColumn<Customer, String> firstNameColumn = new TableColumn<>("FirstName");
        firstNameColumn.setMinWidth(120);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Customer, String> lastNameColumn = new TableColumn<>("LastName");
        lastNameColumn.setMinWidth(120);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Customer, String> userNameColumn = new TableColumn<>("Username");
        userNameColumn.setMinWidth(120);
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<Customer, String> salaryColumn = new TableColumn<>("Role");
        salaryColumn.setMinWidth(120);
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<Customer, String> discountColumn = new TableColumn<>("Discount");
        discountColumn.setMinWidth(120);
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        // add columns to tableView
        customerTableView.getColumns().add(firstNameColumn);
        customerTableView.getColumns().add(lastNameColumn);
        customerTableView.getColumns().add(userNameColumn);
        customerTableView.getColumns().add(salaryColumn);
        customerTableView.getColumns().add(discountColumn);
    }

    public CustomerListViewController getController() {
        return controller;
    }

}
