package numb3r.gte.com.numb3r.Model;

/**
 * Created by andrewlaurienrsocia on 01/12/2017.
 */

public class MyNumbers {

    private String Combination;
    private String Amount;
    private String IsRambolito;

    public void setCombination(String combination) {
        Combination = combination;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setIsRambolito(boolean isRambolito) {
        if (isRambolito) {
            IsRambolito = "Yes";
        } else {
            IsRambolito = "No";
        }
    }

    public String getCombination() {
        return Combination;
    }

    public String getAmount() {
        return Amount;
    }

    public String getIsRambolito() {
        return IsRambolito;
    }

}
