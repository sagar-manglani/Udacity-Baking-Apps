package com.udacity.udacitybakingapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.udacity.udacitybakingapps.Fragments.RecipeDetailsList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm=getSupportFragmentManager();
        Fragment frag=fm.findFragmentById(R.id.recipe_list);
        Fragment frag2= new RecipeDetailsList();
        Bundle b = new Bundle();
        b.putString("text","passed");
        frag2.setArguments(b);
        fm.beginTransaction().replace(R.id.recipe_list,frag2).commit();
    }
}
