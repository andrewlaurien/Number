package numb3r.gte.com.numb3r.Model;

import java.util.ArrayList;

/**
 * Created by andrewlaurienrsocia on 04/12/2017.
 */

public class Transaction {

    private String MobileNumber;
    private String TotalAmount;
    private ArrayList<MyNumbers> Numbers;
    private String UniqueTxnID;
    private String DrawHour;
    private String DrawDate;

    public Transaction(String mobileNumber, String totalAmount, ArrayList<MyNumbers> numbers, String uniqueTxnID) {
        this.MobileNumber = mobileNumber;
        this.TotalAmount = totalAmount;
        this.Numbers = numbers;
        this.UniqueTxnID = uniqueTxnID;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public ArrayList<MyNumbers> getNumbers() {
        return Numbers;
    }

    public void setNumbers(ArrayList<MyNumbers> numbers) {
        this.Numbers = numbers;
    }

    public String getUniqueTxnID() {
        return UniqueTxnID;
    }

    public void setUniqueTxnID(String uniqueTxnID) {
        UniqueTxnID = uniqueTxnID;
    }

    public String getDrawHour() {
        return DrawHour;
    }

    public void setDrawHour(String drawHour) {
        DrawHour = drawHour;
    }

    public String getDrawDate() {
        return DrawDate;
    }

    public void setDrawDate(String drawDate) {
        DrawDate = drawDate;
    }

}
