package view;

import controller.RegisterViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegisterView implements IView {

    private RegisterViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private GridPane layout;
    private Label firstnameLabel;
    private Label lastnameLabel;
    private Label usernameLabel;
    private Label passwordLabel;
    private Label roleLabel;
    private TextField firstnameTextField;
    private TextField lastnameTextField;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private ChoiceBox<String> role;
    private Button signupButton;
    private Button cancelButton;

    private static RegisterView registerView;
    private RegisterView() {
    }
    public static RegisterView getInstance() {
        if(registerView == null)
            registerView = new RegisterView();
        return registerView;
    }

    @Override
    public void buildViewStage() {
        setController();
        createLabels();
        createTextFields();
        createButtons();
        createChoiceBox();
        setupLayout();

        primaryStage = new Stage();
        primaryStage.setTitle("Register View");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setMinHeight(220);
        primaryStage.setMinWidth(300);
        scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    @Override
    public void setController() {
        this.controller = new RegisterViewController();
    }

    @Override
    public Stage getStage() {
        return primaryStage;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    public TextField getFirstnameTextField() {
        return firstnameTextField;
    }

    public TextField getLastnameTextField() {
        return lastnameTextField;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    public ChoiceBox<String> getRole() {
        return role;
    }

    private void setupLayout() {
        layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(20);
        layout.setHgap(20);

        // add firstname / lastname / username / password
        GridPane.setConstraints(firstnameLabel, 0, 0);
        GridPane.setConstraints(firstnameTextField, 1, 0);
        GridPane.setConstraints(lastnameLabel, 0, 1);
        GridPane.setConstraints(lastnameTextField, 1, 1);
        GridPane.setConstraints(usernameLabel, 0, 2);
        GridPane.setConstraints(usernameTextField, 1, 2);
        GridPane.setConstraints(passwordLabel, 0, 3);
        GridPane.setConstraints(passwordTextField, 1, 3);
        GridPane.setConstraints(roleLabel, 0, 4);
        GridPane.setConstraints(role, 1, 4);

        // add buttons
        GridPane.setConstraints(signupButton, 0, 5);
        GridPane.setConstraints(cancelButton, 1, 5);

        layout.getChildren().addAll(firstnameLabel, firstnameTextField, lastnameLabel,
                lastnameTextField, usernameLabel, usernameTextField, passwordLabel, passwordTextField,
                roleLabel, role, signupButton, cancelButton);
        layout.setAlignment(Pos.CENTER);
    }

    private void createLabels() {
        firstnameLabel = new Label("Firstname");
        lastnameLabel = new Label("Lastname");
        usernameLabel = new Label("Username");
        passwordLabel = new Label("Password");
        roleLabel = new Label("Role");
    }

    private void createTextFields() {
        // create firstname TextField
        firstnameTextField = new TextField();
        firstnameTextField.setPromptText("Firstname");

        // create lastname TextField
        lastnameTextField = new TextField();
        lastnameTextField.setPromptText("Lastname");

        // create username TextField
        usernameTextField = new TextField();
        usernameTextField.setPromptText("Username");

        // create password TextField
        passwordTextField = new PasswordField();
        passwordTextField.setPromptText("Password");
    }

    private void createChoiceBox() {
        role = new ChoiceBox<>();
        role.getItems().addAll("Premium Customer", "NonPremium Customer", "Manager", "Waiter", "Cooker");
        role.setValue("NonPremium Customer");
    }

    private void createButtons() {
        // create signup button
        signupButton = new Button();
        signupButton.setText("Sign Up");
        signupButton.setId("sign");
        signupButton.setOnAction(controller.switchStage(signupButton));

        // create cancel button
        cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setId("cancel");
        cancelButton.setOnAction(controller.switchStage(cancelButton));
    }

}
