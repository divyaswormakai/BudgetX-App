package com.example.budgetx.ui.notifications;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.budgetx.database.MyDBHandler;

import java.util.List;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public List LoadTransactions(View view) {
        MyDBHandler db = new MyDBHandler(view.getContext(), null);
        return(db.findIncExp("Expense"));
    }
}