package view;

import controller.CookerViewController;
import model.dishes.Dish;
import model.Order;
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

public class CookerView implements IView {

    private CookerViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private VBox parentLayout;
    private TableView<Order> ordersTableView;
    private TableView<Dish> dishesTableView;
    private Button viewDishesButton;
    private Button preparedButton;
    private Button logoutButton;

    private static CookerView cookerView;
    private CookerView() {
    }
    public static CookerView getInstance() {
        if (cookerView == null)
            cookerView = new CookerView();
        return cookerView;
    }

    @Override
    public void buildViewStage() {
        setController();
        createTables();
        createButtons();
        setupParentLayouts();

        primaryStage = new Stage();
        primaryStage.setTitle("Cooker View");
        primaryStage.setMinHeight(220);
        primaryStage.setMinWidth(300);
        scene = new Scene(parentLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setController() {
        this.controller = new CookerViewController();
    }

    @Override
    public Stage getStage() {
        return primaryStage;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    private void setupParentLayouts() {
        // create parent layout
        parentLayout = new VBox();
        parentLayout.getChildren().addAll(setupTableLayout(), setupButtonsLayout());
    }

    private GridPane setupTableLayout() {
        // setup tableLayout
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.TOP_CENTER);

        // add table
        GridPane.setConstraints(ordersTableView, 0, 0);
        GridPane.setConstraints(dishesTableView, 1, 0);
        pane.getChildren().addAll(ordersTableView, dishesTableView);

        return pane;
    }

    private GridPane setupButtonsLayout() {
        // setup buttonsLayout
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.BOTTOM_CENTER);

        // add buttons
        GridPane.setConstraints(viewDishesButton, 0, 0);
        GridPane.setConstraints(preparedButton, 1, 0);
        GridPane.setConstraints(logoutButton, 2, 0);
        pane.getChildren().addAll(viewDishesButton, preparedButton, logoutButton);
        return pane;
    }

    private void createButtons() {
        // create view dishes button
        viewDishesButton = new Button();
        viewDishesButton.setText("View Dishes");
        viewDishesButton.setId("view");
        viewDishesButton.setOnAction(controller.displayOrderDishes());

        // create delete dish button
        preparedButton = new Button();
        preparedButton.setText("Prepared");
        preparedButton.setOnAction(controller.setDishAsPrepared());

        // create logout button
        logoutButton = new Button();
        logoutButton.setText("Logout");
        logoutButton.setOnAction(controller.logout());
    }

    private void createTables() {
        // order table that will have only the tableNumber of each order
        ordersTableView = new TableView<>();
        ordersTableView.setPrefWidth(150);
        ordersTableView.setCursor(Cursor.HAND);
        ordersTableView.setId("order");
        ordersTableView.setItems(controller.getOrderItems());
        ordersTableView.setRowFactory(controller.detectedDoubleClicks());

        // create tableNumber column
        TableColumn<Order, Integer> tableNumber = new TableColumn<>("Table Number");
        tableNumber.setMinWidth(150);
        tableNumber.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));


        // create dishes table that will have the dish name, type
        dishesTableView = new TableView<>();
        dishesTableView.setPrefWidth(450);
        dishesTableView.setCursor(Cursor.HAND);
        dishesTableView.setId("dish");

        // dish name Column
        TableColumn<Dish, String> dishName = new TableColumn<>("Dish Name");
        dishName.setMinWidth(250);
        dishName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // dish type Column
        TableColumn<Dish, String> dishType = new TableColumn<>("Dish Type");
        dishType.setMinWidth(200);
        dishType.setCellValueFactory(new PropertyValueFactory<>("type"));

        // add columns to the tables
        ordersTableView.getColumns().add(tableNumber);
        dishesTableView.getColumns().add(dishName);
        dishesTableView.getColumns().add(dishType);
    }

    public TableView<Order> getOrdersTable() {
        return ordersTableView;
    }

    public TableView<Dish> getDishesTable() {
        return dishesTableView;
    }

}
