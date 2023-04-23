package view;

import controller.ManagerViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ManagerView implements IView {

    private ManagerViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private VBox parentLayout;
    private Label greetingLabel;
    private Button showCustomersButton;
    private Button showEmployeesButton;
    private Button showBillsButton;
    private Button raiseEmployeeSalary;
    private Button logoutButton;

    private static ManagerView managerView;
    private ManagerView() {
    }
    public static ManagerView getInstance() {
        if (managerView == null)
            managerView = new ManagerView();
        return managerView;
    }

    @Override
    public void buildViewStage() {
        setController();
        createButtons();
        createLabel();
        setupParentLayout();

        primaryStage = new Stage();
        primaryStage.setTitle("Manager View");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(620);
        scene = new Scene(parentLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setController() {
        this.controller = new ManagerViewController();
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
        parentLayout.getChildren().addAll(setupLabelLayout(), setupButtonsLayout());
    }

    private GridPane setupLabelLayout() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.CENTER);
        pane.setMinWidth(600);
        pane.setMinHeight(400);

        pane.getChildren().add(greetingLabel);
        return pane;
    }

    private GridPane setupButtonsLayout() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.BOTTOM_CENTER);

        GridPane.setConstraints(showCustomersButton, 0, 0);
        GridPane.setConstraints(showEmployeesButton, 1, 0);
        GridPane.setConstraints(showBillsButton, 2, 0);
        GridPane.setConstraints(raiseEmployeeSalary, 3, 0);
        GridPane.setConstraints(logoutButton, 4, 0);
        pane.getChildren().addAll(showCustomersButton, showEmployeesButton, showBillsButton,
                raiseEmployeeSalary, logoutButton);
        return pane;
    }

    private void createButtons() {
        // create show customers button
        showCustomersButton = new Button();
        showCustomersButton.setText("Show Customers");
        showCustomersButton.setId("show-customers");
        showCustomersButton.setOnAction(controller.switchView(showCustomersButton));

        // create show employees button
        showEmployeesButton = new Button();
        showEmployeesButton.setText("Show Employees");
        showEmployeesButton.setId("show-employees");
        showEmployeesButton.setOnAction(controller.switchView(showEmployeesButton));

        // create show today's bills button
        showBillsButton = new Button();
        showBillsButton.setText("Show Bills");
        showBillsButton.setId("show-bills");
        showBillsButton.setOnAction(controller.switchView(showBillsButton));

        // create raiseEmployeeSalary button
        raiseEmployeeSalary = new Button();
        raiseEmployeeSalary.setText("Raise Employee Salary");
        raiseEmployeeSalary.setId("raise-salary");
        raiseEmployeeSalary.setOnAction(controller.switchView(raiseEmployeeSalary));

        // create logout button
        logoutButton = new Button();
        logoutButton.setText("Logout");
        logoutButton.setId("logout");
        logoutButton.setOnAction(controller.switchView(logoutButton));
    }

    private void createLabel() {
        greetingLabel = new Label();
        greetingLabel.setText("Welcome to our Restaurant");
        greetingLabel.setFont(new Font("Arial", 30));
    }
    
}
