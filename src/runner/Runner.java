package runner;

import view.IView;
import view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Runner extends Application {

    private final IView loginView = LoginView.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        loginView.buildViewStage();
    }
}
