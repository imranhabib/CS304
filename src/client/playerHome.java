package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Imran on 2016-02-17.
 */
public class playerHome {

    public String playerTitle = "Player Portal";
    public String playerInformation = "Welcome to the Player Management Application. Click menu to get started";
    public String accountInfo = "Select to view your account information";
    public String searchInfo = "Select to begin a basic search";
    public String advancedSearchInfo = "Select to begin an advanced search";
    public String accountChangeHistory = "Select to view recent account changes";
    public String[] menuItems = {"Account Information","Search Information","Advanced Search Information","Account Change History"};
    database db;
    Connection dbConnect;
    BorderPane root;


    public playerHome(){
        Stage playerStage = new Stage();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Scene playerScene = new Scene(createBorderPane(), primaryScreenBounds.getMinX(), primaryScreenBounds.getMinY());
        playerStage.setScene(playerScene);
        db = getDatabase();
        playerStage.show();

    }


    public BorderPane createBorderPane(){
        root = new BorderPane();
        TextField tpain = new TextField();
        tpain.setFont(Font.font("Calibri Light", FontWeight.THIN, 30));
        tpain.setText(playerTitle);
        tpain.setEditable(false);
        TextField playerInfo = new TextField(playerInformation);
        playerInfo.setFont(Font.font("Calibri Light", FontWeight.THIN, 20));



        root.setTop(tpain);

        //Set middle part of window
        GridPane layout = createInnerWindow();
        root.setCenter(layout);


        //Set left part of window
        root.setLeft(leftWindow());



        return root;



    }





    public GridPane createInnerWindow(){
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(20, 10, 0, 10));


        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(50);
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(50);
        layout.getColumnConstraints().addAll(leftColumn, rightColumn);


        return layout;
    }



    public database getDatabase(){
        db = new database();
        dbConnect = db.createDBConnection();
        return db;
    }

    public VBox leftWindow(){
        VBox leftSide = new VBox();
        leftSide.setSpacing(10);
        leftSide.setPadding(new Insets(10));


        ArrayList<TitledPane> menuList = new ArrayList<TitledPane>();
        for(int i = 0; i < menuItems.length; i++){
            menuList.add(createTPane(menuItems[i]));
            leftSide.setMargin(menuList.get(i), new Insets(0, 0, 0, 8));
            leftSide.getChildren().add(menuList.get(i));
        }


        ArrayList<Button> menuButtonList = new ArrayList<Button>();


        return leftSide;
    }


    public TitledPane createTPane(String title){
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        TitledPane pane = new TitledPane();
        pane.setFont(Font.font("Calibri Light", FontWeight.THIN, 20));
        pane.setText(title);

        TextField rootTitle = new TextField();
        rootTitle.setFont(Font.font("Calibri Light", FontWeight.THIN, 15));
        rootTitle.setEditable(false);

        Button menuButton;

        if(title.equals(menuItems[0])){
            rootTitle.setText(accountInfo);
            menuButton = createMenuButtons(menuItems[0]);
            root.getChildren().addAll(rootTitle, menuButton);
        }
        if(title.equals(menuItems[1])){
            rootTitle.setText(searchInfo);
            menuButton = createMenuButtons(menuItems[1]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if(title.equals(menuItems[2])){
            rootTitle.setText(advancedSearchInfo);
            menuButton = createMenuButtons(menuItems[2]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if(title.equals(menuItems[3])){
            rootTitle.setText(accountChangeHistory);
            menuButton = createMenuButtons(menuItems[3]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        pane.setContent(root);
        pane.setExpanded(false);
        return pane;
    }


    public Button createMenuButtons(String title){
        final String actionTitle = title;
        final Button menuButton = new Button(title);
        menuButton.setFont(Font.font("Calibri Light", FontWeight.THIN, 20));

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(actionTitle.equals(menuItems[0])) {
                     accountAction();
                }
                if(actionTitle.equals(menuItems[1])) {
                    searchAction();
                }
                if(actionTitle.equals(menuItems[2])) {
                    advancedSearchAction();
                }
                if(actionTitle.equals(menuItems[3])) {
                    changesAction();
                }

            }
        });

        return menuButton;
    }



    public void accountAction(){

        db.selectAllAccountInformation(dbConnect);
        root.setCenter(createUserAccount());
    }
    public void searchAction(){
        System.out.println("2");
    }
    public void advancedSearchAction(){
        System.out.println("3");
    }
    public void changesAction(){
        System.out.println("4");
    }



    public BorderPane createUserAccount(){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("Account");
        account.setTop(title);

        return account;
    }








































}

