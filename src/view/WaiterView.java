package view;

import controller.WaiterViewController;
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

import java.util.List;

public class WaiterView implements IView {

    private WaiterViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private VBox parentLayout;
    private TableView<Order> ordersTable;
    private Button reservedButton;
    private Button logoutButton;

    private static WaiterView waiterView;
    private WaiterView() {
    }
    public static WaiterView getInstance() {
        if (waiterView == null)
            waiterView = new WaiterView();
        return waiterView;
    }

    @Override
    public void buildViewStage() {
        setController();
        createTables();
        createButtons();
        setupParentLayouts();

        primaryStage = new Stage();
        primaryStage.setTitle("Waiter View");
        primaryStage.setMinHeight(220);
        primaryStage.setMinWidth(300);
        scene = new Scene(parentLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setController() {
        this.controller = new WaiterViewController();
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
        GridPane.setConstraints(ordersTable, 0, 0);
        pane.getChildren().addAll(ordersTable);

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
        GridPane.setConstraints(reservedButton, 0, 0);
        GridPane.setConstraints(logoutButton, 1, 0);
        pane.getChildren().addAll(reservedButton, logoutButton);

        return pane;
    }

    private void createButtons() {
        // create reservedButton
        reservedButton = new Button();
        reservedButton.setText("Reserved");
        reservedButton.setOnAction(controller.deleteOrderFromTable());

        // create logoutButton
        logoutButton = new Button();
        logoutButton.setText("Logout");
        logoutButton.setOnAction(controller.logout());
    }

    private void createTables() {
        // create order table
        ordersTable = new TableView<>();
        ordersTable.setPrefWidth(400);
        ordersTable.setCursor(Cursor.HAND);
        ordersTable.setId("order");
        ordersTable.setItems(controller.getOrderItems());

        // create order tableNumber columns
        TableColumn<Order, Integer> tableNumberColumn = new TableColumn<>("Table Number");
        tableNumberColumn.setMinWidth(100);
        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));

        // create order dishes columns
        TableColumn<Order, List<Dish>> orderDishesColumn = new TableColumn<>("Order Dishes");
        orderDishesColumn.setMinWidth(300);
        orderDishesColumn.setCellValueFactory(new PropertyValueFactory<>("dishes"));

        // add columns to the table
        ordersTable.getColumns().add(tableNumberColumn);
        ordersTable.getColumns().add(orderDishesColumn);

    }

    public TableView<Order> getOrdersTable() {
        return ordersTable;
    }

}
