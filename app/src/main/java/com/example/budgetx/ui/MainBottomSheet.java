package com.example.budgetx.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.budgetx.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MainBottomSheet extends BottomSheetDialogFragment {
    Button cancel;
    Spinner dropdown;
    RadioButton inc,exp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_main,container,false);

        dropdown = v.findViewById(R.id.spinner);
        cancel = v.findViewById(R.id.cancel);
        inc = v.findViewById(R.id.incRadio);
        exp = v.findViewById(R.id.expRadio);

        Clickable();

        return v;
    }

    private void Clickable(){
        final Dialog dialog = getDialog();
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
    }

    public void onRadioButtonClicked(View v){
        boolean checked = ((RadioButton) v).isChecked();
        ArrayAdapter<CharSequence> adapter;
        if(checked) {
            switch (v.getId()) {
                case R.id.incRadio: {
                    adapter = ArrayAdapter.createFromResource(getContext(), R.array.income_categories, R.layout.support_simple_spinner_dropdown_item);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    dropdown.setAdapter(adapter);
                    break;
                }
                case R.id.expRadio: {
                    adapter = ArrayAdapter.createFromResource(getContext(), R.array.expense_categories, R.layout.support_simple_spinner_dropdown_item);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    dropdown.setAdapter(adapter);
                    break;
                }

            }

        }
    }
}
