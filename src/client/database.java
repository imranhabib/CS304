package client;


import java.sql.*;
import Objects.player;

/**
 * Created by Imran on 2016-02-25.
 */
public class database {

    String dbName = "Put your local db URL here";
    Connection dbConnect;
    database connection;
    String username = "Put your local DB username here";
    String password = "Put your local DB password here";
    int userSqNum;



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

    public player selectAllAccountInformation(Connection connection){
        try{
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(getUserAccountInformation(userSqNum));
            player user = new player(result);
            return user.formatPlayerDetails();
        } catch (SQLException e){
            System.out.println(e);
            return null;
        }

    }




    public void breakDBConnection(){}



    /*
    SQL queries
     */

    private String getUserAccountInformation(int squadNumber){
        String stmt = new String("SELECT * FROM managementapplication.player WHERE SquadNumber=" + squadNumber);
        return stmt;
    }







}
