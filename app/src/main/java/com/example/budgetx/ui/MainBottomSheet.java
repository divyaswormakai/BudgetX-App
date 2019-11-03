package com.example.budgetx.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainBottomSheet extends BottomSheetDialogFragment {
    Button cancel,addBtn;
    Spinner dropdown;
    RadioButton inc,exp;
    RadioGroup radioGroup,freqRadio;
    Spinner catSpinner;
    EditText amtText,description;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_main,container,false);

        radioGroup = v.findViewById(R.id.type);
        catSpinner = v.findViewById(R.id.spinner);
        freqRadio = v.findViewById(R.id.freq);
        amtText = v.findViewById(R.id.amount);
        description = v.findViewById(R.id.description);
        cancel = v.findViewById(R.id.cancel);
        inc = v.findViewById(R.id.incRadio);
        exp = v.findViewById(R.id.expRadio);
        addBtn = v.findViewById(R.id.add);

        Clickable();

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.income_categories, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        catSpinner.setAdapter(adapter);

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
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = simpleDateFormat.format(c);
                if(type.length()>0 || cat.length()>0 || freq.length() >0 || amt>0){
                    Transaction trans = new Transaction(type,cat,freq,amt,desc,formattedDate);
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
