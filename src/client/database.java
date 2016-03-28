package client;


import java.sql.*;
import java.util.ArrayList;


import Objects.*;

/**
 * Created by Imran on 2016-02-25.
 */
public class database {

    String dbName = "jdbc:mysql://localhost:3306?useSSL=false";
    Connection dbConnect;
    database connection;
    String username = "root";
    String password = "whatsername";
    int userSqNum;
    int userSIN;



    public database(int sqNum){
        this.userSqNum = sqNum;
    }


    public Connection createDBConnection(){
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

    public void insert (Connection connection, String firstName, String lastName){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT into names " + "VALUES(firstName, lastName)");
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    public contract selectUserContractInformationBySquadNumber(Connection connection){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(getUserContractInformationBySquadNumber(userSqNum));
            contract userContract = new contract(result);
            return userContract.formatContractDetails();
        } catch (SQLException e){
            System.out.println(e);
            return null;
        }

    }


    public player selectAllAccountInformation(Connection connection){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(getUserAccountInformation(userSqNum));
            player user = new player(result);
            return user.formatPlayerDetails();
        } catch (SQLException e){
            System.out.println(e);
            return null;
        }

    }


    public ArrayList<league> getAllLeagues(Connection connection){
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
            for(int i = 0; i < rows + 1; i++){
                leagues.add(new league(result.getInt("Teams"),result.getString("Country"), result.getString("Sponsor"),
                        result.getString("Name")));
                result.next();
            }

            return leagues;

        } catch (SQLException e){
            System.out.println(e);
            return null;
        }

    }



    public ArrayList<team> getAllTeams(Connection connection){
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
                System.out.println("No records found");
            }
            result.first();
            for(int i = 0; i < rows + 1; i++){
                teams.add(new team(result.getInt("Team ID"),result.getString("TM Slogan"), result.getInt("Budget"),
                        result.getString("Name")));
                result.next();
            }

            return teams;

        } catch (SQLException e){
            System.out.println(e);
            return null;
        }

    }



    public ArrayList<GBody> getAllGBodies(Connection connection){
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
                System.out.println("No records found");
            }
            result.first();
            for(int i = 0; i < rows + 1; i++){
                gbodies.add(new GBody(result.getInt("Revenue"),result.getString("Name"), result.getString("President"),
                        result.getString("HQ")));
                result.next();
            }

            return gbodies;

        } catch (SQLException e){
            System.out.println(e);
            return null;
        }

    }


    public ArrayList<manager> getAllManagers(Connection connection){
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
                System.out.println("No records found");
            }
            result.first();
            for(int i = 0; i < rows + 1; i++){
                managers.add(new manager(result.getString("Name")));
                result.next();
            }

            return managers;

        } catch (SQLException e){
            System.out.println(e);
            return null;
        }

    }










    public boolean checkIfSquadNumberExists(Connection connection){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(checkIfSquadNumberIsValid(userSqNum));
            result.first();
            //Need print line below dont delete
            System.out.println(result.getInt("SquadNumber"));
            return true;
        } catch (SQLException e){
            System.out.println(e);
            return false;
        }

    }


    public boolean checkIfManagerSINExists(Connection connection){
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(checkIfManagerSINIsValid(userSIN));
            result.first();
            //Need print line below dont delete
            System.out.println(result.getString("Name"));
            return true;
        } catch (SQLException e){
            System.out.println(e);
            return false;
        }

    }

    public void changePlayerName(Connection connection, String name){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(updatePlayerName(name));
        } catch(SQLException e){

        }
    }


    public void changePlayerAvailability(Connection connection, int availability){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(updatePlayerAvailability(availability));
        } catch(SQLException e){

        }
    }






    /*
    SQL queries
     */


    // PLAYER QUERIES
    private String getUserAccountInformation(int squadNumber){
        String stmt = new String("SELECT * FROM managementapplication.player WHERE SquadNumber=" + squadNumber);
        return stmt;
    }

    private String getUserContractInformationBySquadNumber(int squadNumber){
        String stmt = new String("SELECT * FROM managementapplication.contract WHERE SquadNumber=" + squadNumber);
        return stmt;
    }


    private String getUserContractInformationByContractNumber(int contractNumber){
        String stmt = new String("SELECT * FROM managementapplication.contract WHERE SquadNumber=" + contractNumber);
        return stmt;
    }

    private String checkIfSquadNumberIsValid(int squadNumber){
        String stmt = new String("SELECT * FROM managementapplication.player WHERE SquadNumber=" + squadNumber) ;
        return stmt;

    }

    private String getAllLeagueInformation(){
        String stmt = new String("SELECT * FROM managementapplication.league");
        return stmt;
    }

    private String getAllGBodyInformation(){
        String stmt = new String("SELECT * FROM managementapplication.governingbody");
        return stmt;
    }

    private String getAllTeamInformation(){
        String stmt = new String("SELECT * FROM managementapplication.team");
        return stmt;
    }


    private String getAllManagerInformation(){
        String stmt = new String("SELECT * FROM managementapplication.manager");
        return stmt;
    }

    private String updatePlayerName(String name){
        String stmt = new String("UPDATE managementapplication.player SET Name='" + name + "' " + "WHERE SquadNumber=" + userSqNum);
        return stmt;
    }


    private String updatePlayerAvailability(int availability){
        String stmt = new String("UPDATE managementapplication.player SET Availability='" + availability + "' " + "WHERE SquadNumber=" + userSqNum);
        return stmt;
    }








    // MANAGER QUERIES
    private String checkIfManagerSINIsValid(int SIN){
        String stmt = new String("SELECT * FROM managementapplication.manager WHERE SIN=" + SIN);
        return stmt;
    }

    private String getManagerInformation(int SIN){
        String stmt = new String("SELECT * FROM managementapplication.manager WHERE SIN=" + SIN);
        return stmt;
    }



}
