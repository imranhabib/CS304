package client;

import java.sql.*;
import java.util.ArrayList;

import Objects.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

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
    public String[] menuItems = {"Personal Information", "Search", "Advanced Search", "Account Change History"};
    public String[] searchItems = {"My Team", "All Teams", "All Players", "All Governing Bodies", "All Leagues", "All Managers"};
    public String myTeam = "Select to view your team information";
    public String allTeams = "Select to view all teams information";
    public String allPlayers = "Select to view all player information";
    public String allGoverningBodies = "Select to view information for all governing bodies";
    public String allLeagues = "Select to view all leagues";
    public String allManagers = "Select to view all managers";


    private Font fontSmall = Font.font("Calibri Light", FontWeight.THIN, 20);
    private Font fontSuperSmall = Font.font("Calibri Light", FontWeight.THIN, 10);
    private Font fontLarge = Font.font("Calibri Light", FontWeight.THIN, 30);
    private Font fontLargeBold = Font.font("Calibri Light", FontWeight.BOLD, 30);
    public BorderPane root;
    public Pagination page;
    public int numberOfTeams;
    public int numberOfGBodies;
    public int numberOfLeagues;


    public int userSIN;
    database db;
    Connection dbConnect;
    Scene mainScene;
    Stage managerStage;


    public managerHome() {
        managerStage = new Stage();
        managerStage.setTitle("Manager Portal");
        managerStage.initStyle(StageStyle.DECORATED);
        root = new BorderPane();

        // first make the user type in the number
        mainScene = new Scene(root, 800, 600);
        userLogin();
        managerStage.setScene(mainScene);
        managerStage.show();
    }

    public void createBorderPane(){
        final TextField tpain = new TextField();
        tpain.setFont(fontLarge);
        tpain.setText(managerTitle);
        tpain.setEditable(false);
        TextField playerInfo = new TextField(managerInformation);
        playerInfo.setFont(fontSmall);


        root.setTop(tpain);

        //Set middle part of window
        GridPane layout = createInnerWindow();
        root.setCenter(layout);

        //Set left part of window
        root.setLeft(leftWindow());


    }

    public void userLogin() {
        final TitledPane tpain = new TitledPane();
        tpain.setFont(fontLarge);
        tpain.setText(loginTitle2);
        tpain.setPadding(new Insets(10, 10, 10, 10));
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
                Label errorLabel = new Label(msg + " is not a valid input. Please input your SIN.");
                errorLabel.setFont(fontLarge);
                errorLabel.setTextFill(Color.web("red"));
                root.setBottom(errorLabel);

                if(checkButtonInput(input.getText())){
                    errorLabel.setVisible(false);
                    userSIN = Integer.parseInt(input.getText());
                    System.out.println(userSIN);
                    db = getDatabase(userSIN);
                    boolean valid = checkManagerSINValid(db);

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

    public database getDatabase(int userSIN){
        db = new database(userSIN);
        dbConnect = db.createDBConnection();
        return db;
    }


    public boolean checkButtonInput(String input) {
        boolean isValid;
        try {
            Integer.parseInt(input, 10);
            isValid = true;
        } catch (NumberFormatException x) {
            isValid = false;
        }
        return isValid;
    }

    public boolean checkManagerSINValid(database db) {
        boolean mngSINValid;
        if (db.checkIfManagerSINExists(dbConnect)) {
            mngSINValid = true;
            return mngSINValid;
        } else {
            mngSINValid = false;
            return mngSINValid;
        }
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

    public TitledPane createTPane(String title) {
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

        if (title.equals(menuItems[0])) {
            rootTitle.setText(accountInfo);
            menuButton = createMenuButtons(menuItems[0]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if (title.equals(menuItems[1])) {
            rootTitle.setText(searchInfo);
            menuButton = createMenuButtons(menuItems[1]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if (title.equals(menuItems[2])) {
            rootTitle.setText(advancedSearchInfo);
            menuButton = createMenuButtons(menuItems[2]);
            root.getChildren().addAll(rootTitle, menuButton);
        }

        if (title.equals(menuItems[3])) {
            rootTitle.setText(accountChangeHistory);
            menuButton = createMenuButtons(menuItems[3]);
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


    public Button createMenuButtons(String title) {
        final String actionTitle = title;
        final Button menuButton = new Button(title);
        menuButton.setFont(Font.font("Calibri Light", FontWeight.THIN, 15));
        menuButton.setStyle("-fx-focus-color: transparent; -fx-background-color: cadetblue");

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (actionTitle.equals(menuItems[0])) {
                    accountAction();
                }
                if (actionTitle.equals(menuItems[1])) {
                    searchAction();
                }
                if (actionTitle.equals(menuItems[2])) {
                    advancedSearchAction();
                }
                if (actionTitle.equals(menuItems[3])) {
                    changesAction();
                }


            }
        });

        return menuButton;
    }

 /*
    Methods that call database
     */

    public void accountAction() {
        manager user = db.selectAllManagerAccountInformation(dbConnect);
        root.setCenter(createUserAccount(user));
    }

    public void searchAction() {
        BorderPane borders = new BorderPane();
        borders.setPadding(new Insets(20, 10, 0, 10));
        borders.setLeft(leftWindowForSearch());
        root.setCenter(borders);
    }

    public void advancedSearchAction(){
        advancedSearch advanSearch = new advancedSearch(db, dbConnect);
        BorderPane shell = advanSearch.createAdvanShell();

        root.setCenter(shell);

    }
    public void myTeamAction(){}

    public void allTeamsAction(){
        ArrayList<team> t = db.getAllTeams(dbConnect);
        numberOfTeams = t.size();
        root.setCenter(createTeamPages(t));
    }
    public void allLeaguesAction(){
        ArrayList<league> l = db.getAllLeagues(dbConnect);
        numberOfLeagues = l.size();
        root.setCenter(createLeaguePages(l));
    }
    public void allGoverningBodiesAction(){
        ArrayList<GBody> g = db.getAllGBodies(dbConnect);
        numberOfGBodies = g.size();
        root.setCenter(createGbodyPages(g));}

    public void allPlayersAction(){}
    public void allManagersAction(){}

    public void changesAction(){
        System.out.println("4");
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

        form.add(userName, 0, 0);
        form.add(name, 1, 0);
        form.add(userSIN, 3, 0);
        form.add(sin, 4, 0);
        form.add(userJobSecurity, 0, 2);
        form.add(jobsecurity, 1, 2);

        account.setCenter(form);

        return account;
    }


    public BorderPane createLeaguePages(final ArrayList<league> l){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));
        page = new Pagination(numberOfLeagues);
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


    public BorderPane createTeamPages(final ArrayList<team> t){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));
        page = new Pagination(numberOfTeams);
        page.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        page.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer index) {
                return teamInfoPage(t, index);
            }
        });

        account.setCenter(page);
        return account;

    }


    public BorderPane teamInfoPage(ArrayList<team> t, int index){

        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("Team Information");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        account.setTop(title);
        //Body

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        Label teamName = new Label("Name: ");
        form.setHalignment(teamName, HPos.RIGHT);
        TextField tname = new TextField(t.get(index).getName());
        tname.setEditable(false);


        Label teamSlogan= new Label("Slogan: ");
        form.setHalignment(teamSlogan, HPos.RIGHT);
        TextField tSlo = new TextField(t.get(index).getTMSlogan());
        tSlo.setEditable(false);

        Label teamLeague = new Label("League goes here l8r: ");
        form.setHalignment(teamLeague, HPos.RIGHT);
        TextField tLeague = new TextField(Integer.toString(t.get(index).getTeamId()));
        tLeague.setEditable(false);



        form.add(teamName, 0, 0); form.add(tname, 1, 0);
        form.add(teamSlogan, 3, 0); form.add(tSlo, 4, 0);
        form.add(teamLeague, 0, 2); form.add(tLeague, 1, 2);

        account.setCenter(form);


        return account;

    }


    public BorderPane createGbodyPages(final ArrayList<GBody> g){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));
        page = new Pagination(numberOfGBodies);
        page.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        page.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer index) {
                System.out.println(index);
                return gbodyInfoPage(g, index);
            }
        });

        account.setCenter(page);
        return account;

    }
    public BorderPane gbodyInfoPage(ArrayList<GBody> g, int index){

        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("Governing Body Information");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        account.setTop(title);
        //Body

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        Label gBodyName = new Label("Name: ");
        form.setHalignment(gBodyName, HPos.RIGHT);
        TextField gname = new TextField(g.get(index).getName());
        gname.setEditable(false);


        Label gBodyHQ= new Label("HQ: ");
        form.setHalignment(gBodyHQ, HPos.RIGHT);
        TextField gHQ = new TextField(g.get(index).getHQ());
        gHQ.setEditable(false);

        Label gBodyPresident = new Label("President: ");
        form.setHalignment(gBodyPresident, HPos.RIGHT);
        TextField gPres = new TextField(g.get(index).getPresident());
        gPres.setEditable(false);



        form.add(gBodyName, 0, 0); form.add(gname, 1, 0);
        form.add(gBodyHQ, 3, 0); form.add(gHQ, 4, 0);
        form.add(gBodyPresident, 0, 2); form.add(gPres, 1, 2);

        account.setCenter(form);


        return account;

    }




}