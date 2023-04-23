package view;

import controller.MakeOrderViewController;
import model.dishes.Dish;
import javafx.beans.property.SimpleStringProperty;
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

public class MakeOrderView implements IView {

    private MakeOrderViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private VBox parentLayout;
    private Button addDishButton;
    private Button deleteDishButton;
    private Button orderButton;
    private Button backButton;
    private TableView<Dish> menuTableView;
    private TableView<Dish> selectedDishesTableView;

    private static MakeOrderView menuView;
    private MakeOrderView() {
    }
    public static MakeOrderView getInstance() {
        if(menuView == null)
            menuView = new MakeOrderView();
        return menuView;
    }

    @Override
    public void buildViewStage() {
        setController();
        createButtons();
        createTableViews();
        setupParentLayout();

        primaryStage = new Stage();
        primaryStage.setTitle("Make Order View");
        scene = new Scene(parentLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setController() {
        this.controller = new MakeOrderViewController();
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
        parentLayout.getChildren().addAll(setupTableViewsLayout(), setupButtonsLayout());
    }

    private GridPane setupTableViewsLayout() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.TOP_CENTER);

        GridPane.setConstraints(menuTableView, 0, 0);
        GridPane.setConstraints(selectedDishesTableView, 1, 0);
        pane.getChildren().addAll(menuTableView, selectedDishesTableView);

        return pane;
    }

    private GridPane setupButtonsLayout() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.BOTTOM_CENTER);

        GridPane.setConstraints(addDishButton, 0, 0);
        GridPane.setConstraints(deleteDishButton, 1, 0);
        GridPane.setConstraints(orderButton, 2, 0);
        GridPane.setConstraints(backButton, 3, 0);
        pane.getChildren().addAll(addDishButton, deleteDishButton, orderButton, backButton);

        return pane;
    }

    private void createButtons() {
        // create add dish button
        addDishButton = new Button();
        addDishButton.setText("Add Dish");
        addDishButton.setId("add");
        addDishButton.setOnAction(controller.addDish());

        // create delete dish button
        deleteDishButton = new Button();
        deleteDishButton.setText("Delete Dish");
        deleteDishButton.setId("delete");
        deleteDishButton.setOnAction(controller.deleteDish());

        // create order button
        orderButton = new Button();
        orderButton.setText("Order");
        orderButton.setId("order");
        orderButton.setOnAction(controller.makeOrder());

        // create back button
        backButton = new Button();
        backButton.setText("Back");
        backButton.setId("back");
        backButton.setOnAction(controller.switchView());
    }

    private void createTableViews() {
        // create menu tableView
        menuTableView = new TableView<>();
        menuTableView.setPrefWidth(400);
        menuTableView.setCursor(Cursor.HAND);
        menuTableView.setId("menu");
        menuTableView.setItems(controller.getTableItems(menuTableView));
        menuTableView.setRowFactory(controller.detectedDoubleClicks());

        // create menu tableView Columns
        TableColumn<Dish, String> dishNameColumn = new TableColumn<>("Name");
        dishNameColumn.setMinWidth(100);
        dishNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Dish, String> dishTypeColumn = new TableColumn<>("Type");
        dishTypeColumn.setMinWidth(100);
        dishTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Dish, String> dishPriceColumn = new TableColumn<>("Price");
        dishPriceColumn.setMinWidth(100);
        dishPriceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrice() + "$"));

        TableColumn<Dish, String> dishTaxColumn = new TableColumn<>("Tax");
        dishTaxColumn.setMinWidth(100);
        dishTaxColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTax() + "%"));

        menuTableView.getColumns().add(dishNameColumn);
        menuTableView.getColumns().add(dishTypeColumn);
        menuTableView.getColumns().add(dishPriceColumn);
        menuTableView.getColumns().add(dishTaxColumn);


        // create selected dishes tableView
        selectedDishesTableView = new TableView<>();
        selectedDishesTableView.setPrefWidth(400);
        selectedDishesTableView.setCursor(Cursor.HAND);
        selectedDishesTableView.setId("selected-dishes");
        selectedDishesTableView.setItems(controller.getTableItems(selectedDishesTableView));

        // create menu tableView Columns
        TableColumn<Dish, String> selectedDishNameColumn = new TableColumn<>("Name");
        selectedDishNameColumn.setMinWidth(100);
        selectedDishNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Dish, String> selectedDishTypeColumn = new TableColumn<>("Type");
        selectedDishTypeColumn.setMinWidth(100);
        selectedDishTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Dish, String> selectedDishPriceColumn = new TableColumn<>("Price");
        selectedDishPriceColumn.setMinWidth(100);
        selectedDishPriceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrice() + "$"));

        TableColumn<Dish, String> selectedDishTaxColumn = new TableColumn<>("Tax");
        selectedDishTaxColumn.setMinWidth(100);
        selectedDishTaxColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTax() + "%"));

        selectedDishesTableView.getColumns().add(selectedDishNameColumn);
        selectedDishesTableView.getColumns().add(selectedDishTypeColumn);
        selectedDishesTableView.getColumns().add(selectedDishPriceColumn);
        selectedDishesTableView.getColumns().add(selectedDishTaxColumn);
    }

    public TableView<Dish> getMenuTableView() {
        return menuTableView;
    }

    public TableView<Dish> getSelectedDishesTableView() {
        return selectedDishesTableView;
    }

}
