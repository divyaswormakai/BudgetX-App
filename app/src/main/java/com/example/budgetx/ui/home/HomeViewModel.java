package com.example.budgetx.ui.home;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.budgetx.database.MyDBHandler;
import com.example.budgetx.database.Transaction;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

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
        int type = 1;
        int cat = 1;
        int freq = 1;
        float amt =555;
        String desc = "YES";
        Transaction trans = new Transaction(type,cat,freq,amt,desc);
        db.addHandler(trans);
    }
}