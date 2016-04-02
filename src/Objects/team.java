package Objects;

/**
 * Created by Imran on 2016-03-23.
 */
public class team {


    private int teamId;
    private int budget;
    private String name;
    private String TMSlogan;


    public team (int teamId, String TMSlogan, int budget, String name){
        this.teamId = teamId;
        this.TMSlogan = TMSlogan;
        this.budget = budget;
        this.name = name;

    }

    public team (String TMSlogan, String name){
        this.TMSlogan = TMSlogan;
        this.name = name;

    }


    public team (){


    }


    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTMSlogan() {
        return TMSlogan;
    }

    public void setTMSlogan(String TMSlogan) {
        this.TMSlogan = TMSlogan;
    }
}
