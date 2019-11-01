package com.example.budgetx.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "budgetx.db";

    public static final String TABLE_NAME = "transactions";
    public static final String TRANSACTION_ID = "transcaction_id";
    public static final String TRANSACTION_CATEGORY_ID = "transaction_category_id";
    public static final String CATEGORY_ID = "category_id";
    public static final String FREQUENCY_ID = "frequency_id";
    public static final String AMOUNT = "amount";
    public static final String DESCRIPTION = "description";

    public MyDBHandler(Context context, SQLiteDatabase.CursorFactory factory){

        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, "+TRANSACTION_CATEGORY_ID+" INT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){

    }

    public String loadHandler(){
        String result = "";
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
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
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Transaction findHandler(int transactionID){
        String query = "Select * FROM " + TABLE_NAME + "WHERE" + TRANSACTION_ID + " = " + "'" + transactionID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Transaction transaction = new Transaction();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            transaction.SetValues(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),
                    Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)),
                    Float.parseFloat(cursor.getString(4)),cursor.getString(5));
            cursor.close();
        } else {
            transaction = null;
        }
        db.close();
        return transaction;
    }

    public boolean deleteHandler(int transactionID){
        boolean result = false;
        String query = "Select*FROM" + TABLE_NAME + "WHERE" + TRANSACTION_ID + "= '" + String.valueOf(transactionID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Transaction transaction = new Transaction();
        if (cursor.moveToFirst()) {
            transaction.SetTranscationID(Integer.parseInt(cursor.getString(0)));
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

    public boolean updateHandler(int transactionID, int transactionCategoryID, int categoryID, int frequencyID, float amount, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(TRANSACTION_CATEGORY_ID, transactionCategoryID);
        args.put(CATEGORY_ID, categoryID);
        args.put(FREQUENCY_ID, frequencyID);
        args.put(AMOUNT, amount);
        args.put(DESCRIPTION, description);

        return db.update(TABLE_NAME, args, TRANSACTION_ID + "=" + transactionID, null) > 0;
    }


}
