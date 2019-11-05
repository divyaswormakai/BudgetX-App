package com.example.budgetx.ui.dashboard;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.budgetx.database.MyDBHandler;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public List LoadTransactions(View view) {
        MyDBHandler db = new MyDBHandler(view.getContext(), null);
        return(db.findIncExp("Income"));
    }
}