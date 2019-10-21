package com.example.budgetx.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.budgetx.R;
import com.example.budgetx.ui.ExpenseBottomSheet;
import com.example.budgetx.ui.IncomeBottomSheet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class HomeFragment extends BottomSheetDialogFragment {


    private HomeViewModel homeViewModel;
    private LayoutInflater inflator;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        inflator = inflater;
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button addInc = (Button) root.findViewById(R.id.inc);
        Button addExp = (Button) root.findViewById(R.id.exp);
        addInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncomeBottomSheet incomesheet = new IncomeBottomSheet();
                incomesheet.show(getFragmentManager(),"BottomSheet");
            }
        });

        addExp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ExpenseBottomSheet expensesheet = new ExpenseBottomSheet();
                expensesheet.show(getFragmentManager(),"BottomSheet");
            }
        });

        return root;
    }
}