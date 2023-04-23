package view;

import controller.LoginViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView implements IView {

    private LoginViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private GridPane layout;
    private Label usernameLabel;
    private Label passwordLabel;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private Button loginButton;
    private Button registerButton;

    private static LoginView loginView;
    private LoginView() {
    }
    public static LoginView getInstance() {
        if(loginView == null)
            loginView = new LoginView();
        return loginView;
    }

    @Override
    public void buildViewStage() {
        setController();
        createLabels();
        createTextFields();
        createButtons();
        setupLayout();

        primaryStage = new Stage();
        primaryStage.setTitle("Login View");
        primaryStage.setMinHeight(220);
        primaryStage.setMinWidth(300);
        scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void setController() {
        this.controller = new LoginViewController();
    }

    @Override
    public Stage getStage() {
        return primaryStage;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    private void setupLayout() {
        layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(20);
        layout.setHgap(20);

        // add username (label / textField) & password (label / textField) to the GridPane layout
        GridPane.setConstraints(usernameLabel, 0, 0);
        GridPane.setConstraints(usernameTextField, 1, 0);
        GridPane.setConstraints(passwordLabel, 0, 1);
        GridPane.setConstraints(passwordTextField, 1, 1);

        GridPane.setConstraints(loginButton, 0, 2);
        GridPane.setConstraints(registerButton, 1, 2);

        layout.getChildren().addAll(usernameLabel, usernameTextField, passwordLabel,
                passwordTextField, loginButton, registerButton);
        layout.setAlignment(Pos.CENTER);
    }

    private void createLabels() {
        usernameLabel = new Label("Username");
        passwordLabel = new Label("Password");
    }

    private void createTextFields() {
        // create username TextField
        usernameTextField = new TextField();
        usernameTextField.setPromptText("Username");

        // create Password TextField;
        passwordTextField = new PasswordField();
        passwordTextField.setPromptText("Password");
    }

    private void createButtons() {
        // create login button
        loginButton = new Button();
        loginButton.setText("Login");
        loginButton.setId("log");
        loginButton.setOnAction(controller.switchStage(loginButton));

        // create register button
        registerButton = new Button();
        registerButton.setText("Register");
        registerButton.setId("reg");
        registerButton.setOnAction(controller.switchStage(registerButton));
    }

}
