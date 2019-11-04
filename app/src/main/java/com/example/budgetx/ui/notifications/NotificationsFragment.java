package com.example.budgetx.ui.notifications;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.budgetx.R;
import com.example.budgetx.ui.ExpenseBottomSheet;

import java.util.List;

public class NotificationsFragment extends Fragment {

    ScrollView scroller;
    LinearLayout linearLayout;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        scroller = root.findViewById(R.id.scrollView);
        linearLayout = root.findViewById(R.id.linearlayout);
        final ExpenseBottomSheet expenseSheet = new ExpenseBottomSheet();
        Button addExp = root.findViewById(R.id.exp);

        addExp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                expenseSheet.show(getFragmentManager(),"BottomSheet");
            }
        });

        List transactions = notificationsViewModel.LoadTransactions(root);
        for (Object tempObj  : transactions) {
            String tempString =tempObj.toString();
            String[] components = tempString.split(",");

            TextView tempt =  new TextView(getContext());
            tempt.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            tempt.setGravity(Gravity.CENTER_HORIZONTAL);
            String finalStr="";
            for(String temp : components){
                temp.replaceAll(",","");
                finalStr+=temp+" ";
            }
            tempt.setText(finalStr);
            linearLayout.addView(tempt);
        }

        return root;
    }
}