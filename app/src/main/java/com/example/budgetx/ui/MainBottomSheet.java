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
import android.widget.RadioButton;
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

public class MainBottomSheet extends BottomSheetDialogFragment {
    Button cancel,addBtn,setDate;
    RadioButton inc,exp;
    RadioGroup radioGroup,freqRadio;
    Spinner catSpinner;
    EditText amtText,description,entryDate;
    LocalDate today;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_main,container,false);
        super.onCreate(savedInstanceState);
        radioGroup = v.findViewById(R.id.type);
        catSpinner = v.findViewById(R.id.spinner);
        freqRadio = v.findViewById(R.id.freq);
        amtText = v.findViewById(R.id.amount);
        description = v.findViewById(R.id.description);
        cancel = v.findViewById(R.id.cancel);
        inc = v.findViewById(R.id.incRadio);
        exp = v.findViewById(R.id.expRadio);
        addBtn = v.findViewById(R.id.add);
        setDate = v.findViewById(R.id.setDate);
        entryDate = v.findViewById(R.id.entryDate);

        Clickable();

        //set Spinner default value
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.income_categories, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        catSpinner.setAdapter(adapter);

        //set Date default value
        today = LocalDate.now();
        DateTimeFormatter simpleDateFormat =DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String todayDate = today.format(simpleDateFormat);
        entryDate.setText(todayDate);

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

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog test = new DatePickerDialog(v.getContext(),myDateListener,today.getYear(),today.getMonthValue()-1,today.getDayOfMonth());
                test.show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MyDBHandler db = new MyDBHandler(v.getContext(),null);
                String type,cat, freq,desc;
                float amt;

                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(selectedId == inc.getId()){
                    type="Income";
                }
                else if(selectedId == exp.getId()){
                    type="Expense";
                }
                else{
                    type="";
                }
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
                //get entryDate from the entryDate text field later
                if(type.length()>0 || cat.length()>0 || freq.length() >0 || amt>0){
//                    Transaction trans = new Transaction(type,cat,freq,amt,desc,entryDate,formattedDate);
                    Transaction trans = new Transaction(type,cat,freq,amt,desc,formattedDate,formattedDate);
                    db.addHandler(trans);
                    dialog.cancel();
                }
                else{
                    Toast toast = Toast.makeText(getContext(),"One of the mandatory field is empty",Toast.LENGTH_SHORT);
                    toast.show();
                }

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
                    catSpinner.setAdapter(adapter);
                    break;
                }
                case R.id.expRadio: {
                    adapter = ArrayAdapter.createFromResource(getContext(), R.array.expense_categories, R.layout.support_simple_spinner_dropdown_item);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    catSpinner.setAdapter(adapter);
                    break;
                }
            }
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
