package com.example.budgetx.ui.home;

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

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.budgetx.R;
import com.example.budgetx.database.MyDBHandler;
import com.example.budgetx.ui.EditBottomSheet;
import com.example.budgetx.ui.MainBottomSheet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class HomeFragment extends BottomSheetDialogFragment {

    private static Boolean didAutomate = false;

    private ScrollView scroller;
    private LinearLayout linearLayout;
    static private HomeViewModel homeViewModel;
    private LayoutInflater inflator;
    private View root;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        inflator = inflater;

        scroller = root.findViewById(R.id.scrollView);
        linearLayout = root.findViewById(R.id.linearlayout);
        Button addTrans = root.findViewById(R.id.transaction);

        addTrans.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainBottomSheet mainSheet = new MainBottomSheet();
                mainSheet.show(getFragmentManager(),"BottomSheet");
            }
        });

        //Automate all frequent transactions
        if(!didAutomate) {
            homeViewModel.AutomateTransactions(root);
            didAutomate =true;
        }
        //Load all transactions
        List transactions = homeViewModel.LoadTransactions(root);

        for (Object tempObj  : transactions) {
            String tempString =tempObj.toString();
            final String[] components = tempString.split(",");

            LinearLayout ll = new LinearLayout(getContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);

            TextView tempt =  new TextView(getContext());
            tempt.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
            tempt.setGravity(Gravity.CENTER_HORIZONTAL);
            String finalStr="";
            for(String temp : components){
                temp = temp.replaceAll(",","");
                finalStr+=temp+" ";
            }
            tempt.setText(finalStr);

            //If the transaction is calculated transaction then do not make it editable
            if(!components[3].contains("Automated")){
                Button editBtn = new Button(getContext());
                editBtn.setText("Edit");
                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditBottomSheet editSheet = new EditBottomSheet();
                        Bundle args = new Bundle();
                        args.putString("id", components[0]);
                        args.putString("type", components[1]);
                        args.putString("cat", components[2]);
                        args.putString("freq", components[3]);
                        args.putString("amt", components[4]);
                        args.putString("desc", components[5]);
                        args.putString("entryDate", components[6]);
                        editSheet.setArguments(args);

                        editSheet.show(getFragmentManager(), "BottomSheet");
                    }
                });

                ll.addView(editBtn);
            }

            Button deleteBtn = new Button(getContext());
            deleteBtn.setText("Delete");
            deleteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    MyDBHandler db = new MyDBHandler(getContext(), null);
                    db.deleteHandler(Integer.parseInt(components[0]));
                    getActivity().recreate();
                }
            });
            ll.addView(deleteBtn);
            ll.addView(tempt);
            linearLayout.addView(ll);
        }

        return root;
    }
}