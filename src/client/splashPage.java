package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;



/**
 * Created by Imran on 2016-02-17.
 */
public class splashPage extends Application {

    public String programTitle = "Football Player Management Application";
    public String pageTitle = "Home";
    public String playerPortal = "Player Portal";
    public String managerPortal = "Manager Portal";
    public String playerInstruc = "If you're a player please use this portal";
    public String managerInstruc = "If you're a manager please use this portal";
    public String playerTooltip = "Select to enter the Player Portal";
    public String managerTooltip = "Select to enter the Manager Portal";

    @Override
    public void start(Stage splashStage){
        splashStage.setTitle(programTitle);
        createScene(splashStage);
        splashStage.show();

    }

    public void createScene(Stage splashStage){
        //column , row
        Scene splashScene = new Scene(createLayout(), 1000, 300);
        splashScene.getStylesheets().add
                (splashPage.class.getResource("style.css").toExternalForm());
        splashStage.setScene(splashScene);

    }

    public GridPane createLayout(){
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(20, 10, 0, 10));


        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(50);
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(50);
        layout.getColumnConstraints().addAll(leftColumn, rightColumn);

        HBox homeBox = new HBox();
        homeBox.setPadding(new Insets(15, 12, 15, 12));
        homeBox.setSpacing(10);
        homeBox.setStyle("-fx-background-color: #336699;");

        HBox homeBox2 = new HBox();
        homeBox2.setPadding(new Insets(15, 12, 15, 12));
        homeBox2.setSpacing(10);
        homeBox2.setStyle("-fx-background-color: #580000;");

        HBox homeBox3 = new HBox();
        homeBox3.setPadding(new Insets(15, 12, 15, 12));
        homeBox3.setSpacing(10);
        homeBox3.setStyle("-fx-background-color: #336699;");

        HBox homeBox4 = new HBox();
        homeBox4.setPadding(new Insets(15, 12, 15, 12));
        homeBox4.setSpacing(10);
        homeBox4.setStyle("-fx-background-color: #580000;");


        Text home = new Text(pageTitle);
        home.setFont(Font.font("Calibri Light", FontWeight.THIN, 20));


        Text playerTitle  = new Text(playerPortal);
        playerTitle.setFont(Font.font("Calibri Light", FontWeight.THIN, 30));


        Text managerTitle  = new Text(managerPortal);
        managerTitle.setFont(Font.font("Calibri Light", FontWeight.THIN, 30));


        TextField playerInstructions = new TextField(playerInstruc);
        playerInstructions.setFont(Font.font("Calibri Light", FontWeight.EXTRA_BOLD, 20));
        playerInstructions.setEditable(false);
        playerInstructions.setDisable(true);
        TextField managerInstructions = new TextField(managerInstruc);
        managerInstructions.setFont(Font.font("Calibri Light", FontWeight.THIN, 20));
        managerInstructions.setEditable(false);
        managerInstructions.setDisable(true);
       // managerInstructions.setBackground(Region.Color.GOLD);


        final Button playerButton = new Button("Enter");
        final Button managerButton = new Button("Enter");
        playerButton.setFont(Font.font("Calibri Light", FontWeight.THIN, 20));
        managerButton.setFont(Font.font("Calibri Light", FontWeight.THIN, 20));
        playerButton.setTooltip(new Tooltip(playerTooltip));
        managerButton.setTooltip(new Tooltip(managerTooltip));





        // Set Layouts
        layout.add(homeBox, 0, 0);
        layout.add(homeBox2, 1, 0);
        layout.add(playerTitle, 0, 1);
        layout.add(managerTitle, 1, 1);
        layout.add(playerInstructions, 0, 2);
        layout.add(managerInstructions, 1, 2);


        layout.add(playerButton, 0, 6);
        layout.add(managerButton, 1, 6);
        layout.add(homeBox3, 0, 7);
        layout.add(homeBox4, 1, 7);

        playerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                playerHome playerPage = new playerHome();
                playerButton.setDisable(true);
            }
        });


        managerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                managerHome managerPage = new managerHome();
                managerButton.setDisable(true);
            }
        });



        return layout;
    }















    public static void main(String[] args){
       launch(args);

    }




}
