package view;

import controller.CustomerOrderViewController;
import model.dishes.Dish;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerOrderView implements IView {

    private CustomerOrderViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private VBox parentLayout;
    private Label totalPriceLabel;
    private Button backButton;
    private TableView<Dish> dishesTableView;

    private static CustomerOrderView showCustomerOrder;
    private CustomerOrderView() {
    }
    public static CustomerOrderView getInstance() {
        if(showCustomerOrder == null)
            showCustomerOrder = new CustomerOrderView();
        return showCustomerOrder;
    }

    @Override
    public void buildViewStage() {
        setController();
        createLabelAndButton();
        createDishesTableView();
        setupParentLayout();

        primaryStage = new Stage();
        primaryStage.setTitle("Customer Order View");
        scene = new Scene(parentLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setController() {
        this.controller = new CustomerOrderViewController();
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
        parentLayout.getChildren().addAll(setupTableViewLayout(), setupLabelAndButtonLayout());
    }

    private GridPane setupTableViewLayout() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.TOP_CENTER);

        GridPane.setConstraints(dishesTableView, 0, 0);
        pane.getChildren().add(dishesTableView);

        return pane;
    }

    private GridPane setupLabelAndButtonLayout() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.BOTTOM_CENTER);

        // create label layout
        GridPane labelLayout = new GridPane();
        labelLayout.setPadding(new Insets(3, 3, 3, 3));
        labelLayout.setHgap(10);
        labelLayout.setVgap(10);
        labelLayout.setAlignment(Pos.BOTTOM_CENTER);

        GridPane.setConstraints(totalPriceLabel, 0, 0);
        labelLayout.getChildren().add(totalPriceLabel);

        // create button layout
        GridPane buttonLayout = new GridPane();
        buttonLayout.setPadding(new Insets(3, 3, 3, 3));
        buttonLayout.setHgap(10);
        buttonLayout.setVgap(10);
        buttonLayout.setAlignment(Pos.BOTTOM_CENTER);

        GridPane.setConstraints(backButton, 0, 0);
        buttonLayout.getChildren().add(backButton);

        GridPane.setConstraints(labelLayout, 0, 0);
        GridPane.setConstraints(buttonLayout, 0, 1);
        pane.getChildren().addAll(labelLayout, buttonLayout);
        return pane;
    }

    private void createLabelAndButton() {
        // create back button
        backButton = new Button();
        backButton.setText("Back");
        backButton.setId("back");
        backButton.setOnAction(controller.switchView());

        // create total price label
        totalPriceLabel = new Label();
        totalPriceLabel.setText("Total Price = " + controller.getTotalPrice() + "$");
    }

    private void createDishesTableView() {
        dishesTableView = new TableView<>();
        dishesTableView.setPrefWidth(600);
        dishesTableView.setCursor(Cursor.HAND);
        dishesTableView.setId("dishes");
        dishesTableView.setItems(controller.getTableItems());

        // create table columns
        TableColumn<Dish, String> nameColumn = new TableColumn<>("Dish Name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Dish, String> typeColumn = new TableColumn<>("Dish Type");
        typeColumn.setMinWidth(150);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Dish, String> priceColumn = new TableColumn<>("Dish Price");
        priceColumn.setMinWidth(150);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Dish, String> taxColumn = new TableColumn<>("Dish Tax");
        taxColumn.setMinWidth(150);
        taxColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTax() + "%"));

        // add columns to table
        dishesTableView.getColumns().add(nameColumn);
        dishesTableView.getColumns().add(typeColumn);
        dishesTableView.getColumns().add(priceColumn);
        dishesTableView.getColumns().add(taxColumn);
    }

}
