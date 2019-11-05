package com.example.budgetx.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "budgetx.db";

    private static final String TABLE_NAME = "transactions";
    private static final String TRANSACTION_ID = "transaction_id";
    private static final String TRANSACTION_CATEGORY_ID = "transaction_category_id";
    private static final String CATEGORY_ID = "category_id";
    private static final String FREQUENCY_ID = "frequency_id";
    private static final String AMOUNT = "amount";
    private static final String DESCRIPTION = "description";
    private static final String ENTRYDATE = "entry_date";

    public MyDBHandler(Context context, SQLiteDatabase.CursorFactory factory){

        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, "+TRANSACTION_CATEGORY_ID+" TEXT," +
                CATEGORY_ID + " TEXT,"+ FREQUENCY_ID + " TEXT,"+AMOUNT+ " FLOAT," +  DESCRIPTION+ " TEXT,"+ENTRYDATE+" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){

    }

    //make resilt and array and pass that array
    public List loadHandler(){
        List result = new ArrayList();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            float result_4 = cursor.getFloat(4);
            String result_5 = cursor.getString(5);
            String result_6 = cursor.getString(6);
            String temp =result_0 + "," + result_1 +"," +result_2+"," +result_3 + "," +result_4 +"," +result_5+"," +result_6;
                    //System.getProperty("line.separator");
            result.add(temp);
        }
        cursor.close();
        db.close();
        return result;
    }

    public void addHandler(Transaction transaction){
        ContentValues values = new ContentValues();
        values.put(TRANSACTION_CATEGORY_ID, transaction.GetTransactionType());
        values.put(CATEGORY_ID, transaction.GetCatID());
        values.put(FREQUENCY_ID, transaction.GetFrequencyID());
        values.put(AMOUNT, transaction.GetAmount());
        values.put(DESCRIPTION, transaction.GetDescription());
        values.put(ENTRYDATE, transaction.GetDate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Transaction findHandler(int transactionID){
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + TRANSACTION_ID + " = " + "'" + transactionID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Transaction transaction = new Transaction();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            transaction.SetValues(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),
                    Float.parseFloat(cursor.getString(4)),cursor.getString(5),
                    cursor.getString(6));
            cursor.close();
        } else {
            transaction = null;
        }
        db.close();
        return transaction;
    }

    //gives income or expense based on the arguments
    public List findIncExp(String type){
        List result = new ArrayList();
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + TRANSACTION_CATEGORY_ID + " = " + "'" + type + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            float result_4 = cursor.getFloat(4);
            String result_5 = cursor.getString(5);
            String result_6 = cursor.getString(6);
            String temp =result_0 + "," + result_1 +"," +result_2+"," +result_3 + "," +result_4 +"," +result_5+"," +result_6;
            //System.getProperty("line.separator");
            result.add(temp);
        }
        cursor.close();
        db.close();
        return result;
    }

    public boolean deleteHandler(int transactionID){
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + TRANSACTION_ID + "= " + String.valueOf(transactionID);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Transaction transaction = new Transaction();
        if (cursor.moveToFirst()) {
            transaction.SetTransactionID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, TRANSACTION_ID + "=?",
                    new String[] {
                String.valueOf(transaction.GetTransactionID())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateHandler(int transactionID, String transactionCategoryID, String categoryID, String frequencyID, float amount, String description,String dates){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(TRANSACTION_CATEGORY_ID, transactionCategoryID);
        args.put(CATEGORY_ID, categoryID);
        args.put(FREQUENCY_ID, frequencyID);
        args.put(AMOUNT, amount);
        args.put(DESCRIPTION, description);
        args.put(ENTRYDATE, dates);

        return db.update(TABLE_NAME, args, TRANSACTION_ID + "=" + transactionID, null) > 0;
    }


}
