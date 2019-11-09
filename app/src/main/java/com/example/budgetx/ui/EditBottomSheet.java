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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.budgetx.R;
import com.example.budgetx.database.MyDBHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditBottomSheet extends BottomSheetDialogFragment {
    TextView title;
    Button cancel,addBtn,deleteBtn;
    RadioButton inc,exp;
    RadioGroup freqRadio;
    Spinner catSpinner;
    EditText amtText,description;

    String id,type,cat,freq,desc,entryDate,updateDate;
    float amt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_edit_transaction,container,false);
        super.onCreate(savedInstanceState);

        title = v.findViewById(R.id.title);
        catSpinner = v.findViewById(R.id.spinner);
        freqRadio = v.findViewById(R.id.freq);
        amtText = v.findViewById(R.id.amount);
        description = v.findViewById(R.id.description);
        cancel = v.findViewById(R.id.cancel);
        inc = v.findViewById(R.id.incRadio);
        exp = v.findViewById(R.id.expRadio);
        addBtn = v.findViewById(R.id.add);
        deleteBtn = v.findViewById(R.id.delete);

        Clickable();

        if(getArguments().getString("id").length()>0){
            Bundle values = getArguments();
            id = values.getString("id");
            type = values.getString("type");
            cat = values.getString("cat");
            freq = values.getString("freq");
            amt = Float.parseFloat(values.getString("amt"));
            desc = values.getString("desc");
            entryDate =values.getString("entryDate");
            updateDate = values.getString("updateDate");

            SetEditableValues();
        }

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

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MyDBHandler db = new MyDBHandler(getContext(), null);
                db.deleteHandler(Integer.parseInt(id));
                dialog.cancel();
                getActivity().recreate();
            }
        });

        //EditBtn actually
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MyDBHandler db = new MyDBHandler(v.getContext(),null);
                String type,cat, freq,desc;
                float amt;

                type=title.getText().toString();

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

                if(type.length()>0 || cat.length()>0 || freq.length() >0 || amt>0){
                    db.updateHandler(Integer.parseInt(id),type,cat,freq,amt,desc,entryDate,updateDate);
                    dialog.cancel();
                    getActivity().recreate();
                }
                else{
                    Toast toast = Toast.makeText(getContext(),"One of the mandatory field is empty",Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }

    private void SetEditableValues(){
        //setting value of the title
        title.setText(type);

        //set value of spinner
        ArrayAdapter<CharSequence> adapter;
        if(type.contains("Income")){
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.income_categories, R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            catSpinner.setAdapter(adapter);
        }
        else{
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.expense_categories, R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            catSpinner.setAdapter(adapter);
        }
        int spinnerPos = adapter.getPosition(cat);
        catSpinner.setSelection(spinnerPos);

        //set frequency of transaction
        int selectedID;
        if(freq.contains("One Time")){
            selectedID = R.id.oneTime;
        }
        else if(freq.contains("Daily")){
            selectedID= R.id.Daily;
        }
        else if(freq.contains("Weekly")){
            selectedID = R.id.Weekly;
        }
        else{
            selectedID = R.id.Monthly;
        }
        freqRadio.check(selectedID);

        //setting amount
        amtText.setText(String.valueOf(amt));

        //setting description
        description.setText(desc);
    }
}
