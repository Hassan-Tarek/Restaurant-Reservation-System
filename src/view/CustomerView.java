package view;

import controller.CustomerViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CustomerView implements IView {

    private CustomerViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private VBox parentLayout;
    private Label greetingLabel;
    private Button bookTableButton;
    private Button makeOrderButton;
    private Button showMyOrderButton;
    private Button logoutButton;

    private static CustomerView customerView;
    private CustomerView() {
    }
    public static CustomerView getInstance() {
        if (customerView == null)
            customerView = new CustomerView();
        return customerView;
    }

    @Override
    public void buildViewStage() {
        setController();
        createButtons();
        createLabel();
        setupParentLayout();

        primaryStage = new Stage();
        primaryStage.setTitle("Customer View");
        primaryStage.setMaxHeight(500);
        primaryStage.setMinWidth(620);
        scene = new Scene(parentLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setController() {
        this.controller = new CustomerViewController();
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

        GridPane.setConstraints(bookTableButton, 0, 0);
        GridPane.setConstraints(makeOrderButton, 1, 0);
        GridPane.setConstraints(showMyOrderButton, 2, 0);
        GridPane.setConstraints(logoutButton, 3, 0);
        pane.getChildren().addAll(bookTableButton, makeOrderButton, showMyOrderButton, logoutButton);

        return pane;
    }

    private void createButtons() {
        // create book table button
        bookTableButton = new Button();
        bookTableButton.setText("Book Table");
        bookTableButton.setId("book-table");
        bookTableButton.setOnAction(controller.switchView(bookTableButton));

        // create make order button
        makeOrderButton = new Button();
        makeOrderButton.setText("Make Order");
        makeOrderButton.setId("make-order");
        makeOrderButton.setOnAction(controller.switchView(makeOrderButton));

        // create showMyOrderButton
        showMyOrderButton = new Button();
        showMyOrderButton.setText("Show My Order");
        showMyOrderButton.setId("show-order");
        showMyOrderButton.setOnAction(controller.switchView(showMyOrderButton));

        // create logoutButton
        logoutButton = new Button();
        logoutButton.setText("Logout");
        logoutButton.setId("logout");
        logoutButton.setOnAction(controller.logout());
    }

    private void createLabel() {
        greetingLabel = new Label();
        greetingLabel.setText("Welcome to our Restaurant");
        greetingLabel.setFont(new Font("Arial", 30));
    }

    public CustomerViewController getController() {
        return controller;
    }

}
