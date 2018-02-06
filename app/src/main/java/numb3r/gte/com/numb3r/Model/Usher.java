package numb3r.gte.com.numb3r.Model;

/**
 * Created by andrewlaurienrsocia on 07/12/2017.
 */

public class Usher {

    private String AccountID;
    private String FinancierID;
    private String CoordinatorID;
    private String Representative;
    private String MobileNumber;
    private String Profile;
    private String TASNumber;

    public String getFinancierID() {
        return FinancierID;
    }

    public void setFinancierID(String financierID) {
        FinancierID = financierID;
    }

    public String getCoordinatorID() {
        return CoordinatorID;
    }

    public void setCoordinatorID(String coordinatorID) {
        CoordinatorID = coordinatorID;
    }

    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(String accountID) {
        AccountID = accountID;
    }

    public String getRepresentative() {
        return Representative;
    }

    public void setRepresentative(String representative) {
        Representative = representative;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }

    public char getFirstNameChar() {
        return this.Representative.charAt(0);
    }

    public String getTASNumber() {
        return TASNumber;
    }

    public void setTASNumber(String TASNumber) {
        this.TASNumber = TASNumber;
    }
}
