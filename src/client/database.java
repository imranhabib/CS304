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



    public database(){

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
            System.out.println("error inserting into DB");
        }
    }

    public void selectAllAccountInformation(Connection connection){
        try{
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT" + " * " +"FROM managementapplication.player " + "WHERE ");
            player user = new player(result);
            System.out.println("here");
            user.formatPlayerDetails();
        } catch (SQLException e){
            System.out.println("failed " + e);

        }

    }




    public void breakDBConnection(){}











}
