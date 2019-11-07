package com.example.budgetx.database;

import java.util.Date;

public class Transaction {
    int transactionID;
    String transactionCategoryID;
    String categoryID;
    String frequencyID;
    float amount;
    String description;
    String date;
    String update_data;

    public Transaction(){

    }

    public Transaction(String transactionCat,String catID, String frequencyID, float amt,String descp,String date,String entryDate){
        this.transactionCategoryID = transactionCat;
        this.categoryID = catID;
        this.frequencyID = frequencyID;
        this.amount = amt;
        this.description = descp;
        this.date = date;
        this.update_data = entryDate;
    }

    public void SetValues(int transactionID, String transactionCategoryID, String categoryID, String frequencyID, float amount, String description,String dates,String entryDate){
        this.transactionID = transactionID;
        this.transactionCategoryID = transactionCategoryID;
        this.categoryID = categoryID;
        this.frequencyID = frequencyID;
        this.amount = amount;
        this.description = description;
        this.date = dates;
        this.update_data = entryDate;
    }

    public void SetTransactionID(int id){
        this.transactionID = id;
    }

    public int GetTransactionID(){
        return this.transactionID;
    }

    public String GetTransactionType(){
        return this.transactionCategoryID;
    }

    public String GetCatID()
    {
        return this.categoryID;
    }

    public String GetFrequencyID(){
        return this.frequencyID;
    }

    public float GetAmount() { return this.amount; }

    public String GetDescription() { return this.description; }

    public String GetDate() { return this.date; }

    public String GetUpdateDate(){return this.update_data;}
}
