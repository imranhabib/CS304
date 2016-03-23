package client;

import Objects.contract;
import Objects.league;
import Objects.player;
import com.sun.media.jfxmedia.events.PlayerStateEvent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Imran on 2016-02-17.
 */
public class playerHome {

    public String loginTitle = "Please enter your squad number below to gain access to the portal";
    public String playerTitle = "Player Portal";
    public String loginTitle2 = "Portal Access";
    public String submitBox = "Squad Number";
    public String playerInformation = "Welcome to the Player Management Application. Click menu to get started";
    public String accountInfo = "Select to view your personal information";
    public String contractInfo = "Select to view your contract information";
    public String searchInfo = "Select to begin a basic search";
    public String advancedSearchInfo = "Select to begin an advanced search";
    public String accountChangeHistory = "Select to view recent account changes";
    public String myTeam = "Select to view your team information";
    public String allTeams = "Select to view all teams information";
    public String allPlayers = "Select to view all player information";
    public String allGoverningBodies = "Select to view information for all governing bodies";
    public String allLeagues = "Select to view all leagues";
    public String allManagers = "Select to view all managers";
    public String[] menuItems = {"Personal Information", "Contract Information", "Search","Advanced Search","Account Change History"};
    public String[] searchItems = {"My Team", "All Teams", "All Players", "All Governing Bodies", "All Leagues", "All Managers"};
    private Font fontSmall = Font.font("Calibri Light", FontWeight.THIN, 20);
    private Font fontSuperSmall = Font.font("Calibri Light", FontWeight.THIN, 10);
    private Font fontLarge = Font.font("Calibri Light", FontWeight.THIN, 30);
    private Font fontLargeBold = Font.font("Calibri Light", FontWeight.BOLD, 30);
    public BorderPane root;
    public int numberOfTeams;
    public Pagination page;


    public int userSquadNumber;
    database db;
    Connection dbConnect;
     Scene mainScene;
    Stage playerStage;
    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();


    public playerHome(){
        playerStage = new Stage();
        playerStage.setTitle("Player Portal");
        playerStage.initStyle(StageStyle.DECORATED);
        root = new BorderPane();

        // first make the user type in the number
        mainScene = new Scene(root, 1000,1000);
        userLogin();
        playerStage.setScene(mainScene);
        playerStage.show();
    }


    public void createBorderPane(){
        final TextField tpain = new TextField();
        tpain.setFont(fontLarge);
        tpain.setText(playerTitle);
        tpain.setEditable(false);
        TextField playerInfo = new TextField(playerInformation);
        playerInfo.setFont(fontSmall);


        root.setTop(tpain);

        //Set middle part of window
        GridPane layout = createInnerWindow();
        root.setCenter(layout);

        //Set left part of window
        root.setLeft(leftWindow());


    }



