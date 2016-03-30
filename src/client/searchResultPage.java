package client;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Imran on 2016-03-29.
 */
public class searchResultPage {

    private BorderPane root;
    private Scene mainScene;
    private Stage resultStage;

    public searchResultPage(){
        resultStage = new Stage();
        resultStage.setTitle("Advanced Search Results");
        resultStage.initStyle(StageStyle.DECORATED);
        root = new BorderPane();

        // first make the user type in the number
        mainScene = new Scene(root, 800,600);
        resultStage.setScene(mainScene);
        resultStage.show();


    }


    public void createBorderPane(BorderPane layout){
        root.setCenter(layout);

    }
















}
