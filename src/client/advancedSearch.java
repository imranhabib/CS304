package client;

import Objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by Imran on 2016-03-27.
 */
public class advancedSearch {


    private contract contr;
    private player plyr;
    private GBody govt;
    private league league;
    private manager man;
    private team team;
    public BorderPane root;


    public advancedSearch(){

    }

    public advancedSearch(contract contr){
        this.contr = contr;

    }

    public advancedSearch(player plyr){
        this.plyr = plyr;

    }
    public advancedSearch(GBody govt){
        this.govt = govt;

    }
    public advancedSearch(league league){
        this.league = league;
    }
    public advancedSearch(manager man){
        this.man = man;
    }
    public advancedSearch(team team){
        this.team = team;
    }



    public BorderPane createAdvanShell(){
        BorderPane shell = new BorderPane();
        shell.setPadding(new Insets(20, 10, 0, 10));

        BorderPane titlePane = new BorderPane();
        titlePane.setPadding(new Insets(20, 10, 0, 10));
        //Title
        TextField title = new TextField("Advanced Search");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        titlePane.setTop(title);
        shell.setCenter(titlePane);
        //Body

        GridPane form = new GridPane();
        form.setPadding(new Insets(20, 0, 20, 20));
        form.setHgap(7);
        form.setVgap(7);

        Label searchBy = new Label("Search By: ");
        form.setHalignment(searchBy, HPos.RIGHT);

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


        form.add(searchBy, 0, 0); form.add(entities, 1, 0);

        shell.setRight(form);

        return shell;

    }


    public BorderPane handlePlayerSearch(){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("Advanced Player Search");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        account.setTop(title);
        //Body
        return account;
    }
    public BorderPane handleManagerSearch(){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("Advanced Manager Search");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        account.setTop(title);
        //Body
        return account;
    }
    public BorderPane handleLeagueSearch(){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("Advanced League Search");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        account.setTop(title);
        //Body
        return account;
    }
    public BorderPane handleContractSearch(){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("Advanced Contract Search");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        account.setTop(title);
        //Body
        return account;
    }
    public BorderPane handleTeamSearch(){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("Advanced Team Search");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        account.setTop(title);
        //Body
        return account;
    }
    public BorderPane handleGBodySearch(){
        BorderPane account = new BorderPane();
        account.setPadding(new Insets(20, 10, 0, 10));

        //Title
        TextField title = new TextField("Advanced Government Body Search");
        title.setEditable(false);
        title.setFont(Font.font("Calibri Light", FontWeight.BOLD, 25));

        account.setTop(title);
        //Body
        return account;
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
