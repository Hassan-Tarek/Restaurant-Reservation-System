package view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public interface IView {

    /**
     * build the view stage
     */
    void buildViewStage();

    /**
     * set the event handler of the view
     */
    void setController();

    /**
     * get the stage of the view
     * @return the view stage
     */
    Stage getStage();

    /**
     * get the scene of the view
     * @return the view scene
     */
    Scene getScene();

}
