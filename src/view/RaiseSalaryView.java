package view;

import controller.RaiseSalaryViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RaiseSalaryView implements IView {

    private RaiseSalaryViewController controller;
    private Stage primaryStage;
    private Scene scene;
    private VBox parentLayout;
    private Label usernameLabel;
    private Label raiseLabel;
    private TextField usernameTextField;
    private TextField raiseTextField;
    private Button raiseButton;
    private Button closeButton;

    private static RaiseSalaryView raiseSalaryView;
    private RaiseSalaryView() {
    }
    public static RaiseSalaryView getInstance() {
        if (raiseSalaryView == null)
            raiseSalaryView = new RaiseSalaryView();
        return raiseSalaryView;
    }

    @Override
    public void buildViewStage() {
        setController();
        createLabels();
        createTextFields();
        createButtons();
        setupParentLayout();

        primaryStage = new Stage();
        primaryStage.setTitle("Raise Salary View");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(parentLayout);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    @Override
    public void setController() {
        this.controller = new RaiseSalaryViewController();
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
        parentLayout.getChildren().addAll(setupLabelsAndTextFieldsLayout(), setupButtonsLayout());
    }

    private GridPane setupLabelsAndTextFieldsLayout() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.TOP_CENTER);

        GridPane.setConstraints(usernameLabel, 0, 0);
        GridPane.setConstraints(usernameTextField, 1, 0);
        GridPane.setConstraints(raiseLabel, 0, 1);
        GridPane.setConstraints(raiseTextField, 1, 1);
        pane.getChildren().addAll(usernameLabel, usernameTextField, raiseLabel, raiseTextField);
        return pane;
    }

    private GridPane setupButtonsLayout() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.BOTTOM_CENTER);

        GridPane.setConstraints(raiseButton, 0, 0);
        GridPane.setConstraints(closeButton, 1, 0);
        pane.getChildren().addAll(raiseButton, closeButton);
        return pane;
    }

    private void createLabels() {
        // create username label
        usernameLabel = new Label();
        usernameLabel.setText("Username");
        // create raise label
        raiseLabel = new Label();
        raiseLabel.setText("Raise Amount");
    }

    private void createTextFields() {
        // create username textField
        usernameTextField = new TextField();
        usernameTextField.setPromptText("Username");
        // create raise textField
        raiseTextField = new TextField();
        raiseTextField.setPromptText("Raise Amount");
    }

    private void createButtons() {
        // create raise button
        raiseButton = new Button();
        raiseButton.setText("Raise");
        raiseButton.setOnAction(controller.raise());
        // create close button
        closeButton = new Button();
        closeButton.setText("Close");
        closeButton.setOnAction(controller.close());
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public TextField getRaiseTextField() {
        return raiseTextField;
    }

}
