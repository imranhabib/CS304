package Objects;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Imran on 2016-03-14.
 */
public class contract {


    contract userContract;
    private ResultSet details;

    //Contract attributes
    private int lenRemain;
    private int duration;
    private boolean loanOption;
    private int squadNumber;
    private int contractNum;

    public contract(int lenRemain, int duration, boolean loanOption, int contractNum, int squadNumber){
        this.lenRemain = lenRemain;
        this.duration = duration;
        this.loanOption = loanOption;
        this.contractNum = contractNum;
        this.squadNumber = squadNumber;
    }

    public contract(int lenRemain, int duration, boolean loanOption){
        this.lenRemain = lenRemain;
        this.duration = duration;
        this.loanOption = loanOption;

    }

    public contract (ResultSet result){
        this.details = result;
    }

    public contract formatContractDetails() throws SQLException{
        ResultSet information = details;
        information.first();

        contract formattedUserContract = new contract(information.getInt("LengthRemaining"), information.getInt("Duration"),
                information.getBoolean("LoanOption"), information.getInt("ContractNumber"), information.getInt("SquadNumber"));


        return formattedUserContract;
    }



    public int getContractNum() {
        return contractNum;
    }

    public void setContractNum(int contractNum) {
        this.contractNum = contractNum;
    }

    public contract getUserContract() {
        return userContract;
    }

    public void setUserContract(contract userContract) {
        this.userContract = userContract;
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

    public int getSquadNumber() {
        return squadNumber;
    }

    public void setSquadNumber(int squadNumber) {
        this.squadNumber = squadNumber;
    }
}
