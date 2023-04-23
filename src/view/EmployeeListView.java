package view;

import controller.EmployeeListViewController;
import model.users.Employee;
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

public class EmployeeListView implements IView {

    private EmployeeListViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private VBox parentLayout;
    private TableView<Employee> employeeTableView;
    private Button backButton;

    private static EmployeeListView restaurantEmployees;
    private EmployeeListView() {
    }
    public static EmployeeListView getInstance() {
        if(restaurantEmployees == null)
            restaurantEmployees = new EmployeeListView();
        return restaurantEmployees;
    }

    @Override
    public void buildViewStage() {
//        setController();
        createButtons();
        createEmployeeListTable();
        setupParentLayout();

        primaryStage = new Stage();
        primaryStage.setTitle("Employee List");
        primaryStage.setMaxHeight(500);
        primaryStage.setMinWidth(620);
        scene = new Scene(parentLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setController() {
        this.controller = new EmployeeListViewController();
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

        GridPane.setConstraints(employeeTableView, 0, 0);
        pane.getChildren().add(employeeTableView);
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

    private void createEmployeeListTable() {
        employeeTableView = new TableView<>();
        employeeTableView.setPrefWidth(600);
        employeeTableView.setCursor(Cursor.HAND);
        employeeTableView.setId("employees");
        employeeTableView.setItems(controller.getTableItems());

        // create table columns
        TableColumn<Employee, String> firstNameColumn = new TableColumn<>("FirstName");
        firstNameColumn.setMinWidth(120);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("LastName");
        lastNameColumn.setMinWidth(120);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Employee, String> userNameColumn = new TableColumn<>("Username");
        userNameColumn.setMinWidth(120);
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<Employee, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setMinWidth(120);
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setMinWidth(120);
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        // add columns to tableView
        employeeTableView.getColumns().add(firstNameColumn);
        employeeTableView.getColumns().add(lastNameColumn);
        employeeTableView.getColumns().add(userNameColumn);
        employeeTableView.getColumns().add(roleColumn);
        employeeTableView.getColumns().add(salaryColumn);
    }

    public EmployeeListViewController getController() {
        return controller;
    }

}
