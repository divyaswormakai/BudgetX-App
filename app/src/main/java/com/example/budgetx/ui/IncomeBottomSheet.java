package com.example.budgetx.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IncomeBottomSheet extends BottomSheetDialogFragment {
    View v;
    Spinner catSpinner;
    EditText amtText,description,entryDate;
    RadioGroup freqRadio;
    Button setDate;
    LocalDate today;

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
        setDate = v.findViewById(R.id.setDate);
        entryDate = v.findViewById(R.id.entryDate);
        final Dialog dialog = getDialog();

        //set Date default value
        today = LocalDate.now();
        DateTimeFormatter simpleDateFormat =DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String todayDate = today.format(simpleDateFormat);
        entryDate.setText(todayDate);

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

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog test = new DatePickerDialog(v.getContext(),myDateListener,today.getYear(),today.getMonthValue()-1,today.getDayOfMonth());
                test.show();
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
        String formattedDate = entryDate.getText().toString();

        if(cat.length()>0 || freq.length() >0 || amt>0){
            Transaction trans = new Transaction(type,cat,freq,amt,desc,formattedDate,formattedDate);
            db.addHandler(trans);
            dialog.cancel();
        }
        else{
            Toast toast = Toast.makeText(getContext(),"One of the mandatory field is empty",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year,arg2 = month,arg3 = day
            String arg2String =String.valueOf(arg2+1);
            String arg3String =String.valueOf(arg3);
            if(arg2+1 <10){
                arg2String ="0"+arg2String;
            }
            if(arg3 <10){
                arg3String ="0"+arg3String;
            }
            entryDate.setText(new StringBuilder().append(arg1).append("/").append(arg2String).append("/").append(arg3String));
        }
    };


}
