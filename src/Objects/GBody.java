package Objects;

/**
 * Created by Imran on 2016-03-23.
 */
public class GBody {

    private int revenue;
    private String name;
    private String president;
    private String HQ;

    public GBody(int revenue, String name, String president, String HQ){
        this.revenue = revenue;
        this.name = name;
        this.president = president;
        this.HQ = HQ;

    }


    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getHQ() {
        return HQ;
    }

    public void setHQ(String HQ) {
        this.HQ = HQ;
    }










}
