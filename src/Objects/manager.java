package Objects;

import java.sql.ResultSet;
import java.sql.SQLException;


public class manager {

    manager user;

    private String name;
    private int SIN;
    private int JobSecurity;
    private int ManagerTeamID;
    private ResultSet details;

    public manager getUser() {
        return user;
    }

    public void setUser(manager user) {
        this.user = user;
    }

    public manager(ResultSet details){
        this.details = details;
    }


    public manager(String name, int SIN, int JobSecurity, int ManagerTeamID){
        this.name = name;
        this.SIN = SIN;
        this.JobSecurity = JobSecurity;
        this.ManagerTeamID = ManagerTeamID;

    }

    public manager(String name, int JobSecurity){
        this.name = name;
        this.JobSecurity = JobSecurity;

    }

    public manager(String name){
        this.name = name;

    }

    public manager(int ManagerTeamID, String name){
        this.name = name;
        this.ManagerTeamID = ManagerTeamID;

    }


    public manager formatManagerDetails() throws SQLException{
        ResultSet information = details;
        information.first();

        manager formattedUser = new manager(information.getString("Name"), information.getInt("SIN"), information.getInt("JobSecurity"), information.getInt("TeamID"));

        return formattedUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSIN() {
        return SIN;
    }

    public void setSIN(int SIN) {
        this.SIN = SIN;
    }

    public int getJobSecurity() {
        return JobSecurity;
    }

    public void setJobSecurity(int JobSecurity) {
        this.JobSecurity = JobSecurity;
    }

    public int getManagerTeamID() { return ManagerTeamID; }
}
