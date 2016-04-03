package Objects;

/**
 * Created by Imran on 2016-04-03.
 */
public class managerTeam {


    //Manager Attributes
    private String name;
    private int SIN;
    private int JobSecurity;

    //Team Attributes
    private int teamId;
    private int budget;
    private String teamName;
    private String TMSlogan;



    public managerTeam(String name, int JobSecurity, int teamId, String TMSlogan, String teamName){
        this.name = name;
        this.JobSecurity = JobSecurity;

        this.teamId = teamId;
        this.TMSlogan = TMSlogan;
        this.teamName = teamName;




    }


    public String getName() {
        return name;
    }

    public int getSIN() {
        return SIN;
    }

    public int getJobSecurity() {
        return JobSecurity;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getBudget() {
        return budget;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTMSlogan() {
        return TMSlogan;
    }
}
