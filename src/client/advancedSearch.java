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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

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

        Label playerName = new Label("First Name: ");
        form.setHalignment(playerName, HPos.RIGHT);
        TextField name = new TextField();
        name.setEditable(true);

        Label playerLastName = new Label("Last Name: ");
        form.setHalignment(playerLastName, HPos.RIGHT);
        TextField lname = new TextField();
        lname.setEditable(true);


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

        form.add(playerPos, 0, 0); form.add(pos, 1, 0);
        form.add(playerPrice, 0, 1); form.add(playerP, 1, 1);
        form.add(playerName, 0, 2); form.add(name, 1, 2);
        form.add(playerLastName, 0, 3); form.add(lname, 1, 3);
        form.add(playerAge, 0, 4); form.add(plyAge, 1, 4);
        form.add(playerSal, 0, 5); form.add(sal, 1, 5);
        form.add(playerNationality, 0, 6); form.add(nation, 1, 6);
        form.add(playerSquadNo, 0, 7); form.add(sqNo, 1, 7);
        form.add(userRating, 0, 8); form.add(plyRate, 1, 8);
        form.add(availability, 0, 9); form.add(avail, 1, 9);


        account.setCenter(form);

        Button submit = new Button("Search");
        submit.setFont((Font.font("Calibri Light", FontWeight.THIN, 15)));

        account.setBottom(submit);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int availablePlayer;
                if ((avail.getValue()).equals("Yes")) {
                    availablePlayer = 1;
                } else {
                    availablePlayer = 0;
                }
                result = db.searchAdvancePlayer(connection, "Forward", Integer.parseInt(playerP.getText()), name.getText(), Integer.parseInt((String) plyAge.getValue()),
                        Integer.parseInt(sal.getText()), nation.getText(), Integer.parseInt(sqNo.getText()),
                        availablePlayer, Integer.parseInt((String) plyRate.getValue()));

                boolean a;
                try {
                    if (result.getInt("Availability") == 1) {
                        a = true;
                    } else {
                        a = false;
                    }

                    player p = new player(result.getString("Position"), result.getInt("Price"), result.getString("Name"),
                            result.getInt("Age"), result.getInt("Salary"), result.getString("Nationality"),
                            result.getInt("SquadNumber"), a, result.getInt("Rating"));


                    searchResultPage search = new searchResultPage();
                    search.createBorderPane(setPlayerFields(p));



                } catch (SQLException e) {

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
    public ScrollPane handleManagerSearch(){
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

        account.setBottom(submit);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String identifier = "manager";



            }
        });


        ScrollPane rootScroll = new ScrollPane();
        rootScroll.setPadding(new Insets(5, 5, 10, 5));
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setContent(account);




        //Body
        return rootScroll;
    }













    public ScrollPane handleLeagueSearch(){
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

        account.setBottom(submit);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String identifier = "league";

            }
        });



        ScrollPane rootScroll = new ScrollPane();
        rootScroll.setPadding(new Insets(5, 5, 10, 5));
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setContent(account);




        //Body
        return rootScroll;
    }
    public ScrollPane handleContractSearch(){
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


        Label lenRemain = new Label("Length Remaining(Yrs): ");
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


        Label dur = new Label("Length Remaining(Yrs): ");
        form.setHalignment(dur, HPos.RIGHT);
        ObservableList<String> duration = FXCollections.observableArrayList();
        for(int j = 0; j < 6; j++){
            len.add(Integer.toString(j));
        }
        final ComboBox d = new ComboBox(duration);




        form.add(lenRemain, 0, 0); form.add(remain, 1, 0);
        form.add(availability, 0, 1); form.add(avail, 1, 1);
        form.add(dur, 0, 2); form.add(d, 1, 2);


        account.setCenter(form);


        Button submit = new Button("Search");
        submit.setFont((Font.font("Calibri Light", FontWeight.THIN, 15)));

        account.setBottom(submit);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String identifier = "contract";

            }
        });


        ScrollPane rootScroll = new ScrollPane();
        rootScroll.setPadding(new Insets(5, 5, 10, 5));
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setContent(account);




        //Body
        return rootScroll;
    }
    public ScrollPane handleTeamSearch(){
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
            tName.add(slogansInDB.get(a).getTMSlogan());
        }
        final ComboBox s = new ComboBox(sName);




        form.add(teamName, 0, 0); form.add(t, 1, 0);
        form.add(teamSlogan, 0, 1); form.add(s, 1, 1);


        account.setCenter(form);

        Button submit = new Button("Search");
        submit.setFont((Font.font("Calibri Light", FontWeight.THIN, 15)));

        account.setBottom(submit);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String identifier = "team";


            }
        });


        ScrollPane rootScroll = new ScrollPane();
        rootScroll.setPadding(new Insets(5, 5, 10, 5));
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setContent(account);




        //Body
        return rootScroll;
    }



    public ScrollPane handleGBodySearch(){
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

        account.setBottom(submit);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String identifier = "governingbody";

            }
        });


        ScrollPane rootScroll = new ScrollPane();
        rootScroll.setPadding(new Insets(5, 5, 10, 5));
        rootScroll.setFitToWidth(true);
        rootScroll.setFitToHeight(true);
        rootScroll.setContent(account);




        //Body
        return rootScroll;
    }


    public BorderPane setPlayerFields(player p){
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
                new PropertyValueFactory<player,Integer>("Squad Number")
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


//
//    public void setNumberOfRows(ResultSet result){
//        try {
//            result.last();
//            numberOfResults = result.getRow();
//            result.first();
//        } catch (SQLException e){
//        }
//    }
//

//    public BorderPane createPlayerPage(player p, int index){
//            BorderPane account = new BorderPane();
//            account.setPadding(new Insets(20, 10, 0, 9));
//
//            //Title
//            TextField title = new TextField("Search Result");
//            title.setEditable(false);
//            title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));
//
//            account.setTop(title);
//            //Body
//
//        GridPane form = new GridPane();
//        form.setPadding(new Insets(20, 0, 20, 20));
//        form.setHgap(7);
//        form.setVgap(7);
//
//
//        Label userName = new Label("Account Holder: ");
//        form.setHalignment(userName, HPos.RIGHT);
//        TextField name = new TextField(p.getName());
//        name.setEditable(false);
//
//        Label userSqNum = new Label("Squad Number: ");
//        form.setHalignment(userSqNum, HPos.RIGHT);
//        TextField sqNum = new TextField(Integer.toString(p.getSquadNumber()));
//        sqNum.setEditable(false);
//
//        Label userAge = new Label("Age: ");
//        form.setHalignment(userAge, HPos.RIGHT);
//        TextField age = new TextField(Integer.toString(p.getAge()) + " Yrs");
//        age.setEditable(false);
//
//        Label userPos = new Label("Position: ");
//        form.setHalignment(userPos, HPos.RIGHT);
//        TextField pos = new TextField(p.getPosition());
//        pos.setEditable(false);
//
//        Label userPrice = new Label("Price: ");
//        form.setHalignment(userPrice, HPos.RIGHT);
//        TextField price = new TextField(Integer.toString(p.getPrice()) + " $");
//        price.setEditable(false);
//
//        Label userSalary = new Label("Salary: " );
//        form.setHalignment(userSalary, HPos.RIGHT);
//        TextField sal = new TextField(Integer.toString(p.getSalary()) + " $/Yr");
//        sal.setEditable(false);
//
//        Label userNationality = new Label("Nationality: ");
//        form.setHalignment(userNationality, HPos.RIGHT);
//        TextField nation = new TextField(p.getNationality());
//        nation.setEditable(false);
//
//
//        Label userAvailability = new Label("Available: ");
//        form.setHalignment(userAvailability, HPos.RIGHT);
//        TextField avail = new TextField();
//        if(p.isAvailability()){
//            avail.setText("Yes!");
//        } else {
//            avail.setText("No");
//        }
//
//
//        avail.setEditable(false);
//
//        Label userRating = new Label("Rating: ");
//        form.setHalignment(userRating, HPos.RIGHT);
//        TextField rate = new TextField(Integer.toString(p.getRating()) + " Pts");
//        rate.setEditable(false);
//
//
//        form.add(userName, 0, 0);
//            form.add(name, 1, 0);
//            form.add(userSqNum, 3, 0);
//            form.add(sqNum, 4, 0);
//            form.add(userAge, 0, 2);
//            form.add(age, 1, 2);
//            form.add(userPos, 3, 2);
//            form.add(pos, 4, 2);
//            form.add(userPrice, 0, 4);
//            form.add(price, 1, 4);
//            form.add(userSalary, 3, 4);
//            form.add(sal, 4, 4);
//            form.add(userNationality, 0, 6);
//            form.add(nation, 1, 6);
//            form.add(userAvailability, 3, 6);
//            form.add(avail, 4, 6);
//            form.add(userRating, 0, 8);
//            form.add(rate, 1, 8);
//            return account;
//
//    }
//
//















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
