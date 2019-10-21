package com.example.budgetx.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.budgetx.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class IncomeBottomSheet extends BottomSheetDialogFragment {

    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_income,container,false);
        return v;
    }
}
