package Objects;

import java.sql.ResultSet;

/**
 * Created by Imran on 2016-03-20.
 */
public class league {

    private int numberOfTeams;
    private String country;
    private String sponsor;
    private String name;
    private ResultSet details;


    public league(int numberOfTeams, String country, String sponsor, String name){
        this.numberOfTeams = numberOfTeams;
        this.country = country;
        this.sponsor = sponsor;
        this.name = name;
    }

    public league(ResultSet details){
        this.details = details;
    }






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }
}
