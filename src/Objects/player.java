package Objects;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Imran on 2016-03-13.
 */
public class player {

    player user;

    //Player attributes
    private String position;
    private int price;
    private String name;
    private int age;
    private int salary;
    private String nationality;
    private int squadNumber;
    private boolean availability;
    private int rating;
    private ResultSet details;


    public player getUser() {
        return user;
    }

    public void setUser(player user) {
        this.user = user;
    }

    public player(ResultSet details){
        this.details = details;
    }


    public player(String position, int price, String name, int age, int salary, String nationality,
                  int squadNumber, boolean availability, int rating){
        this.position = position;
        this.price = price;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.nationality = nationality;
        this.squadNumber = squadNumber;
        this.availability = availability;
        this.rating = rating;


    }

    public player formatPlayerDetails() throws SQLException{
        ResultSet information = details;
        information.first();

        player formattedUser = new player(information.getString("Position"), information.getInt("price"),
                information.getString("name"), information.getInt("age"), information.getInt("salary"),
                information.getString("Nationality"), information.getInt("SquadNumber"), information.getBoolean("Availability"),
                information.getInt("rating"));


            return formattedUser;
    }



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getSquadNumber() {
        return squadNumber;
    }

    public void setSquadNumber(int squadNumber) {
        this.squadNumber = squadNumber;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }























}
