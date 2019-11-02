package com.example.budgetx.ui.home;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.budgetx.database.MyDBHandler;
import com.example.budgetx.database.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Calendar calendar;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public String LoadTransactions(View view){
        MyDBHandler db = new MyDBHandler(view.getContext(),null);
        return db.loadHandler();
    }
    public void AddTest(View view){
        MyDBHandler db = new MyDBHandler(view.getContext(),null);
        String type = "Income";
        String cat = "Salary";
        String freq = "One Time";
        float amt =555;
        String desc = "YES";
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = simpleDateFormat.format(c);
        Transaction trans = new Transaction(type,cat,freq,amt,desc,formattedDate);
        db.addHandler(trans);
    }
}