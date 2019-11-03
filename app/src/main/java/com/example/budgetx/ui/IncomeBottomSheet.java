package com.example.budgetx.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.budgetx.R;
import com.example.budgetx.database.MyDBHandler;
import com.example.budgetx.database.Transaction;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IncomeBottomSheet extends BottomSheetDialogFragment {

    View v;
    Spinner catSpinner;
    EditText amtText,description;
    RadioGroup freqRadio;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dialog_income,container,false);
        Spinner dropdown = v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.income_categories,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        Button cancel = v.findViewById(R.id.cancel);
        Button addBtn = v.findViewById(R.id.add);
        catSpinner = v.findViewById(R.id.spinner);
        freqRadio = v.findViewById(R.id.freq);
        amtText = v.findViewById(R.id.amount);
        description = v.findViewById(R.id.description);
        final Dialog dialog = getDialog();

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AddIncome(v);
            }
        });
        return v;
    }

    void AddIncome(View v){
        Dialog dialog = getDialog();
        MyDBHandler db = new MyDBHandler(v.getContext(),null);
        String type,cat, freq,desc;
        float amt;
        type="Income";
        cat = catSpinner.getSelectedItem().toString();
        switch(freqRadio.getCheckedRadioButtonId()){
            case R.id.Daily:{
                freq = "Daily";
                break;
            }
            case R.id.Weekly:{
                freq = "Weekly";
                break;
            }
            case R.id.Monthly:{
                freq = "Monthly";
                break;
            }
            default:{
                freq = "One Time";
                break;
            }
        }
        amt = Float.parseFloat(amtText.getText().toString());
        desc = description.getText().toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = simpleDateFormat.format(c);
        if(cat.length()>0 || freq.length() >0 || amt>0){
            Transaction trans = new Transaction(type,cat,freq,amt,desc,formattedDate);
            db.addHandler(trans);
            dialog.cancel();
        }
        else{
            Toast toast = Toast.makeText(getContext(),"One of the mandatory field is empty",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
