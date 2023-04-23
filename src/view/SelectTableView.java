package view;

import controller.SelectTableViewController;
import model.Table;
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

public class SelectTableView implements IView {

    private SelectTableViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private VBox parentLayout;
    private Button backButton;
    private Button bookButton;
    private TableView<Table> tablesTableView;

    private static SelectTableView selectTableView;
    private SelectTableView() {
    }
    public static SelectTableView getInstance() {
        if(selectTableView == null)
            selectTableView = new SelectTableView();
        return selectTableView;
    }

    @Override
    public void buildViewStage() {
        setController();
        createButtons();
        createTablesTableView();
        setupParentLayout();

        primaryStage = new Stage();
        primaryStage.setTitle("Select Table View");
        scene = new Scene(parentLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setController() {
        this.controller = new SelectTableViewController();
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
        parentLayout.getChildren().addAll(setupTableViewLayout(), setupButtonsLayout());
    }

    private GridPane setupTableViewLayout() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.TOP_CENTER);

        GridPane.setConstraints(tablesTableView, 0, 0);
        pane.getChildren().add(tablesTableView);

        return pane;
    }

    private GridPane setupButtonsLayout() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.BOTTOM_CENTER);

        GridPane.setConstraints(bookButton, 0, 0);
        GridPane.setConstraints(backButton, 1, 0);
        pane.getChildren().addAll(bookButton, backButton);

        return pane;
    }

    private void createButtons() {
        // create book button
        bookButton = new Button();
        bookButton.setText("Book");
        bookButton.setId("book");
        bookButton.setOnAction(controller.bookTable());

        // create back button
        backButton = new Button();
        backButton.setText("Back");
        backButton.setId("back");
        backButton.setOnAction(controller.switchScene());
    }

    private void createTablesTableView() {
        tablesTableView = new TableView<>();
        tablesTableView.setPrefWidth(600);
        tablesTableView.setCursor(Cursor.HAND);
        tablesTableView.setId("tables");
        tablesTableView.setItems(controller.getTableItems());
        tablesTableView.setRowFactory(controller.detectedDoubleClicks());

        // create table columns
        TableColumn<Table, Integer> tableNumberColumn = new TableColumn<>("Table Number");
        tableNumberColumn.setMinWidth(200);
        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));

        TableColumn<Table, Integer> numOfSeatsColumn = new TableColumn<>("Num of Seats");
        numOfSeatsColumn.setMinWidth(200);
        numOfSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfSeats"));

        TableColumn<Table, String> isSmokingColumn = new TableColumn<>("Smoking Area");
        isSmokingColumn.setMinWidth(200);
        isSmokingColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isSmokingArea() ? "Yes" : "No"));

        // add columns to tableView
        tablesTableView.getColumns().add(tableNumberColumn);
        tablesTableView.getColumns().add(numOfSeatsColumn);
        tablesTableView.getColumns().add(isSmokingColumn);
    }

    public TableView<Table> getTablesTableView() {
        return tablesTableView;
    }

}
