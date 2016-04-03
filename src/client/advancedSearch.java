package client;

import Objects.*;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Imran on 2016-03-27.
 */
public class advancedSearch {


    public contract contr;
    public player plyr;
    public GBody govt;
    public league league;
    public manager man;
    public team team;
    database db;
    Connection connection;
    ResultSet result;
    private BorderPane shell = new BorderPane();
    private GridPane form = new GridPane();
    private TableView table = new TableView();
    private int numberOfResults;
    private boolean noElementExists = false;
    private boolean didNotSearchForAnything = false;
    private boolean integerTypeFail = false;
    private Label errorLabel;
    private String style =   "-fx-border-color: red; "
            + "-fx-font-size: 15;"
            + "-fx-border-insets: 5, 0, 5, 5; "
            + "-fx-border-radius: 5;"
            + "-fx-border-style: dashed;"
            + "-fx-border-width: 5;";




    public advancedSearch(database db, Connection connection){
        this.db = db;
        this.connection = connection;
    }



    public BorderPane createAdvanShell(){

        shell.setPadding(new Insets(20, 10, 10, 10));

        BorderPane titlePane = new BorderPane();
        titlePane.setPadding(new Insets(20, 10, 10, 10));
        //Title
        TextField title = new TextField("Advanced Search");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        title.setStyle("-fx-background-color: blanchedalmond");

        titlePane.setTop(title);



        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(5, 5, 5, 5));


