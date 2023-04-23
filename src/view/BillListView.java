package view;

import controller.BillListViewController;
import model.Bill;
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

public class BillListView implements IView {

    private BillListViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private VBox parentLayout;
    private TableView<Bill> billTableView;
    private Label totalMoneyLabel;
    private Button backButton;

    private static BillListView restaurantBills;
    private BillListView() {
    }
    public static BillListView getInstance() {
        if(restaurantBills == null)
            restaurantBills = new BillListView();
        return restaurantBills;
    }

    @Override
    public void buildViewStage() {
        setController();
        createTableView();
        createLabelAndButton();
        setupParentLayout();

        primaryStage = new Stage();
        primaryStage.setTitle("Bill List View");
        primaryStage.setMinHeight(220);
        primaryStage.setMinWidth(300);
        scene = new Scene(parentLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setController() {
        this.controller = new BillListViewController();
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

        GridPane.setConstraints(billTableView, 0, 0);
        pane.getChildren().add(billTableView);
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

        GridPane.setConstraints(totalMoneyLabel, 0, 0);
        labelLayout.getChildren().add(totalMoneyLabel);

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
        backButton.setOnAction(controller.switchView(backButton));

        // create total price label
        totalMoneyLabel = new Label();
        totalMoneyLabel.setText("Total Earned Money = " + controller.getTotalPrice() + "$");
    }

    private void createTableView() {
        // create table view
        billTableView = new TableView<>();
        billTableView.setPrefWidth(600);
        billTableView.setCursor(Cursor.HAND);
        billTableView.setId("bills");
        billTableView.setItems(controller.getTableItems());

        // create table columns
        TableColumn<Bill, Integer> tableNumberColumn = new TableColumn<>("Table Number");
        tableNumberColumn.setMinWidth(150);
        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));

        TableColumn<Bill, String> dateColumn = new TableColumn<>("Order Date");
        dateColumn.setMinWidth(150);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<Bill, String> billColumn = new TableColumn<>("Order Bill");
        billColumn.setMinWidth(150);
        billColumn.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.2f" , data.getValue().getTotalPrice()) + "$"));

        TableColumn<Bill, String> discountColumn = new TableColumn<>("Order Discount");
        discountColumn.setMinWidth(150);
        discountColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDiscount() + "%"));

        // add columns to table
        billTableView.getColumns().add(tableNumberColumn);
        billTableView.getColumns().add(dateColumn);
        billTableView.getColumns().add(billColumn);
        billTableView.getColumns().add(discountColumn);
    }

}
