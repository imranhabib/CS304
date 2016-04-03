package client;


import java.sql.*;
import java.util.ArrayList;


import Objects.*;

public class database {

    String dbName = "jdbc:mysql://localhost:3306?useSSL=false";
    Connection dbConnect;
    database connection;
    String username = "root";
    String password = "whatername";
    int userSqNum;
    int userSIN;


    public database(int sqNum) {
        this.userSqNum = sqNum;
        userSIN = sqNum;
    }


    public Connection createDBConnection() {
        try {
            System.out.println(dbName);
            Connection connect = DriverManager.getConnection(dbName, username, password);
            return connect;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("error in the DB Connection");
            return null;
        }
    }

    public contract selectUserContractInformationBySquadNumber(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(getUserContractInformationBySquadNumber(userSqNum));
            contract userContract = new contract(result);
            return userContract.formatContractDetails();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }


    public player selectAllAccountInformation(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(getUserAccountInformation(userSqNum));
            player user = new player(result);
            return user.formatPlayerDetails();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }

    public manager selectAllManagerAccountInformation(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(getManagerInformation(userSIN));
            manager user = new manager(result);
            return user.formatManagerDetails();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }



    public ArrayList<player> getAllPlayers(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(getAllPlayerInformation());
            result.first();
            ArrayList<player> players = new ArrayList<>();
            int rows = 0;
            while (result.next()) {
                ++rows;
            }
            if (rows == 0) {
                //handle this case later
                System.out.println("No records found");
            }
            result.first();
            for (int i = 0; i < rows + 1; i++) {
                boolean a;
                if (result.getInt("Availability") == 1) {
                    a = true;
                } else {
                    a = false;
                }
                players.add(new player(result.getString("Position"), result.getInt("Price"), result.getString("Name"),
                        result.getInt("Age"), result.getInt("Salary"), result.getString("Nationality"),
                        result.getInt("SquadNumber"), a, result.getInt("Rating"), result.getInt("TeamID")));
                result.next();
            }

            return players;

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }






    public ArrayList<league> getAllLeagues(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(getAllLeagueInformation());
            result.first();
            ArrayList<league> leagues = new ArrayList<league>();
            int rows = 0;
            while (result.next()) {
                ++rows;
            }
            if (rows == 0) {
                //handle this case later
                System.out.println("No records found");
            }
            result.first();
            for (int i = 0; i < rows + 1; i++) {
                leagues.add(new league(result.getInt("Teams"), result.getString("Country"), result.getString("Sponsor"),
                        result.getString("Name")));
                result.next();
            }

            return leagues;

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }


    public ArrayList<team> getAllTeams(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(getAllTeamInformation());
            result.first();
            ArrayList<team> teams = new ArrayList<team>();
            int rows = 0;
            while (result.next()) {
                ++rows;
            }
            if (rows == 0) {
                //handle this case later
                System.out.println("No records found in DB method");
            }
            result.first();
            for (int i = 0; i < rows + 1; i++) {
                teams.add(new team(result.getInt("TeamID"), result.getString("TMSlogan"), result.getInt("Budget"),
                        result.getString("Name")));
                result.next();
            }

            return teams;

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }


    public ArrayList<GBody> getAllGBodies(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(getAllGBodyInformation());
            result.first();
            ArrayList<GBody> gbodies = new ArrayList<GBody>();
            int rows = 0;
            while (result.next()) {
                ++rows;
            }
            if (rows == 0) {
                //handle this case later
                System.out.println("No records found in G Body");
            }
            result.first();
            for (int i = 0; i < rows + 1; i++) {
                gbodies.add(new GBody(result.getInt("Revenue"), result.getString("Name"), result.getString("President"),
                        result.getString("HQ")));
                result.next();
            }

            return gbodies;

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }


    public ArrayList<manager> getAllManagers(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(getAllManagerInformation());
            result.first();
            ArrayList<manager> managers = new ArrayList<manager>();
            int rows = 0;
            while (result.next()) {
                ++rows;
            }
            if (rows == 0) {
                //handle this case later
                System.out.println("No records found in Managers");
            }
            result.first();
            for (int i = 0; i < rows + 1; i++) {
                managers.add(new manager(result.getString("Name")));
                result.next();
            }

            return managers;

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }


