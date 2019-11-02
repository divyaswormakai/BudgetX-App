package com.example.budgetx.database;

public class Transaction {
    int transactionID;
    int transactionCategoryID;
    int categoryID;
    int frequencyID;
    float amount;
    String description;

    public Transaction(){

    }

    public Transaction(int transactionCat,int catID, int frequencyID, float amt,String descp){
        this.transactionCategoryID = transactionCat;
        this.categoryID = catID;
        this.frequencyID = frequencyID;
        this.amount = amt;
        this.description = descp;
    }

    public void SetValues(int transactionID, int transactionCategoryID, int categoryID, int frequencyID, float amount, String description){
        this.transactionID = transactionID;
        this.transactionCategoryID = transactionCategoryID;
        this.categoryID = categoryID;
        this.frequencyID = frequencyID;
        this.amount = amount;
        this.description = description;
    }

    public void SetTransactionID(int id){
        this.transactionID = id;
    }

    public int GetTransactionID(){
        return this.transactionID;
    }

    public int GetTransactionType(){
        return this.transactionCategoryID;
    }

    public int GetCatID()
    {
        return this.categoryID;
    }

    public int GetFrequencyID(){
        return this.frequencyID;
    }

    public float GetAmount()
    {
        return this.amount;
    }

    public String GetDescription()
    {
        return this.description;
    }
}
