package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Created by Imran on 2016-02-17.
 */
public class managerHome {

    public String managerTitle = "Manager Portal";


    public managerHome(){
        Stage managerStage = new Stage();
        Scene managerScene = new Scene(createBorderPane(), 1000, 300);
        managerStage.setScene(managerScene);
        managerStage.show();


    }


    public BorderPane createBorderPane(){
        BorderPane root = new BorderPane();
        TitledPane tpain = new TitledPane();
        tpain.setFont(Font.font("Calibri Light", FontWeight.THIN, 30));
        tpain.setText(managerTitle);

        root.setTop(tpain);
        return root;



    }







}