    public boolean checkIfSquadNumberExists(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(checkIfSquadNumberIsValid(userSqNum));
            result.first();
            //Need print line below dont delete
            System.out.println(result.getInt("SquadNumber") + " debug line IGNORE");
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

    }


    public boolean checkIfManagerSINExists(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(checkIfManagerSINIsValid(userSIN));
            result.first();
            //Need print line below dont delete
            System.out.println(result.getString("SIN") + " debug line IGNORE");
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

    }

    public void changePlayerName(Connection connection, String name) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(updatePlayerName(name));
        } catch (SQLException e) {

        }
    }


    public void changePlayerAvailability(Connection connection, int availability) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(updatePlayerAvailability(availability));
        } catch (SQLException e) {

        }
    }

    public ResultSet searchAdvancePlayer(Connection connection, String position, int price, String name, int age, int salary, String nationality, int squadNumber, int availability, int rating){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(AdvancedSearchPlayer(position, price, name, age, salary, nationality, squadNumber, availability, rating));
            result.first();

            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet searchAdvanceContract(Connection connection, int lenRemain, int duration, int loanOption){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(AdvancedSearchContract(lenRemain, duration, loanOption));
            result.first();
            System.out.println(result.getString("squadNumber"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet searchAdvanceManager(Connection connection, String name, int JobSecurity){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(AdvancedSearchManager(name, JobSecurity));
            result.first();
            System.out.println(result.getString("name"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet searchAdvanceGBody(Connection connection, String name, String president, String HQ){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(AdvancedSearchGBody(name, president, HQ));
            result.first();
            System.out.println(result.getString("name"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet searchAdvanceLeague(Connection connection, int numberofTeams, String country, String sponsor, String name){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(AdvancedSearchLeague(numberofTeams, country, sponsor, name));
            result.first();
            System.out.println(result.getString("name"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet searchAdvanceTeam(Connection connection, String slogan, String name){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(AdvancedSearchTeam(slogan, name));
            result.first();
            System.out.println(result.getString("name"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet searchAdvancePlayerContractJoin(Connection connection, int lenRemain, int duration, int loanOption){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(AdvancedPlayerContractJoin(lenRemain, duration, loanOption));
            result.first();
            System.out.println(result.getString("Name"));
            System.out.println(result.getString("SquadNumber"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet searchAdvanceManagerTeamJoin(Connection connection, String managerName, int JobSecurity, int managerTeamId, int teamId, String TMSlogan, String teamName){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(AdvancedManagerTeamJoin(managerName, JobSecurity, managerTeamId, teamId, TMSlogan, teamName));
            result.first();
            System.out.println(result.getString("teamName"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet searchAdvancedBestPlayerPerTeam(Connection connection, int teamId, String criteria){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(AdvancedBestSomethingPerTeamAggregation(teamId, criteria));
            result.first();
            System.out.println(result.getString("teamId"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet searchHighestSomethingByLowestSomething(Connection connection, String highest, String lowest){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(somethingAndHigestRatedPlayer(highest,lowest));
            result.first();
            System.out.println(result.getString("Age"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet deleteContract(Connection connection, int contractNUmber){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(deleteContract(contractNUmber));
            result.first();
            System.out.println(result.getString("contractNUmber"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet deletePlayer(Connection connection, int SquadNumber){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(deletePlayerCascadeContract(SquadNumber));
            result.first();
            System.out.println(result.getString("SquadNumber"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public ResultSet selectAvailablePlayerNotInTeams(Connection connection, String teamId1, String teamId2){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(selectAvailablePlayersNotInTeams(teamId1, teamId2));
            result.first();
            System.out.println(result.getString("SquadNumber"));
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }


    /*
    SQL queries
     */


    // PLAYER QUERIES
    private String getUserAccountInformation(int squadNumber) {
        String stmt = new String("SELECT * FROM managementapplication.player WHERE SquadNumber=" + squadNumber);
        return stmt;
    }

    private String getUserContractInformationBySquadNumber(int squadNumber) {
        String stmt = new String("SELECT * FROM managementapplication.contract WHERE SquadNumber=" + squadNumber);
        return stmt;
    }


    private String getUserContractInformationByContractNumber(int contractNumber) {
        String stmt = new String("SELECT * FROM managementapplication.contract WHERE SquadNumber=" + contractNumber);
        return stmt;
    }

    private String checkIfSquadNumberIsValid(int squadNumber) {
        String stmt = new String("SELECT * FROM managementapplication.player WHERE SquadNumber=" + squadNumber);
        return stmt;

    }

    private String getAllLeagueInformation() {
        String stmt = new String("SELECT * FROM managementapplication.league");
        return stmt;
    }


    private String getAllPlayerInformation() {
        String stmt = new String("SELECT * FROM managementapplication.player");
        return stmt;
    }

    private String getAllGBodyInformation() {
        String stmt = new String("SELECT * FROM managementapplication.governingbody");
        return stmt;
    }

    private String getAllTeamInformation() {
        String stmt = new String("SELECT * FROM managementapplication.team");
        return stmt;
    }


    private String getAllManagerInformation() {
        String stmt = new String("SELECT * FROM managementapplication.manager");
        return stmt;
    }

    private String updatePlayerName(String name) {
        String stmt = new String("UPDATE managementapplication.player SET Name='" + name + "' " + "WHERE SquadNumber=" + userSqNum);
        return stmt;
    }


    private String updatePlayerAvailability(int availability) {
        String stmt = new String("UPDATE managementapplication.player SET Availability='" + availability + "' " + "WHERE SquadNumber=" + userSqNum);
        return stmt;
    }


    // MANAGER QUERIES
    private String checkIfManagerSINIsValid(int SIN) {
        String stmt = new String("SELECT * FROM managementapplication.manager WHERE SIN=" + SIN);
        return stmt;
    }

    private String getManagerInformation(int SIN) {
        String stmt = new String("SELECT * FROM managementapplication.manager WHERE SIN=" + SIN);
        return stmt;
    }


// ADVANCED SEARCH QUERIES

    public String AdvancedSearchPlayer(String position, int price, String name, int age, int salary, String nationality,
                                       int squadNumber, int availability, int rating) {

        int and = 0;
        String addAnd = "";

        String SQLPlayerSearch = "SELECT * FROM managementapplication.player WHERE ";

        if (position != "") {
            SQLPlayerSearch = SQLPlayerSearch + "position = " + "'" + position + "'";
            and = 1;
        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (price != 0) {
            SQLPlayerSearch = SQLPlayerSearch + addAnd + " price = " + price;
            and = 1;
        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (name != "") {
            SQLPlayerSearch = SQLPlayerSearch + addAnd + " name = " + "'" + name + "'";
            and = 1;
        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (age != 0) {
            SQLPlayerSearch = SQLPlayerSearch + addAnd + " age = " + age;
            and = 1;

        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (salary != 0) {
            SQLPlayerSearch = SQLPlayerSearch + addAnd + " salary = " + salary;
            and = 1;

        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (nationality != "") {
            SQLPlayerSearch = SQLPlayerSearch + addAnd + " nationality = " + "'" + nationality + "'";
            and = 1;

        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (squadNumber != 0) {
            SQLPlayerSearch = SQLPlayerSearch + addAnd + " squadNumber = " + squadNumber;
            and = 1;

        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (availability != 2) {
            SQLPlayerSearch = SQLPlayerSearch + addAnd + " availability = " + availability;
            and = 1;

        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (rating != 0) {
            SQLPlayerSearch = SQLPlayerSearch + addAnd + " rating = " + rating;
        }

        String stmt = new String(SQLPlayerSearch);
        System.out.println(stmt);
        return stmt;

    }


    public String AdvancedSearchContract(int lenRemain, int duration, int loanOption) {

        int and = 0;
        String addAnd = "";

        String SQLContractSearch = "SELECT * FROM managementapplication.contract WHERE ";

        if (lenRemain != 0) {
            SQLContractSearch = SQLContractSearch + " LengthRemaining = " + lenRemain;
            and = 1;
        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (duration != 0) {
            SQLContractSearch = SQLContractSearch + addAnd + " Duration = " + duration;
            and = 1;
        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (loanOption != 0) {
            SQLContractSearch = SQLContractSearch + addAnd + " LoanOptions = " + loanOption;
            and = 1;
        }
        if (and == 1) {
            addAnd = " AND ";
        }

        String stmt = new String(SQLContractSearch);
        System.out.println(stmt);

        return stmt;
    }


    public String AdvancedSearchManager(String name, int JobSecurity){

        int and = 0;
        String addAnd = "";

        String SQLManagerSearch = "SELECT * FROM managementapplication.manager WHERE ";

        if(name != ""){
            SQLManagerSearch = SQLManagerSearch + " Name = " + "'" + name + "'";
            and = 1;
        }
        if(and == 1){
            addAnd = " AND ";
        }
        if(JobSecurity != 0){
            SQLManagerSearch = SQLManagerSearch + addAnd + " JobSecurity = " + JobSecurity;
        }

        String stmt = new String(SQLManagerSearch);
        System.out.println(stmt);
        return stmt;
    }


    public String AdvancedSearchGBody(String name, String president, String HQ){

        int and = 0;
        String addAnd = "";

        String SQLGBodySearch = "SELECT * FROM managementapplication.governingbody WHERE ";

        if(name != ""){
            SQLGBodySearch = SQLGBodySearch + " Name = " + "'" + name + "'";
            and = 1;
        }
        if(and == 1 ){
            addAnd = " AND ";
        }
        if(president != ""){
            SQLGBodySearch = SQLGBodySearch + addAnd + " President = " + "'" + president + "'";
            and = 1;
        }
        if(and == 1 ){
            addAnd = " AND ";
        }
        if(HQ != ""){
            SQLGBodySearch = SQLGBodySearch + addAnd + " HQ = " + "'" + HQ + "'";
        }

        String stmt = new String(SQLGBodySearch);
        System.out.println(stmt);
        return stmt;

    }


    public String AdvancedSearchLeague(int numberOfTeams, String country, String sponsor, String name){

        int and = 0;
        String addAnd = "";

        String SQLLeagueSearch = "SELECT * FROM managementapplication.league WHERE ";


        if(numberOfTeams != 0){
            SQLLeagueSearch = SQLLeagueSearch + " Teams = " + numberOfTeams;
            and = 1;
        }
        if(and == 1 ){
            addAnd = " AND ";
        }
        if(country != ""){
            SQLLeagueSearch = SQLLeagueSearch + addAnd + " Country = " + "'" + country + "'";
            and = 1;
        }
        if(and == 1 ){
            addAnd = " AND ";
        }
        if(sponsor != ""){
            SQLLeagueSearch = SQLLeagueSearch + addAnd + " Sponsor = " + "'" + sponsor + "'";
        }
        if(and == 1 ){
            addAnd = " AND ";
        }
        if(name != ""){
            SQLLeagueSearch = SQLLeagueSearch + addAnd + " Name = " + "'" + name + "'";
        }

        String stmt = new String(SQLLeagueSearch);
        System.out.println(stmt);
        return stmt;

    }


    public String AdvancedSearchTeam(String TMSlogan, String name){

        int and = 0;
        String addAnd = "";

        String SQLTeamSearch = "SELECT * FROM managementapplication.team WHERE ";



        if(and == 1 ){
            addAnd = " AND ";
        }
        if(TMSlogan != ""){
            SQLTeamSearch = SQLTeamSearch + addAnd + " TMSlogan = " + "'" + TMSlogan + "'";
            and = 1;
        }
        if(and == 1 ){
            addAnd = " AND ";
        }
        if(name != ""){
            SQLTeamSearch = SQLTeamSearch + addAnd + " Name = " + "'" + name + "'";
        }

        String stmt = new String(SQLTeamSearch);
        System.out.println(stmt);
        return stmt;
    }


    public String AdvancedPlayerContractJoin(int lenRemain, int duration, int loanOption) {

        int and = 0;
        String addAnd = "";

        String SQLPlayerContractJoin = "SELECT * FROM managementapplication.player INNER JOIN managementapplication.contract ON contract.SquadNumber = player.SquadNumber WHERE ";


        if (lenRemain != 0) {
            SQLPlayerContractJoin = SQLPlayerContractJoin + addAnd + " contract.LengthRemaining = " + lenRemain;
            and = 1;
        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (duration != 0) {
            SQLPlayerContractJoin = SQLPlayerContractJoin + addAnd + " contract.Duration = " + duration;
            and = 1;
        }
        if (and == 1) {
            addAnd = " AND ";
        }
        if (loanOption != 2) {
            SQLPlayerContractJoin = SQLPlayerContractJoin + addAnd + " contract.LoanOption = " + loanOption;
            and = 1;
        }
        if (and == 1) {
            addAnd = " AND ";
        }

        String stmt = new String (SQLPlayerContractJoin);
        System.out.println(stmt);
        return stmt;
    }



    public String AdvancedManagerTeamJoin(String managerName, int JobSecurity, int managerTeamId, int teamId, String TMSlogan, String teamName){

        int and = 0;
        String addAnd = "";

        String SQLManagerTeamJoin = "SELECT * FROM managementapplication.manager INNER JOIN managementapplication.team ON manager.managerTeamId = team.teamId WHERE ";

        if(managerName != ""){
            SQLManagerTeamJoin = SQLManagerTeamJoin + " Name = " + "'" + managerName + "'";
            and = 1;
        }
        if(and == 1){
            addAnd = " AND ";
        }
        if(JobSecurity != 0){
            SQLManagerTeamJoin = SQLManagerTeamJoin + addAnd + " JobSecurity = " + JobSecurity;
        }

        if (and == 1) {
            addAnd = " AND ";
        }
        if(teamId != 0){
            SQLManagerTeamJoin = SQLManagerTeamJoin + addAnd + " TeamID = " + teamId;
            and = 1;
        }
        if(and == 1 ){
            addAnd = " AND ";
        }
        if(TMSlogan != ""){
            SQLManagerTeamJoin = SQLManagerTeamJoin + addAnd + " TMSlogan = " + "'" + TMSlogan + "'";
            and = 1;
        }
        if(and == 1 ){
            addAnd = " AND ";
        }
        if(teamName != ""){
            SQLManagerTeamJoin = SQLManagerTeamJoin + addAnd + " Name = " + "'" + teamName + "'";
        }
        String stmt = new String(SQLManagerTeamJoin);
        System.out.println(stmt);
        return stmt;

    }

    public String AdvancedBestSomethingPerTeamAggregation(int team, String criteria) {

        String SQLBestPlayerAgg = "SELECT * FROM managementapplication.player WHERE player." + criteria + " = ((SELECT max(player." + criteria + ") FROM managementapplication.player WHERE player.teamID =" + team + "))";

        String stmt = new String(SQLBestPlayerAgg);
        System.out.println(stmt);
        return stmt;
    }

    public String somethingAndHigestRatedPlayer(String highest, String lowest) {



        String SQLsomethingHighestRatedPLayer = "SELECT * FROM managementapplication.player C WHERE C." + highest + "= (SELECT MAX(C2." + highest + ") FROM managementapplication.player C2 WHERE C." + lowest + "= (SELECT MIN(C3." + lowest + ") FROM managementapplication.player C3))";


        String stmt = new String(SQLsomethingHighestRatedPLayer);
        System.out.println(stmt);
        return stmt;
    }

    public String deleteContract(int contractNumber){


        String SQLDeleteContract = "DELETE FROM managementapplication.contract WHERE ContractNumber = " + contractNumber;

        String stmt = new String(SQLDeleteContract);
        System.out.println(stmt);
        return stmt;
    }


    public String deletePlayerCascadeContract(int SquadNumber){


        String SQLDeletePlayer = "DELETE FROM managementapplication.player WHERE SquadNumber = " + SquadNumber;

        String stmt = new String(SQLDeletePlayer);
        System.out.println(stmt);
        return stmt;
    }


    public String selectAvailablePlayersNotInTeams(String teamId1, String teamId2){

        String SQLAvailableNotInTeams = "SELECT * FROM managementapplication.player WHERE Availability = 1 AND TeamID NOT IN (SELECT TeamID FROM managementapplication.team WHERE Name = ";

        if(teamId1 != "") {
            SQLAvailableNotInTeams = SQLAvailableNotInTeams + "'" + teamId1 + "'";
        }
        if(teamId2 != "") {
            SQLAvailableNotInTeams = SQLAvailableNotInTeams + " OR Name = " + "'" + teamId2 + "'";
        }

        SQLAvailableNotInTeams = SQLAvailableNotInTeams + ")";

        String stmt = new String(SQLAvailableNotInTeams);

        System.out.println(stmt);
        return stmt;
    }

}