    public void userLogin(){
        //root = new BorderPane();
        final TitledPane tpain = new TitledPane();
        tpain.setFont(fontLarge);
        tpain.setText(loginTitle2);
        tpain.setPadding(new Insets(10, 10, 10 , 10));
        root.setPadding(new Insets(15, 10, 10, 15));
        Text title = new Text(loginTitle);
        tpain.setContent(title);
        tpain.setFont(fontSmall);
        TextField playerInfo = new TextField(playerInformation);
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
        submitButton.setTooltip(new Tooltip("Click to submit squad number"));


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String msg = "'" + input.getText() + "'";
                Label errorLabel = new Label(msg  + " is not a valid input. Please input a number.");
                errorLabel.setFont(fontLarge);
                errorLabel.setTextFill(Color.web("red"));
                root.setBottom(errorLabel);
                if(checkButtonInput(input.getText())){
                   errorLabel.setVisible(false);
                    userSquadNumber = Integer.parseInt(input.getText());
                    System.out.println(userSquadNumber);
                    db = getDatabase(userSquadNumber);
                    boolean valid = checkSquadNumberValid(db);

                    if(valid) {
                        createBorderPane();
                    } else {
                        errorLabel.setText("Squad Number: " + msg + " does not exist");
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


    public database getDatabase(int sqNum){
        db = new database(sqNum);
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


    public VBox leftWindowForSearch(){
        VBox leftSide = new VBox();
        leftSide.setSpacing(10);
        leftSide.setPadding(new Insets(10));


        ArrayList<TitledPane> searchList = new ArrayList<TitledPane>();
        for(int i = 0; i < searchItems.length; i++){
            searchList.add(createSearchPane(searchItems[i]));
            leftSide.setMargin(searchList.get(i), new Insets(0, 0, 0, 8));
            leftSide.getChildren().add(searchList.get(i));
        }




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
            rootTitle.setText(contractInfo);
            menuButton = createMenuButtons(menuItems[1]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if(title.equals(menuItems[2])){
            rootTitle.setText(searchInfo);
            menuButton = createMenuButtons(menuItems[2]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if(title.equals(menuItems[3])){
            rootTitle.setText(advancedSearchInfo);
            menuButton = createMenuButtons(menuItems[3]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if(title.equals(menuItems[4])){
            rootTitle.setText(accountChangeHistory);
            menuButton = createMenuButtons(menuItems[4]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        pane.setContent(root);
        pane.setExpanded(false);
        return pane;
    }



    public TitledPane createSearchPane(String title){
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));



        TitledPane pane = new TitledPane();
        pane.setFont(Font.font("Calibri Light", FontWeight.THIN, 15));
        pane.setText(title);

        TextField rootTitle = new TextField();
        rootTitle.setFont(Font.font("Calibri Light", FontWeight.THIN, 10));
        rootTitle.setEditable(false);

        Button menuButton;

        if(title.equals(searchItems[0])){
            rootTitle.setText(myTeam);
            menuButton = createSearchButtons(searchItems[0]);
            root.getChildren().addAll(rootTitle, menuButton);
        }
        if(title.equals(searchItems[1])){
            rootTitle.setText(allTeams);
            menuButton = createSearchButtons(searchItems[1]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if(title.equals(searchItems[2])){
            rootTitle.setText(allPlayers);
            menuButton = createSearchButtons(searchItems[2]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if(title.equals(searchItems[3])){
            rootTitle.setText(allGoverningBodies);
            menuButton = createSearchButtons(searchItems[3]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if(title.equals(searchItems[4])){
            rootTitle.setText(allLeagues);
            menuButton = createSearchButtons(searchItems[4]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if(title.equals(searchItems[5])){
            rootTitle.setText(allManagers);
            menuButton = createSearchButtons(searchItems[5]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        pane.setContent(root);
        pane.setExpanded(false);
        return pane;
    }


    public Button createSearchButtons(String title){
        final String actionTitle = title;
        final Button searchButton = new Button(title);
        searchButton.setFont(Font.font("Calibri Light", FontWeight.THIN, 15));
        searchButton.setStyle("-fx-focus-color: transparent; -fx-background-color: cadetblue");

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(actionTitle.equals(searchItems[0])) {
                    myTeamAction();
                }
                if(actionTitle.equals(searchItems[1])) {
                    allTeamsAction();
                }
                if(actionTitle.equals(searchItems[2])) {
                    allPlayersAction();
                }
                if(actionTitle.equals(searchItems[3])) {
                    allGoverningBodiesAction();
                }
                if(actionTitle.equals(searchItems[4])) {
                    allLeaguesAction();
                }
                if(actionTitle.equals(searchItems[5])) {
                    allManagersAction();
                }

            }
        });

        return searchButton;
    }

























    public Button createMenuButtons(String title){
        final String actionTitle = title;
        final Button menuButton = new Button(title);
        menuButton.setFont(Font.font("Calibri Light", FontWeight.THIN, 15));
        menuButton.setStyle("-fx-focus-color: transparent; -fx-background-color: cadetblue");

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(actionTitle.equals(menuItems[0])) {
                     accountAction();
                }
                if(actionTitle.equals(menuItems[1])) {
                    contractAction();
                }
                if(actionTitle.equals(menuItems[2])) {
                    searchAction();
                }
                if(actionTitle.equals(menuItems[3])) {
                   advancedSearchAction();
                }
                if(actionTitle.equals(menuItems[4])) {
                    changesAction();
                }

            }
        });

        return menuButton;
    }



    public void accountAction(){
       player user = db.selectAllAccountInformation(dbConnect);
        root.setCenter(createUserAccount(user));
    }
    public void searchAction(){
        BorderPane borders = new BorderPane();
        borders.setPadding(new Insets(20, 10, 0, 10));
        borders.setLeft(leftWindowForSearch());
        root.setCenter(borders);
    }
    public void contractAction(){
       contract userContract = db.selectUserContractInformationBySquadNumber(dbConnect);
        root.setCenter(createUserContract(userContract));
    }
    public void advancedSearchAction(){
        System.out.println("3");
    }
    public void changesAction(){
        System.out.println("4");
    }




    public void myTeamAction(){}
    public void allTeamsAction(){}
    public void allLeaguesAction(){
        ArrayList<league> l = db.getAllLeagues(dbConnect);
        numberOfTeams = l.size();
        for(int i = 0; i < l.size(); i++){
            System.out.println(l.get(i).getCountry());
        }

        root.setCenter(createLeaguePages(l));
    }
    public void allGoverningBodiesAction(){}
    public void allPlayersAction(){}
    public void allManagersAction(){}





















    public BorderPane createUserAccount(player user){
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

        Label userName = new Label("Account Holder: ");
        form.setHalignment(userName, HPos.RIGHT);
        TextField name = new TextField(user.getName());
        name.setEditable(false);

        Label userSqNum = new Label("Squad Number: ");
        form.setHalignment(userSqNum, HPos.RIGHT);
        TextField sqNum = new TextField(Integer.toString(user.getSquadNumber()));
        sqNum.setEditable(false);

        Label userAge = new Label("Age: ");
        form.setHalignment(userAge, HPos.RIGHT);
        TextField age = new TextField(Integer.toString(user.getAge()) + " Yrs");
        age.setEditable(false);

        Label userPos = new Label("Position: ");
        form.setHalignment(userPos, HPos.RIGHT);
        TextField pos = new TextField(user.getPosition());
        pos.setEditable(false);

        Label userPrice = new Label("Price: ");
        form.setHalignment(userPrice, HPos.RIGHT);
        TextField price = new TextField(Integer.toString(user.getPrice()) + " $");
        price.setEditable(false);

        Label userSalary = new Label("Salary: " );
        form.setHalignment(userSalary, HPos.RIGHT);
        TextField sal = new TextField(Integer.toString(user.getSalary()) + " $/Yr");
        sal.setEditable(false);

        Label userNationality = new Label("Nationality: ");
        form.setHalignment(userNationality, HPos.RIGHT);
        TextField nation = new TextField(user.getNationality());
        nation.setEditable(false);


        Label userAvailability = new Label("Available: ");
        form.setHalignment(userAvailability, HPos.RIGHT);
        TextField avail = new TextField();
        if(user.isAvailability()){
             avail.setText("Yes!");
        } else {
            avail.setText("No");
        }


        avail.setEditable(false);

        Label userRating = new Label("Rating: ");
        form.setHalignment(userRating, HPos.RIGHT);
        TextField rate = new TextField(Integer.toString(user.getRating()) + " Pts");
        rate.setEditable(false);

        form.add(userName, 0, 0); form.add(name, 1, 0);
        form.add(userSqNum, 3, 0); form.add(sqNum, 4, 0);
        form.add(userAge, 0, 2); form.add(age, 1, 2);
        form.add(userPos, 3, 2); form.add(pos, 4, 2);
        form.add(userPrice, 0, 4); form.add(price, 1, 4);
        form.add(userSalary, 3, 4); form.add(sal, 4, 4);
        form.add(userNationality, 0, 6); form.add(nation, 1, 6);
        form.add(userAvailability, 3, 6); form.add(avail, 4, 6);
        form.add(userRating, 0, 8); form.add(rate, 1, 8);

        account.setCenter(form);


        return account;
    }



    public BorderPane createUserContract(contract user){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("Contractual Information");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        account.setTop(title);
        //Body

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        Label userCon = new Label("Contract Number: ");
        form.setHalignment(userCon, HPos.RIGHT);
        TextField con = new TextField(Integer.toString(user.getContractNum()));
        con.setEditable(false);


        Label userSqNum = new Label("Squad Number: ");
        form.setHalignment(userSqNum, HPos.RIGHT);
        TextField sqNum = new TextField(Integer.toString(user.getSquadNumber()));
        sqNum.setEditable(false);

        Label userDur = new Label("Duration: ");
        form.setHalignment(userDur, HPos.RIGHT);
        TextField dur = new TextField(Integer.toString(user.getDuration()) + " Mnths");
        dur.setEditable(false);

        Label userRem = new Label("Length Remaining: ");
        form.setHalignment(userRem, HPos.RIGHT);
        TextField rem = new TextField(Integer.toString(user.getLenRemain()) + " Mnths");
        rem.setEditable(false);

        Label userLoanOp = new Label("Loan Available?: ");
        form.setHalignment(userLoanOp, HPos.RIGHT);
        TextField avail = new TextField();
        if(user.isLoanOption()){
            avail.setText("Yes!");
        } else {
            avail.setText("No");
        }
        avail.setEditable(false);


        form.add(userCon, 0, 0); form.add(con, 1, 0);
        form.add(userSqNum, 3, 0); form.add(sqNum, 4, 0);
        form.add(userDur, 0, 2); form.add(dur, 1, 2);
        form.add(userRem, 3, 2); form.add(rem, 4, 2);
        form.add(userLoanOp, 0, 4); form.add(avail, 1, 4);

        account.setCenter(form);


        return account;
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

    public boolean checkSquadNumberValid(database db){
        boolean sqdNumberValid;
        if(db.checkIfSquadNumberExists(dbConnect)){
            sqdNumberValid = true;
            return sqdNumberValid;
        } else {
            sqdNumberValid = false;
            return sqdNumberValid;
        }
    }



    public BorderPane createLeaguePages(final ArrayList<league> l){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));
        page = new Pagination(numberOfTeams);
        page.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        page.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer index) {
                System.out.println(index);
               return leagueInfoPage(l, index);
            }
        });

        account.setCenter(page);
        return account;

    }

    public BorderPane leagueInfoPage(ArrayList<league> l, int index){

        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("League Information");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        account.setTop(title);
        //Body

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        Label leagueName = new Label("Name: ");
        form.setHalignment(leagueName, HPos.RIGHT);
        TextField lname = new TextField(l.get(index).getName());
        System.out.println("this is name " + l.get(index).getName());
        lname.setEditable(false);


        Label leagueCountry = new Label("Country: ");
        form.setHalignment(leagueCountry, HPos.RIGHT);
        TextField lcountry = new TextField(l.get(index).getCountry());
        lcountry.setEditable(false);

        Label leagueSponsor = new Label("Sponsor: ");
        form.setHalignment(leagueSponsor, HPos.RIGHT);
        TextField lSpon = new TextField(l.get(index).getSponsor());
        lSpon.setEditable(false);

        Label leagueTeams = new Label("Teams in League: ");
        form.setHalignment(leagueTeams, HPos.RIGHT);
        TextField lTeams = new TextField(Integer.toString(l.get(index).getNumberOfTeams()));
        lTeams.setEditable(false);


        form.add(leagueName, 0, 0); form.add(lname, 1, 0);
        form.add(leagueCountry, 3, 0); form.add(lcountry, 4, 0);
        form.add(leagueSponsor, 0, 2); form.add(lSpon, 1, 2);
        form.add(leagueTeams, 3, 2); form.add(lTeams, 4, 2);

        account.setCenter(form);


        return account;

    }


}

