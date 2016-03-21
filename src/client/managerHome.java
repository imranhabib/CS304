package client;

import java.sql.*;

import Objects.manager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Imran on 2016-02-17.
 */
public class managerHome {

    public String managerTitle = "Manager Portal";
    public String loginTitle = "Please enter your SIN below to gain access to the portal";
    public String loginTitle2 = "Portal Access";
    public String submitBox = "Manager SIN";
    public String managerInformation = "Welcome to the Manager Management Application. Click menu to get started";

    public String accountInfo = "Select to view your personal information";
    public String searchInfo = "Select to begin a basic search";
    public String advancedSearchInfo = "Select to begin an advanced search";
    public String accountChangeHistory = "Select to view recent account changes";
    public String[] menuItems = {"Personal Information", "Contract Information", "Search","Advanced Search","Account Change History"};

    private Font fontSmall = Font.font("Calibri Light", FontWeight.THIN, 20);
    private Font fontSuperSmall = Font.font("Calibri Light", FontWeight.THIN, 10);
    private Font fontLarge = Font.font("Calibri Light", FontWeight.THIN, 30);
    private Font fontLargeBold = Font.font("Calibri Light", FontWeight.BOLD, 30);
    public BorderPane root;
    public int numberOfTeams;
    public Pagination page;

    public int userSIN;
    database db;
    Connection dbConnect;
    Scene mainScene;
    Stage managerStage;


    public managerHome(){
        managerStage = new Stage();
        managerStage.setTitle("Manager Portal");
        managerStage.initStyle(StageStyle.DECORATED);
        root = new BorderPane();

        // first make the user type in the number
        mainScene = new Scene(root, 1000,1000);
        userLogin();
        managerStage.setScene(mainScene);
        managerStage.show();
}

    public BorderPane createBorderPane(){
        BorderPane root = new BorderPane();
        TitledPane tpain = new TitledPane();
        tpain.setFont(Font.font("Calibri Light", FontWeight.THIN, 30));
        tpain.setText(managerTitle);
        root.setTop(tpain);
        userLogin();
        return root;

    }

    public void userLogin(){
        final TitledPane tpain = new TitledPane();
        tpain.setFont(fontLarge);
        tpain.setText(loginTitle2);
        tpain.setPadding(new Insets(10, 10, 10 , 10));
        root.setPadding(new Insets(15, 10, 10, 15));
        Text title = new Text(loginTitle);
        tpain.setContent(title);
        tpain.setFont(fontSmall);
        TextField playerInfo = new TextField(managerInformation);
        playerInfo.setFont(fontSmall);

        root.setTop(tpain);

        //Set middle part of window
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(20, 10, 0, 10));

        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(100);
        layout.getColumnConstraints().addAll(leftColumn);


        Label loginLabel = new Label(loginTitle);
        loginLabel.setFont(fontSmall);
        final TextField input = new TextField();
        input.setFont(fontSmall);

        final Button submitButton = new Button("Submit");

        submitButton.setFont(fontSmall);
        submitButton.setTooltip(new Tooltip("Click to submit SIN"));


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String msg = "'" + input.getText() + "'";
                Label errorLabel = new Label(msg  + " is not a valid input. Please input your SIN.");
                errorLabel.setFont(fontLarge);
                errorLabel.setTextFill(Color.web("red"));
                root.setBottom(errorLabel);
                if(checkButtonInput(input.getText())){
                    errorLabel.setVisible(false);
                    userSIN = Integer.parseInt(input.getText());
                    System.out.println(userSIN);
                    db = getDatabase(userSIN);
                    boolean valid = checkManagerNameValid(db);

                    if(valid) {
                        createBorderPane();
                    } else {
                        errorLabel.setText("Manager SIN: " + msg + " does not exist");
                        errorLabel.setVisible(true);
                    }
                } else {
                    errorLabel.setVisible(true);

                }

            }
        });


        Label vertLabel = new Label(submitBox);
        vertLabel.setFont(fontSmall);


        VBox stackBox = new VBox();
        stackBox.setSpacing(5);
        stackBox.setPadding(new Insets(50, 10, 10, 10));

        stackBox.getChildren().addAll(vertLabel, input, submitButton);

        layout.setGridLinesVisible(false);
        layout.getChildren().add(stackBox);

        root.setCenter(layout);

    }

    public boolean checkButtonInput(String input){
        boolean isValid;
        try {
            Integer.parseInt(input, 10);
            isValid = true;
        } catch (NumberFormatException x){
            isValid = false;
        }
        return isValid;
    }

    public boolean checkManagerNameValid(database db){
        boolean mngSINValid;
        if(db.checkIfManagerSINExists(dbConnect)){
            mngSINValid = true;
            return mngSINValid;
        } else {
            mngSINValid = false;
            return mngSINValid;
        }
    }


    public BorderPane createUserAccount(manager user){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("Account: " + user.getName());
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        account.setTop(title);
        //Body

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        Label userName = new Label("NAME: ");
        form.setHalignment(userName, HPos.RIGHT);
        TextField name = new TextField(user.getName());
        name.setEditable(false);

        Label userSIN = new Label("SIN: ");
        form.setHalignment(userSIN, HPos.RIGHT);
        TextField sin = new TextField(Integer.toString(user.getSIN()));
        sin.setEditable(false);

        Label userJobSecurity = new Label("JOB SECURITY: ");
        form.setHalignment(userJobSecurity, HPos.RIGHT);
        TextField jobsecurity = new TextField(Integer.toString(user.getJobSecurity()));
        jobsecurity.setEditable(false);

        form.add(userName, 0, 0); form.add(name, 1, 0);
        form.add(userSIN, 3, 0); form.add(sin, 4, 0);
        form.add(userJobSecurity, 0, 2); form.add(jobsecurity, 1, 2);

        account.setCenter(form);

        return account;
    }

    public database getDatabase(int SIN){
        db = new database(SIN);
        dbConnect = db.createDBConnection();
        return db;
    }

}