        Label searchBy = new Label("Search By: ");
        layout.setHalignment(searchBy, HPos.RIGHT);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Player",
                        "Manager",
                        "Contract",
                        "League",
                        "Team",
                        "Government Body"
                );
        final ComboBox entities = new ComboBox(options);


        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);






        entities.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String entity = (String)entities.getSelectionModel().getSelectedItem();
                if(entity == null){
                    return;
                }
                switch(entity.toLowerCase()) {
                    case "player":
                        shell.setCenter(null);
                        shell.setCenter(handlePlayerSearch());
                        shell.setRight(form);
                        break;
                    case "manager":
                        shell.setTop(null);
                        shell.setCenter(handleManagerSearch());
                        shell.setRight(form);
                        break;
                    case "contract":
                        shell.setTop(null);
                        shell.setCenter(handleContractSearch());
                        shell.setRight(form);
                        break;
                    case "league":
                        shell.setTop(null);
                        shell.setCenter(handleLeagueSearch());
                        shell.setRight(form);
                        break;
                    case "team":
                        shell.setTop(null);
                        shell.setCenter(handleTeamSearch());
                        shell.setRight(form);
                        break;
                    case "government body":
                        shell.setTop(null);
                        shell.setCenter(handleGBodySearch());
                        shell.setRight(form);
                        break;
                    default:
                        break;
                }
            }

        });


        shell.setCenter(titlePane);
        form.add(searchBy, 0, 0); form.add(entities, 1, 0);

        shell.setRight(form);

        return shell;




    }


    public ScrollPane handlePlayerSearch(){
        noElementExists = false;
        didNotSearchForAnything = false;
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("Player Search Criteria");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        account.setTop(title);



        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);


        Label playerPos = new Label("Position: ");
        form.setHalignment(playerPos, HPos.RIGHT);
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "GK", "D", "M", "F"
                );
        final ComboBox pos = new ComboBox(options);

        Label playerPrice = new Label("Price: ");
        form.setHalignment(playerPrice, HPos.RIGHT);
        TextField playerP = new TextField();
        playerP.setEditable(true);

        Label playerName = new Label("Name: ");
        form.setHalignment(playerName, HPos.RIGHT);
        TextField name = new TextField();
        name.setEditable(true);

        Label playerAge = new Label("Age: ");
        form.setHalignment(playerAge, HPos.RIGHT);
        ObservableList<String> ages = FXCollections.observableArrayList();
        for(int j = 16; j < 41; j++) {
            ages.add(Integer.toString(j));
        }
        final ComboBox plyAge = new ComboBox(ages);


        Label playerSal = new Label("Salary($/week): " );
        form.setHalignment(playerSal, HPos.RIGHT);
        TextField sal = new TextField();
        sal.setEditable(true);

        Label playerNationality = new Label("Nationality: ");
        form.setHalignment(playerNationality, HPos.RIGHT);
        TextField nation = new TextField();
        nation.setEditable(true);

        Label playerSquadNo = new Label("Squad Number: ");
        form.setHalignment(playerSquadNo, HPos.RIGHT);
        TextField sqNo = new TextField();
        sqNo.setEditable(true);

        Label userRating = new Label("Rating: ");
        form.setHalignment(userRating, HPos.RIGHT);
        ObservableList<String> rates = FXCollections.observableArrayList();
        for(int i = 50; i < 101; i++) {
            rates.add(Integer.toString(i));
        }
        final ComboBox plyRate = new ComboBox(rates);


        Label availability = new Label("Availability: ");
        form.setHalignment(availability, HPos.RIGHT);
        ObservableList<String> a =
                FXCollections.observableArrayList(
                        "Yes", "No"
                );
        final ComboBox avail = new ComboBox(a);

        Button submit = new Button("Search");
               submit.setFont((Font.font("Calibri Light", FontWeight.THIN, 15)));
        Label something = new Label("Select Your Criteria: ");
               form.setHalignment(something, HPos.RIGHT);
              ObservableList<String> b =
                   FXCollections.observableArrayList(
                               "Rating", "Age", "Salary", "Price"
                         );
                final ComboBox criteria = new ComboBox(b);

                Label teamLabel = new Label("Select The Team To Search: ");
                form.setHalignment(teamLabel, HPos.RIGHT);
                ObservableList<String> teamSelect = FXCollections.observableArrayList();
                ArrayList<team> teamsInDB = db.getAllTeams(connection);
             for(int z = 0; z < teamsInDB.size(); z++) {
            teamSelect.add(teamsInDB.get(z).getName());
                  }
        final ComboBox teamBox = new ComboBox(teamSelect);
       Button submit1 = new Button("Search Highest * Per Team ");
              submit1.setFont((Font.font("Calibri Light", FontWeight.THIN, 15)));
                TextField title1 = new TextField("Find The Highest Something Per Team");
                title1.setEditable(false);
                title1.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));




        form.add(playerPos, 0, 0); form.add(pos, 1, 0);
        form.add(playerPrice, 0, 1); form.add(playerP, 1, 1);
        form.add(playerName, 0, 2); form.add(name, 1, 2);
        form.add(playerAge, 0, 4); form.add(plyAge, 1, 4);
        form.add(playerSal, 0, 5); form.add(sal, 1, 5);
        form.add(playerNationality, 0, 6); form.add(nation, 1, 6);
        form.add(playerSquadNo, 0, 7); form.add(sqNo, 1, 7);
        form.add(userRating, 0, 8); form.add(plyRate, 1, 8);
        form.add(availability, 0, 9); form.add(avail, 1, 9);
        form.add(submit,0,10);
        form.add(title1,0,17,30,1);
        form.add(something, 0, 20); form.add(criteria, 1, 20);
        form.add(teamLabel,0,22); form.add(teamBox,1,22);
        form.add(submit1,0,24);

        account.setCenter(form);



        errorLabel = new Label("");


        VBox stackBox = new VBox();
        stackBox.setSpacing(5);
        stackBox.setPadding(new Insets(50, 10, 10, 10));

        stackBox.getChildren().addAll(errorLabel, submit);



        account.setBottom(stackBox);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int ageInputInt = 0 ;
                int salaryInputInt = 0;
                int squadNumberInputInt = 0;
                int ratingInputInt = 0;
                int priceInputInt = 0;

                String positionInput = (String) (pos.getValue());
                String priceInput = (playerP.getText());
                String nameInput = name.getText();
                String ageInput = (String) plyAge.getValue();
                String salaryInput = sal.getText();
                String nationalityInput = nation.getText();
                String squadNumberInput = sqNo.getText();
                String ratingInput = (String) plyRate.getValue();

                int availabilityInput;

                int errorCheck = 0;
                int errorCheckTotal = 9;

                boolean input1 = false;
                boolean input2 = false;
                boolean input3 = false;
                boolean input4 = false;
                boolean input5 = false;





                if(avail.getValue() == null){
                    errorCheck ++;
                    availabilityInput = 2;
                }
                else if ((avail.getValue()).equals("Yes")) {
                    availabilityInput = 1;
                } else {
                    availabilityInput = 0;
                }


                if(ageInput == null){
                    errorCheck ++;
                    ageInputInt = 0;
                }
                else{
                    ageInputInt = Integer.parseInt((String) plyAge.getValue());
                }

                if(salaryInput.isEmpty()){
                    errorCheck ++;
                    salaryInputInt = 0;
                }
                else{
                    try {
                        input3 = false;
                        integerTypeFail = false;
                        salaryInputInt = Integer.parseInt(sal.getText());
                    } catch (NumberFormatException nf){
                        input3 = true;
                        sal.setStyle(style);
                        integerTypeFail = true;
                        setErrorLabelForTypeChecking(errorLabel);
                    }
                }
                if(ratingInput == null){
                    errorCheck ++;
                    ratingInputInt = 0;
                }
                else{
                    ratingInputInt = Integer.parseInt((String) plyRate.getValue());
                }
                if(squadNumberInput.isEmpty()){
                    errorCheck ++;
                    squadNumberInputInt = 0;
                }
                else{
                    try {
                        input2 = false;
                        integerTypeFail = false;
                        squadNumberInputInt = Integer.parseInt(sqNo.getText());
                    } catch (NumberFormatException nf){
                        input2 = true;
                        sqNo.setStyle(style);
                        integerTypeFail = true;
                        setErrorLabelForTypeChecking(errorLabel);
                    }
                }
                if(positionInput == null){
                    errorCheck ++;
                    positionInput = "";
                }
                else {
                    positionInput = (String) (pos.getValue());
                }
                if(priceInput.isEmpty()){
                    errorCheck ++;
                    priceInputInt = 0;
                }
                else{
                    try {
                        input1 = false;
                        integerTypeFail = false;
                        priceInputInt = Integer.parseInt(playerP.getText());
                    } catch (NumberFormatException nf){
                        input1 = true;
                        playerP.setStyle(style);
                        integerTypeFail = true;
                        setErrorLabelForTypeChecking(errorLabel);
                    }
                }
                if(nameInput.isEmpty() == true){
                    errorCheck ++;
                    nameInput = "";
                }
                else{
                    input4 = false;
                    nameInput = name.getText();
                    if(nameChangeIsValid(nameInput)){
                    } else {
                        input4 = true;
                        name.setStyle(style);
                        integerTypeFail = true;
                        setErrorLabelForTypeChecking(errorLabel);
                    }
                }

                if(nationalityInput.isEmpty()){
                    errorCheck ++;
                    nationalityInput = "";
                }
                else {
                    input5 = false;
                    nationalityInput = nation.getText();
                    if(nameChangeIsValid(nationalityInput)){
                    } else {
                        input5 = true;
                        nation.setStyle(style);
                        integerTypeFail = true;
                        setErrorLabelForTypeChecking(errorLabel);
                    }
                }
                if(integerTypeFail){
                    if(!input1)
                        playerP.setStyle("");
                    if(!input2)
                        sqNo.setStyle("");
                    if(!input3)
                        sal.setStyle("");
                    if(!input4)
                        name.setStyle("");
                    if(!input5)
                        nation.setStyle("");
                    return;
                } else {
                    integerTypeFail = false;

                    if(!input1)
                    playerP.setStyle("");
                    if(!input2)
                    sqNo.setStyle("");
                    if(!input3)
                    sal.setStyle("");
                    if(!input4)
                    name.setStyle("");
                    if(!input5)
                    nation.setStyle("");

                }

                if(errorCheck == errorCheckTotal){
                    didNotSearchForAnything = true;
                    setErrorLabel(errorLabel);
                    return;
                } else {
                    didNotSearchForAnything = false;
                    setErrorLabel(errorLabel);
                    result = db.searchAdvancePlayer(connection, positionInput, priceInputInt, nameInput, ageInputInt, salaryInputInt, nationalityInput, squadNumberInputInt, availabilityInput, ratingInputInt);
                }
                boolean a;
                setNumberOfRows(result);
                try {
                    if (result.getInt("Availability") == 1) {
                        a = true;
                    } else {
                        a = false;
                    }

                    ArrayList<player> p = new ArrayList<>();
                    int rows = 0;
                    while (result.next()) {
                        ++rows;
                    }
                    if (rows == 0) {
                        //handle this case later
                        System.out.println("No records found in the advan");
                    }
                    result.first();
                    didNotSearchForAnything = false;
                    setErrorLabel(errorLabel);
                    for (int i = 0; i < rows + 1; i++) {
                        p.add(new player(result.getString("Position"), result.getInt("Price"), result.getString("Name"),
                                result.getInt("Age"), result.getInt("Salary"), result.getString("Nationality"),
                                result.getInt("SquadNumber"), a, result.getInt("Rating"), result.getInt("TeamID")));
                        result.next();
                    }

                    searchResultPage search = new searchResultPage();
                    search.createBorderPane(setPlayerFields(p));
                } catch (SQLException e) {
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(e);
                } catch (NullPointerException n){
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(n);

                }
            }});



        ScrollPane rootScroll = new ScrollPane();
        rootScroll.setPadding(new Insets(5, 5, 10, 5));
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setContent(account);


        return rootScroll;
    }



    public ScrollPane handleManagerSearch(){
        noElementExists = false;
        didNotSearchForAnything = false;
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("Manager Search Criteria");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        account.setTop(title);



        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);


        Label manName = new Label("Name: ");
        form.setHalignment(manName, HPos.RIGHT);
        ObservableList<String> managerName = FXCollections.observableArrayList();
        ArrayList<manager> managersInDB = db.getAllManagers(connection);
        for(int z = 0; z < managersInDB.size(); z++){
            managerName.add(managersInDB.get(z).getName());
        }
        final ComboBox mname = new ComboBox(managerName);


        Label jobSecurity = new Label("Job Security: ");
        form.setHalignment(jobSecurity, HPos.RIGHT);
        ObservableList<String> JS = FXCollections.observableArrayList();
        for(int i = 0; i < 11; i++) {
            JS.add(Integer.toString(i));
        }
        final ComboBox job = new ComboBox(JS);



        form.add(manName, 0, 0); form.add(mname, 1, 0);
        form.add(jobSecurity, 0, 1); form.add(job, 1, 1);



        account.setCenter(form);
        Button submit = new Button("Search");
        submit.setFont((Font.font("Calibri Light", FontWeight.THIN, 15)));

        errorLabel = new Label("");


        VBox stackBox = new VBox();
        stackBox.setSpacing(5);
        stackBox.setPadding(new Insets(50, 10, 10, 10));

        stackBox.getChildren().addAll(errorLabel, submit);


        account.setBottom(stackBox);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int jobInputInt;
                String mnameInputString;

                String mnameInput = (String) mname.getValue();
                String jobInput = (String) job.getValue();


                int errorCheck = 0;
                int errorCheckTotal = 2;

                if(mnameInput == null){
                    errorCheck++;
                    mnameInputString = "";
                }
                else{
                    mnameInputString = (String) mname.getValue();
                }
                if(jobInput == null){
                    errorCheck++;
                    jobInputInt = 0;
                }
                else{
                    jobInputInt = Integer.parseInt((String) job.getValue());
                }

                if(errorCheck == errorCheckTotal){
                    didNotSearchForAnything = true;
                    setErrorLabel(errorLabel);
                    return;
                } else {
                    didNotSearchForAnything = false;
                    setErrorLabel(errorLabel);
                    result = db.searchAdvanceManager(connection, mnameInputString, jobInputInt);
                }

                setNumberOfRows(result);
                try {


                    ArrayList<manager> m = new ArrayList<>();
                    int rows = 0;
                    while (result.next()) {
                        ++rows;
                    }
                    if (rows == 0) {
                        //handle this case later
                        System.out.println("No records found in the advan");
                    }
                    result.first();
                    didNotSearchForAnything = false;
                    setErrorLabel(errorLabel);
                    for (int i = 0; i < rows + 1; i++) {
                        m.add(new manager(result.getString("Name"), result.getInt("JobSecurity")));
                        result.next();
                    }

                    searchResultPage search = new searchResultPage();
                    search.createBorderPane(setManagerFields(m));
                } catch (SQLException e) {
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(e);
                } catch (NullPointerException n){
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(n);

                }
            }});




        ScrollPane rootScroll = new ScrollPane();
        rootScroll.setPadding(new Insets(5, 5, 10, 5));
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setContent(account);




        //Body
        return rootScroll;
    }













    public ScrollPane handleLeagueSearch(){
        noElementExists = false;
        didNotSearchForAnything = false;
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("League Search Criteria");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        account.setTop(title);



        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        Label leagueName = new Label("Name: ");
        form.setHalignment(leagueName, HPos.RIGHT);
        ObservableList<String> lName = FXCollections.observableArrayList();
        ArrayList<league> leaguesInDB = db.getAllLeagues(connection);
        for(int a = 0; a < leaguesInDB.size(); a++){
            lName.add(leaguesInDB.get(a).getName());
        }
        final ComboBox l = new ComboBox(lName);

        Label leagueCountry = new Label("Country: ");
        form.setHalignment(leagueCountry, HPos.RIGHT);
        TextField leagueC = new TextField();
        leagueC.setEditable(true);

        Label leagueSpon = new Label("Sponsor: ");
        form.setHalignment(leagueSpon, HPos.RIGHT);
        ObservableList<String> lSponsorName = FXCollections.observableArrayList();
        ArrayList<league> sponsorsInDB = db.getAllLeagues(connection);
        for(int a = 0; a < sponsorsInDB.size(); a++){
            lSponsorName.add(sponsorsInDB.get(a).getSponsor());
        }
        final ComboBox spon = new ComboBox(lSponsorName);


        Label noOfTeams = new Label("Number of Teams: ");
        form.setHalignment(noOfTeams, HPos.RIGHT);
        ObservableList<String> t = FXCollections.observableArrayList();
        for(int i = 10; i < 21; i++) {
            t.add(Integer.toString(i));
        }
        final ComboBox teamNo = new ComboBox(t);

        form.add(leagueName, 0, 0); form.add(l, 1, 0);
        form.add(leagueCountry, 0, 1); form.add(leagueC, 1, 1);
        form.add(leagueSpon, 0, 2); form.add(spon, 1, 2);
        form.add(noOfTeams, 0, 3); form.add(teamNo, 1, 3);


        account.setCenter(form);

        Button submit = new Button("Search");
        submit.setFont((Font.font("Calibri Light", FontWeight.THIN, 15)));

        errorLabel = new Label("");


        VBox stackBox = new VBox();
        stackBox.setSpacing(5);
        stackBox.setPadding(new Insets(50, 10, 10, 10));

        stackBox.getChildren().addAll(errorLabel, submit);


        account.setBottom(stackBox);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int numberOfTeamsInputInt;
                String leagueNameInput = (String) (l.getValue());
                String countryInput = leagueC.getText();
                String sponsorInput = (String) (spon.getValue());
                String numberOfTeamsInput = (String) teamNo.getValue();


                int errorCheck = 0;
                int errorCheckTotal = 4;

                boolean input1 = false;

                if(leagueNameInput == null){
                    errorCheck++;
                    leagueNameInput = "";
                }
                else{
                    leagueNameInput = (String) l.getValue();
                }
                if(countryInput.isEmpty()){
                    errorCheck++;
                    countryInput = "";
                }
                else{
                    input1 = false;
                    countryInput = leagueC.getText();
                    if(nameChangeIsValid(countryInput)){
                        integerTypeFail = false;
                    } else {
                        input1 = true;
                        leagueC.setStyle(style);
                        integerTypeFail = true;
                        setErrorLabelForTypeChecking(errorLabel);
                    }
                }
                if(sponsorInput == null){
                    errorCheck++;
                    sponsorInput = "";
                }
                else{
                    sponsorInput = (String) spon.getValue();
                }
                if(numberOfTeamsInput == null){
                    errorCheck++;
                    numberOfTeamsInputInt = 0;
                }
                else{
                    numberOfTeamsInputInt = Integer.parseInt((String) teamNo.getValue());
                }


                if(integerTypeFail){
                    if(!input1)
                        leagueC.setStyle("");
                    return;
                } else {
                    integerTypeFail = false;
                    if(!input1)
                        leagueC.setStyle("");
                }

                if(errorCheck == errorCheckTotal){

                    didNotSearchForAnything = true;
                    setErrorLabel(errorLabel);
                    return;
                } else {
                    didNotSearchForAnything = false;
                    setErrorLabel(errorLabel);
                    result = db.searchAdvanceLeague(connection, numberOfTeamsInputInt, countryInput, sponsorInput, leagueNameInput);
                }
                setNumberOfRows(result);
                try {
                    ArrayList<league> l = new ArrayList<>();
                    int rows = 0;
                    while (result.next()) {
                        ++rows;
                    }
                    if (rows == 0) {
                        //handle this case later
                        System.out.println("No records found in the advan");
                    }
                    result.first();
                    for (int i = 0; i < rows + 1; i++) {
                        l.add(new league(result.getInt("Teams"), result.getString("Country"), result.getString("Sponsor"), result.getString("Name")));
                        result.next();
                    }
                    searchResultPage search = new searchResultPage();
                    search.createBorderPane(setLeagueFields(l));
                } catch (SQLException e) {
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(e);
                } catch (NullPointerException n){
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(n);

                }
            }});




        ScrollPane rootScroll = new ScrollPane();
        rootScroll.setPadding(new Insets(5, 5, 10, 5));
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setContent(account);




        //Body
        return rootScroll;
    }
    public ScrollPane handleContractSearch(){
        noElementExists = false;
        didNotSearchForAnything = false;
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("Contract Search Criteria");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        account.setTop(title);



        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);


        Label lenRemain = new Label("Length Remaining (Yrs): ");
        form.setHalignment(lenRemain, HPos.RIGHT);
        ObservableList<String> len = FXCollections.observableArrayList();
        for(int z = 0; z < 6; z++){
            len.add(Integer.toString(z));
        }
        final ComboBox remain = new ComboBox(len);

        Label availability = new Label("Loan Option: ");
        form.setHalignment(availability, HPos.RIGHT);
        ObservableList<String> a =
                FXCollections.observableArrayList(
                        "Yes", "No"
                );
        final ComboBox avail = new ComboBox(a);


        Label dur = new Label("Total Contract Length (Yrs): ");
        form.setHalignment(dur, HPos.RIGHT);
        ObservableList<String> duration = FXCollections.observableArrayList();
        for(int j = 0; j < 6; j++){
            duration.add(Integer.toString(j));
        }
        final ComboBox d = new ComboBox(duration);




        form.add(lenRemain, 0, 0); form.add(remain, 1, 0);
        form.add(availability, 0, 1); form.add(avail, 1, 1);
        form.add(dur, 0, 2); form.add(d, 1, 2);


        account.setCenter(form);


        Button submit = new Button("Search");
        submit.setFont((Font.font("Calibri Light", FontWeight.THIN, 15)));

        errorLabel = new Label("");


        VBox stackBox = new VBox();
        stackBox.setSpacing(5);
        stackBox.setPadding(new Insets(50, 10, 10, 10));

        stackBox.getChildren().addAll(errorLabel, submit);


        account.setBottom(stackBox);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String identifier = "contract";

                int lengthInputInt;
                int durationInputInt;
                int loanOptionInputInt = 0;
                int squadNumberInputInt = 0;

                String lengthInput = (String) remain.getValue();
                String loanOptionInput = (String) avail.getValue();
                String durationInput = (String) d.getValue();


                int errorCheck = 0;
                int errorCheckTotal = 3;


                if(lengthInput == null){
                    errorCheck++;
                    lengthInputInt = 0;
                }
                else{
                    lengthInputInt = Integer.parseInt(lengthInput);
                }

                if(loanOptionInput == null){
                    errorCheck++;
                }
                if(loanOptionInput == "Yes"){
                    loanOptionInputInt = 1;

                }
                if(durationInput == null){
                    errorCheck++;
                    durationInputInt = 0;
                }
                else{
                    durationInputInt = Integer.parseInt(durationInput);
                }

                if(errorCheck == errorCheckTotal){
                    didNotSearchForAnything = true;
                    setErrorLabel(errorLabel);
                    return;
                } else {
                    didNotSearchForAnything = false;
                    setErrorLabel(errorLabel);
                    result = db.searchAdvanceContract(connection, lengthInputInt, durationInputInt, loanOptionInputInt);
                }

                setNumberOfRows(result);
                try {
                    ArrayList<contract> c = new ArrayList<>();
                    int rows = 0;
                    while (result.next()) {
                        ++rows;
                    }
                    if (rows == 0) {
                        //handle this case later
                        System.out.println("No records found in the advan");
                    }
                    result.first();
                    noElementExists = false;
                    setErrorLabel(errorLabel);
                    for (int i = 0; i < rows + 1; i++) {
                        boolean truth;
                        if(result.getInt("LoanOption") == 1) {
                            truth = true;
                        } else {
                            truth = false;
                        }
                        c.add(new contract(result.getInt("LengthRemaining"), result.getInt("Duration"), truth));
                        result.next();
                    }

                    searchResultPage search = new searchResultPage();
                    search.createBorderPane(setContractFields(c));
                } catch (SQLException e) {
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(e);

                } catch (NullPointerException n){
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(n);

                }
            }});



        ScrollPane rootScroll = new ScrollPane();
        rootScroll.setPadding(new Insets(5, 5, 10, 5));
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setContent(account);




        //Body
        return rootScroll;
    }
    public ScrollPane handleTeamSearch(){
        noElementExists = false;
        didNotSearchForAnything = false;
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("Team Search Criteria");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        account.setTop(title);



        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);


        Label teamName = new Label("Name: ");
        form.setHalignment(teamName, HPos.RIGHT);
        ObservableList<String> tName = FXCollections.observableArrayList();
        ArrayList<team> teamsInDB = db.getAllTeams(connection);
        for(int z = 0; z < teamsInDB.size(); z++){
            tName.add(teamsInDB.get(z).getName());
        }
        final ComboBox t = new ComboBox(tName);

        Label teamSlogan = new Label("TM Slogan: ");
        form.setHalignment(teamSlogan, HPos.RIGHT);
        ObservableList<String> sName = FXCollections.observableArrayList();
        ArrayList<team> slogansInDB = db.getAllTeams(connection);
        for(int a = 0; a < slogansInDB.size(); a++){
            sName.add(slogansInDB.get(a).getTMSlogan());
        }
        final ComboBox s = new ComboBox(sName);




        form.add(teamName, 0, 0); form.add(t, 1, 0);
        form.add(teamSlogan, 0, 1); form.add(s, 1, 1);


        account.setCenter(form);

        Button submit = new Button("Search");
        submit.setFont((Font.font("Calibri Light", FontWeight.THIN, 15)));

        errorLabel = new Label("");


        VBox stackBox = new VBox();
        stackBox.setSpacing(5);
        stackBox.setPadding(new Insets(50, 10, 10, 10));

        stackBox.getChildren().addAll(errorLabel, submit);


        account.setBottom(stackBox);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String identifier = "team";


                String nameInput = (String) (t.getValue());
                String tmSlogan = (String) (s.getValue());


                int errorCheck = 0;
                int errorCheckTotal = 2;


                if(nameInput == null){
                    errorCheck++;
                    nameInput = "";
                }
                else{
                    nameInput = (String) (t.getValue());
                }
                if(tmSlogan == null){
                    errorCheck++;
                    tmSlogan = "";
                }
                else{
                    tmSlogan = (String) (s.getValue());
                }



                if(errorCheck == errorCheckTotal){
                    didNotSearchForAnything = true;
                    setErrorLabel(errorLabel);
                    return;
                } else {
                    didNotSearchForAnything = false;
                    setErrorLabel(errorLabel);
                    result = db.searchAdvanceTeam(connection, tmSlogan, nameInput);
                }

                setNumberOfRows(result);
                try {
                    ArrayList<team> t = new ArrayList<>();
                    int rows = 0;
                    while (result.next()) {
                        ++rows;
                    }
                    if (rows == 0) {
                        //handle this case later
                        System.out.println("No records found in the advan");
                    }
                    result.first();
                    didNotSearchForAnything = false;
                    setErrorLabel(errorLabel);
                    for (int i = 0; i < rows + 1; i++) {
                        t.add(new team(result.getString("Name"), result.getString("TMSlogan")));
                        result.next();
                    }

                    searchResultPage search = new searchResultPage();
                    search.createBorderPane(setTeamFields(t));
                } catch (SQLException e) {
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(e);
                } catch (NullPointerException n){
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(n);

                }
            }});


        ScrollPane rootScroll = new ScrollPane();
        rootScroll.setPadding(new Insets(5, 5, 10, 5));
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setContent(account);




        //Body
        return rootScroll;
    }



    public ScrollPane handleGBodySearch(){
        noElementExists = false;
        didNotSearchForAnything = false;
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("Governing Body Search Criteria");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        account.setTop(title);



        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);


        Label gName = new Label("Name: ");
        form.setHalignment(gName, HPos.RIGHT);
        ObservableList<String> govName = FXCollections.observableArrayList();
        ArrayList<GBody> govsInDB = db.getAllGBodies(connection);
        for(int z = 0; z < govsInDB.size(); z++){
            govName.add(govsInDB.get(z).getName());
        }
        final ComboBox g = new ComboBox(govName);

        Label govHQ = new Label("HQ: ");
        form.setHalignment(govHQ, HPos.RIGHT);
        ObservableList<String> HQName = FXCollections.observableArrayList();
        ArrayList<GBody> HQInDB = db.getAllGBodies(connection);
        for(int a = 0; a < HQInDB.size(); a++){
            HQName.add(HQInDB.get(a).getHQ());
        }
        final ComboBox HQ = new ComboBox(HQName);



        Label govPres = new Label("President: ");
        form.setHalignment(govPres, HPos.RIGHT);
        ObservableList<String> PresName = FXCollections.observableArrayList();
        ArrayList<GBody> PresInDB = db.getAllGBodies(connection);
        for(int p = 0; p < PresInDB.size(); p++){
            PresName.add(PresInDB.get(p).getPresident());
        }
        final ComboBox P = new ComboBox(PresName);


        form.add(gName, 0, 0); form.add(g, 1, 0);
        form.add(govHQ, 0, 1); form.add(HQ, 1, 1);
        form.add(govPres, 0, 2); form.add(P, 1, 2);


        account.setCenter(form);

        Button submit = new Button("Search");
        submit.setFont((Font.font("Calibri Light", FontWeight.THIN, 15)));

        errorLabel = new Label("");


        VBox stackBox = new VBox();
        stackBox.setSpacing(5);
        stackBox.setPadding(new Insets(50, 10, 10, 10));

        stackBox.getChildren().addAll(errorLabel, submit);


        account.setBottom(stackBox);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String identifier = "governingbody";

                String nameInput = (String) g.getValue();
                String hqInput = (String) HQ.getValue();
                String presidentInput = (String) P.getValue();



                int errorCheck = 0;
                int errorCheckTotal = 3;

                if (nameInput == null) {
                    errorCheck++;
                    nameInput = "";
                } else {
                    nameInput = (String) (g.getValue());
                }
                if (hqInput == null) {
                    errorCheck++;
                    hqInput = "";
                } else {
                    hqInput = (String) (HQ.getValue());
                }
                if (presidentInput == null) {
                    errorCheck++;
                    presidentInput = "";
                } else {
                    presidentInput = (String) (P.getValue());
                }


                if(errorCheck == errorCheckTotal){
                    didNotSearchForAnything = true;
                    setErrorLabel(errorLabel);
                    return;
                } else {
                    didNotSearchForAnything = false;
                    setErrorLabel(errorLabel);
                    result = db.searchAdvanceGBody(connection, nameInput, presidentInput, hqInput);
                }
                setNumberOfRows(result);
                try {

                    ArrayList<GBody> gb = new ArrayList<>();
                    int rows = 0;
                    while (result.next()) {
                        ++rows;
                    }
                    if (rows == 0) {
                        //handle this case later
                        System.out.println("No records found in the advan");
                    }
                    result.first();
                    didNotSearchForAnything = false;
                    setErrorLabel(errorLabel);
                    for (int i = 0; i < rows + 1; i++) {
                        gb.add(new GBody(result.getString("Name"), result.getString("President"), result.getString("HQ")));
                        result.next();
                    }

                    searchResultPage search = new searchResultPage();
                    search.createBorderPane(setGBodyFields(gb));
                } catch (SQLException e) {
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(e);
                } catch (NullPointerException n){
                    noElementExists = true;
                    setErrorLabel(errorLabel);
                    System.out.println(n);

                }
            }});



        ScrollPane rootScroll = new ScrollPane();
        rootScroll.setPadding(new Insets(5, 5, 10, 5));
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setContent(account);




        //Body
        return rootScroll;
    }


    public BorderPane setTeamFields(ArrayList<team> t){
        BorderPane resultView = new BorderPane();
        resultView.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("Search Results");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        resultView.setTop(title);

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        TableView<team> table = new TableView<team>();
        TableColumn name = new TableColumn("Name");
        TableColumn tmSlogan = new TableColumn("TM Slogan");



        table.getColumns().addAll(name, tmSlogan);

        final ObservableList<team> data = FXCollections.observableArrayList(
                t
        );

        name.setCellValueFactory(
                new PropertyValueFactory<team,String>("Name")
        );
        tmSlogan.setCellValueFactory(
                new PropertyValueFactory<team,Integer>("TMSlogan"));



        name.prefWidthProperty().bind(table.widthProperty().divide(2));
        tmSlogan.prefWidthProperty().bind(table.widthProperty().divide(2));



        table.setItems(data);
        resultView.setCenter(table);


        return resultView;
    }



    public BorderPane setManagerFields(ArrayList<manager> m){
        BorderPane resultView = new BorderPane();
        resultView.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("Search Results");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        resultView.setTop(title);

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        TableView<manager> table = new TableView<manager>();
        TableColumn name = new TableColumn("Name");
        TableColumn jobSecurity = new TableColumn("Job Security");



        table.getColumns().addAll(name, jobSecurity);

        final ObservableList<manager> data = FXCollections.observableArrayList(
                m
        );

        name.setCellValueFactory(
                new PropertyValueFactory<manager,String>("Name")
        );
        jobSecurity.setCellValueFactory(
                new PropertyValueFactory<manager,Integer>("JobSecurity"));



        name.prefWidthProperty().bind(table.widthProperty().divide(2));
        jobSecurity.prefWidthProperty().bind(table.widthProperty().divide(2));



        table.setItems(data);
        resultView.setCenter(table);


        return resultView;
    }


    public BorderPane setContractFields(ArrayList<contract> c){
        BorderPane resultView = new BorderPane();
        resultView.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("Search Results");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        resultView.setTop(title);

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        TableView<contract> table = new TableView<contract>();
        TableColumn lenRemain = new TableColumn("Length Remaining (Yrs)");
        TableColumn loanOption = new TableColumn("Loan Option");
        TableColumn duration = new TableColumn("Duration (Yrs)");



        table.getColumns().addAll(lenRemain, loanOption, duration);

        final ObservableList<contract> data = FXCollections.observableArrayList(
                c
        );

        lenRemain.setCellValueFactory(
                new PropertyValueFactory<contract,String>("lenRemain")
        );
        loanOption.setCellValueFactory(
                new PropertyValueFactory<contract,Integer>("loanOption")
        );
        duration.setCellValueFactory(
                new PropertyValueFactory<contract,Integer>("duration")
        );

        lenRemain.prefWidthProperty().bind(table.widthProperty().divide(3));
        loanOption.prefWidthProperty().bind(table.widthProperty().divide(3));
        duration.prefWidthProperty().bind(table.widthProperty().divide(3));

        table.setItems(data);
        resultView.setCenter(table);


        return resultView;
    }
    public BorderPane setLeagueFields(ArrayList<league> l){
        BorderPane resultView = new BorderPane();
        resultView.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("Search Results");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        resultView.setTop(title);

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        TableView<league> table = new TableView<league>();
        TableColumn name = new TableColumn("Name");
        TableColumn country = new TableColumn("Country");
        TableColumn sponsor = new TableColumn("Sponsor");
        TableColumn noOfTeams = new TableColumn("Number of Teams");

        table.getColumns().addAll(name, country, sponsor, noOfTeams);

        final ObservableList<league> data = FXCollections.observableArrayList(
                l
        );

        name.setCellValueFactory(
                new PropertyValueFactory<league,String>("name")
        );
        country.setCellValueFactory(
                new PropertyValueFactory<league,Integer>("country")
        );
        sponsor.setCellValueFactory(
                new PropertyValueFactory<league,Integer>("sponsor")
        );
        noOfTeams.setCellValueFactory(
                new PropertyValueFactory<league,String>("numberOfTeams")
        );



        name.prefWidthProperty().bind(table.widthProperty().divide(4));
        country.prefWidthProperty().bind(table.widthProperty().divide(4));
        sponsor.prefWidthProperty().bind(table.widthProperty().divide(4));
        noOfTeams.prefWidthProperty().bind(table.widthProperty().divide(4));



        table.setItems(data);
        resultView.setCenter(table);


//        CheckBox keepResultOpen = new CheckBox();
//
//        keepResultOpen.setText("Keep Result Window Open?");
//        keepResultOpen.setSelected(true);
//
//
//        keepResultOpen.setStyle(
//                "-fx-border-color: maroon; "
//                        + "-fx-font-size: 15;"
//                        + "-fx-border-insets: 5, 0, 5, 5; "
//                        + "-fx-border-radius: 5;"
//                        + "-fx-border-style: dashed;"
//                        + "-fx-border-width: 5;"
//        );
//
//
//
//        resultView.setBottom(keepResultOpen);
//
        return resultView;
    }


    public BorderPane setPlayerFields(ArrayList<player> p){
        BorderPane resultView = new BorderPane();
        resultView.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("Search Results");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        resultView.setTop(title);

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        TableView<player> table = new TableView<player>();
        TableColumn name = new TableColumn("Name");
        TableColumn squadNumber = new TableColumn("Squad Number");
        TableColumn age = new TableColumn("Age");
        TableColumn nationality = new TableColumn("Nationality");
        TableColumn position = new TableColumn("Position");
        TableColumn price = new TableColumn("Price");
        TableColumn salary = new TableColumn("Salary");
        TableColumn availability = new TableColumn("Availability");
        TableColumn rating = new TableColumn("Rating");


        table.getColumns().addAll(name, squadNumber, age, nationality, position,
                price, salary, availability, rating);

        final ObservableList<player> data = FXCollections.observableArrayList(
                p
        );

        name.setCellValueFactory(
                new PropertyValueFactory<player,String>("Name")
        );
        squadNumber.setCellValueFactory(
                new PropertyValueFactory<player,Integer>("SquadNumber")
        );
        age.setCellValueFactory(
                new PropertyValueFactory<player,Integer>("Age")
        );
        nationality.setCellValueFactory(
                new PropertyValueFactory<player,String>("Nationality")
        );
        position.setCellValueFactory(
                new PropertyValueFactory<player,String>("Position")
        );
        price.setCellValueFactory(
                new PropertyValueFactory<player,Integer>("Price")
        );

        availability.setCellValueFactory(
                new PropertyValueFactory<player,String>("Availability")
        );
        salary.setCellValueFactory(
                new PropertyValueFactory<player,Integer>("Salary")
        );
        rating.setCellValueFactory(
                new PropertyValueFactory<player,Integer>("Rating")
        );


        name.prefWidthProperty().bind(table.widthProperty().divide(9)); // w * 1/4
        squadNumber.prefWidthProperty().bind(table.widthProperty().divide(9)); // w * 2/4
        age.prefWidthProperty().bind(table.widthProperty().divide(9)); // w * 1/4
        nationality.prefWidthProperty().bind(table.widthProperty().divide(9)); // w * 1/4
        position.prefWidthProperty().bind(table.widthProperty().divide(9)); // w * 2/4
        price.prefWidthProperty().bind(table.widthProperty().divide(9)); // w * 1/4
        availability.prefWidthProperty().bind(table.widthProperty().divide(9)); // w * 1/4
        salary.prefWidthProperty().bind(table.widthProperty().divide(9)); // w * 2/4
        rating.prefWidthProperty().bind(table.widthProperty().divide(9)); // w * 1/4


        table.setItems(data);
        resultView.setCenter(table);

        return resultView;
    }
    public BorderPane setGBodyFields(ArrayList<GBody> gb){
        BorderPane resultView = new BorderPane();
        resultView.setPadding(new Insets(10, 5, 5, 5));

        //Title
        TextField title = new TextField("Search Results");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        resultView.setTop(title);

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        TableView<GBody> table = new TableView<GBody>();
        TableColumn name = new TableColumn("Name");
        TableColumn president = new TableColumn("President");
        TableColumn hq = new TableColumn("HQ");



        table.getColumns().addAll(name, president, hq);

        final ObservableList<GBody> data = FXCollections.observableArrayList(
                gb
        );

        name.setCellValueFactory(
                new PropertyValueFactory<GBody,String>("Name")
        );
        president.setCellValueFactory(
                new PropertyValueFactory<GBody,Integer>("President")
        );
        hq.setCellValueFactory(
                new PropertyValueFactory<GBody,Integer>("HQ")
        );

        name.prefWidthProperty().bind(table.widthProperty().divide(3));
        president.prefWidthProperty().bind(table.widthProperty().divide(3));
        hq.prefWidthProperty().bind(table.widthProperty().divide(3));


        table.setItems(data);
        resultView.setCenter(table);




        return resultView;
    }































    public int getNumberOfRows(){
        return numberOfResults;
    }


    public void setNumberOfRows(ResultSet result){
        try {
            noElementExists = false;
            result.last();
            numberOfResults = result.getRow();
            if(numberOfResults == 0){
                noElementExists = true;
            } else {
                noElementExists = false;
            }
            result.first();
        } catch (SQLException e) {
            noElementExists = true;
        } catch (NullPointerException n){

        }
    }


    public Label getErrorLabel() {
        return errorLabel;
    }

    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
        errorLabel.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        errorLabel.setTextFill(javafx.scene.paint.Color.web("red"));
        if(noElementExists && !didNotSearchForAnything){
            errorLabel.setText("Value doesn't exist, please search again!");
            return;
        } else {
            errorLabel.setText("");
        }
        if(didNotSearchForAnything && !noElementExists){
            errorLabel.setText("Search empty, please search again!");
            return;
        } else {
            errorLabel.setText("");
        }

    }


    public void setErrorLabelForTypeChecking(Label errorLabel){
        this.errorLabel = errorLabel;
        errorLabel.setFont(Font.font("Calibri Light", FontWeight.BOLD, 15));
        errorLabel.setTextFill(javafx.scene.paint.Color.web("red"));

        if(integerTypeFail){
            errorLabel.setText("Please correct highlighted value(s)");
        } else {
            errorLabel.setText("");
        }

    }


    public boolean nameChangeIsValid(String input){
        try {
            Integer.parseInt(input);
            return false;
        } catch (NumberFormatException e){
            return true;
        }
    }







    public contract getContr() {
        return contr;
    }

    public void setContr(contract contr) {
        this.contr = contr;
    }

    public player getPlyr() {
        return plyr;
    }

    public void setPlyr(player plyr) {
        this.plyr = plyr;
    }

    public GBody getGovt() {
        return govt;
    }

    public void setGovt(GBody govt) {
        this.govt = govt;
    }

    public Objects.league getLeague() {
        return league;
    }

    public void setLeague(league league) {
        this.league = league;
    }

    public manager getMan() {
        return man;
    }

    public void setMan(manager man) {
        this.man = man;
    }

    public Objects.team getTeam() {
        return team;
    }

    public void setTeam(team team) {
        this.team = team;
    }
}
