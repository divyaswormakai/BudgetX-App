package com.example.budgetx.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.budgetx.R;
import com.example.budgetx.ui.MainBottomSheet;
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

        Button addTrans = root.findViewById(R.id.transaction);
        Button test = root.findViewById(R.id.exp);
        Button add = root.findViewById(R.id.add);

        addTrans.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainBottomSheet mainSheet = new MainBottomSheet();
                mainSheet.show(getFragmentManager(),"BottomSheet");
            }
        });

        test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String temp = homeViewModel.LoadTransactions(v);
                textView.setText(temp);
            }
        });

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                homeViewModel.AddTest(v);
            }
        });

        return root;
    }
}