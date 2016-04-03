package Objects;

import java.sql.ResultSet;

/**
 * Created by Imran on 2016-04-03.
 */
public class contractPlayer {


    //Contract attributes
    private int lenRemain;
    private int duration;
    private boolean loanOption;
    private int contractNum;



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
    private int teamID;
    private ResultSet details;




    public contractPlayer(int lenRemain, int duration, boolean loanOption,
                          String position, int price, String name, int age, int salary, String nationality,
                          boolean availability, int rating, int teamID, int squadNumber){
        this.lenRemain = lenRemain;
        this.duration = duration;
        this.loanOption = loanOption;

        this.position = position;
        this.price = price;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.nationality = nationality;
        this.availability = availability;
        this.rating = rating;
        this.teamID = teamID;
        this.squadNumber = squadNumber;

    }

    public contractPlayer( String position, int price, String name, int age, int salary, String nationality,
                           boolean availability, int rating, int teamID){
        this.position = position;
        this.price = price;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.nationality = nationality;
        this.availability = availability;
        this.rating = rating;
        this.teamID = teamID;

    }



    public contractPlayer(int lenRemain, int duration, boolean loanOption, int contractNum, int squadNumber
                          ) {
        this.lenRemain = lenRemain;
        this.duration = duration;
        this.loanOption = loanOption;
        this.contractNum = contractNum;
        this.squadNumber = squadNumber;
    }

















    public int getLenRemain() {
        return lenRemain;
    }

    public void setLenRemain(int lenRemain) {
        this.lenRemain = lenRemain;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isLoanOption() {
        return loanOption;
    }

    public void setLoanOption(boolean loanOption) {
        this.loanOption = loanOption;
    }

    public int getContractNum() {
        return contractNum;
    }

    public void setContractNum(int contractNum) {
        this.contractNum = contractNum;
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

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public ResultSet getDetails() {
        return details;
    }

    public void setDetails(ResultSet details) {
        this.details = details;
    }
}
