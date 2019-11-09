package com.example.budgetx.ui.home;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.budgetx.database.MyDBHandler;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public List LoadTransactions(View view) {
        MyDBHandler db = new MyDBHandler(view.getContext(), null);
        return(db.loadHandler());
    }

    public void AutomateTransactions(View view){
        MyDBHandler db = new MyDBHandler(view.getContext(), null);
        db.Automate(view);
    }

}