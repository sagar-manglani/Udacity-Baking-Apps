package com.udacity.udacitybakingapps;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class testingfragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.testingfragment,container,false);
        String text="changed";

        if(getArguments()!=null) {
            Log.d("testing",getArguments().getString("text"));
            text = getArguments().getString("text");
        }else
            Log.d("testing","I am null");
        //TextView tv=view.findViewById(R.id.testing);
        //tv.setText(text);
        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
