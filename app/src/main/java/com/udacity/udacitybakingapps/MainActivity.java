package com.udacity.udacitybakingapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm=getSupportFragmentManager();
        Fragment frag=fm.findFragmentById(R.id.recipe_list);
        Bundle b = new Bundle();
        b.putString("text","passed");
        frag.setArguments(b);
    }
}
