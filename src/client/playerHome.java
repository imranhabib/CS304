package client;

import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Created by Imran on 2016-02-17.
 */
public class playerHome {

    public String playerTitle = "Player Portal";

    public playerHome(){
        Stage playerStage = new Stage();
        Scene playerScene = new Scene(createBorderPane(), 1000, 300);
        playerStage.setScene(playerScene);
        playerStage.show();

    }


    public BorderPane createBorderPane(){
        BorderPane root = new BorderPane();
        TitledPane tpain = new TitledPane();
        tpain.setFont(Font.font("Calibri Light", FontWeight.THIN, 30));
        tpain.setText(playerTitle);

        root.setTop(tpain);
        return root;



    }
















}
