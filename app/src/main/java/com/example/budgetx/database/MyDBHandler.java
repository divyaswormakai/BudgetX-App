package com.example.budgetx.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
    private static final String LAST_UPDATED = "last_updated_date";

    public MyDBHandler(Context context, SQLiteDatabase.CursorFactory factory){

        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, "+TRANSACTION_CATEGORY_ID+" TEXT," +
                CATEGORY_ID + " TEXT,"+ FREQUENCY_ID + " TEXT,"+AMOUNT+ " FLOAT," +  DESCRIPTION+ " TEXT,"+ENTRYDATE+" TEXT,"+LAST_UPDATED+" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){

    }

    //make result and array and pass that array
    public List loadHandler(){
        List result = new ArrayList();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String type = cursor.getString(1);
            String cat = cursor.getString(2);
            String freq = cursor.getString(3);
            float amt = cursor.getFloat(4);
            String desc = cursor.getString(5);
            String entryDate = cursor.getString(6);
            String updateDate = cursor.getString(7);
            String temp =id + "," + type +"," +cat+"," +freq + "," +amt +"," +desc+"," +entryDate+","+updateDate;
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
        values.put(LAST_UPDATED, transaction.GetUpdateDate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);

        db.close();
    }

//    public Transaction findHandler(int transactionID){
//        String query = "Select * FROM " + TABLE_NAME + " WHERE " + TRANSACTION_ID + " = " + "'" + transactionID + "'";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Transaction transaction = new Transaction();
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            transaction.SetValues(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
//                    cursor.getString(2),cursor.getString(3),
//                    Float.parseFloat(cursor.getString(4)),cursor.getString(5),
//                    cursor.getString(6),cursor.getString(7));
//            cursor.close();
//        } else {
//            transaction = null;
//        }
//        db.close();
//        return transaction;
//    }

    //gives income or expense based on the arguments
    public List findIncExp(String transaction_type){
        List result = new ArrayList();
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + TRANSACTION_CATEGORY_ID + " = " + "'" + transaction_type + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String type = cursor.getString(1);
            String cat = cursor.getString(2);
            String freq = cursor.getString(3);
            float amt = cursor.getFloat(4);
            String desc = cursor.getString(5);
            String entryDate = cursor.getString(6);
            String updateDate = cursor.getString(7);
            String temp =id + "," + type +"," +cat+"," +freq + "," +amt +"," +desc+"," +entryDate+","+updateDate;
            result.add(temp);
        }
        cursor.close();
        db.close();
        return result;
    }

    public boolean deleteHandler(int transactionID){
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + TRANSACTION_ID + "= " + transactionID;
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

    public boolean updateHandler(int transactionID, String transactionCategoryID, String categoryID, String frequencyID, float amount, String description,String dates,String update_date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(TRANSACTION_CATEGORY_ID, transactionCategoryID);
        args.put(CATEGORY_ID, categoryID);
        args.put(FREQUENCY_ID, frequencyID);
        args.put(AMOUNT, amount);
        args.put(DESCRIPTION, description);
        args.put(ENTRYDATE, dates);
        args.put(LAST_UPDATED,update_date);

        return db.update(TABLE_NAME, args, TRANSACTION_ID + "=" + transactionID, null) > 0;
    }

    public void Automate(View view){
        List transactions = loadHandler();

        for (Object transaction:transactions ) {
            String tempString =transaction.toString();
            final String[] components = tempString.split(",");
            if(!components[3].contains("One Time") && !components[4].contains("Automated")){
                //today date
                LocalDate today = LocalDate.now();
                DateTimeFormatter simpleDateFormat =DateTimeFormatter.ofPattern("yyyy/MM/dd");
                String todayDate = today.format(simpleDateFormat);

                //Last update date
                String last_upload = components[7];

                if(todayDate !=  last_upload){
                    LocalDate updateDate = LocalDate.parse(last_upload,simpleDateFormat);
                    Period diffPeriod = Period.between(updateDate,today);       //first arg should be smaller
                    int diff;

                    int id = Integer.parseInt(components[0]);
                    String type = components[1];
                    String cat = components[2];
                    String freq = "Automated";
                    float amt = Float.parseFloat(components[4]);
                    String entryDate = components[6];
                    String desc = cat + " Automated of "+entryDate;

                    switch (components[3]){
                        case "Daily":
                            diff = diffPeriod.getDays();
                            for(int i=0;i<diff;i++){
                                updateDate = updateDate.plusDays(1);
                                Transaction tempTransaction = new Transaction(type,cat,freq,amt,desc,updateDate.format(simpleDateFormat),updateDate.format(simpleDateFormat));
                                addHandler(tempTransaction);
                            }
                            break;

                        case "Weekly":
                            diff = diffPeriod.getDays();
                            if(diff>=7){
                                for(int i=0;i<=diff;i+=7){
                                    updateDate = updateDate.plusDays(7);
                                    Transaction tempTransaction = new Transaction(type,cat,freq,amt,desc,updateDate.format(simpleDateFormat),updateDate.format(simpleDateFormat));
                                    addHandler(tempTransaction);
                                }
                            }
                            break;

                        case "Monthly":
                            diff = diffPeriod.getMonths();
                            for(int i=0;i<diff;i++){
                                updateDate = updateDate.plusMonths(1);
                                Transaction tempTransaction = new Transaction(type,cat,freq,amt,desc,updateDate.format(simpleDateFormat),updateDate.format(simpleDateFormat));
                                addHandler(tempTransaction);
                            }
                            break;

                        default:
                            System.out.println("SDF");
                            break;
                    }
                    updateHandler(id,type,cat,components[3],amt,components[5],components[6],updateDate.format(simpleDateFormat));
                }
                else{
                    continue;
                }
            }
        }
    }


}
