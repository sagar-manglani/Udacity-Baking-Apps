package com.udacity.udacitybakingapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Fragments.RecipeDetailsFragmentsList;
import com.udacity.udacitybakingapps.Fragments.StepDetailsFragment;
import com.udacity.udacitybakingapps.Interface.FragmentToActivityListener;
import com.udacity.udacitybakingapps.Utils.FetchData;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

public class RecipeDetail extends AppCompatActivity implements FragmentToActivityListener {

    private static String RECIPE_DETAIL_FRAGMENT_TAG="detail_fragment";
    private static String STEP_DETAIL_FRAGMENT_TAG="step_fragment";
    Recipe recipe;
    private static String TAG=RecipeDetail.class.getSimpleName();
    RecipeDetailsFragmentsList frag_details;
    StepDetailsFragment details;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recipe);
        Log.d(TAG,"Inside recipe detail");
        if(savedInstanceState==null) {
            frag_details = new RecipeDetailsFragmentsList(this);
            frag_details.setRetainInstance(true);
            getSupportFragmentManager().beginTransaction().add(R.id.recipe_detail_list_container,frag_details,RECIPE_DETAIL_FRAGMENT_TAG).commit();
            ArrayList<Recipe> recipelist=new ArrayList<Recipe>();

            Intent intent= getIntent();

            if(intent.getExtras()!=null){
                Log.d(TAG,"inside if");
                recipe=(Recipe) intent.getParcelableExtra("recipe");
                Log.d(TAG,"Inside recipe detail if "+recipe.getName());
                Log.d(TAG,recipe.getName());
                String ingredient_text_row="";
                recipelist.add(recipe);
            }
               frag_details.sendData(recipelist);
        }else{
             frag_details=(RecipeDetailsFragmentsList)getSupportFragmentManager().findFragmentByTag(RECIPE_DETAIL_FRAGMENT_TAG);

        }


        //frag_manager.beginTransaction().add(R.id.recipe_detail_list_container,frag_details,RECIPE_DETAIL_FRAGMENT_TAG).commit();

    }


    @Override
    public void sendDataToActivity(int position) {

        Log.d(TAG,"Inside send Data to Activity");
        /*if(getSupportFragmentManager().isDestroyed()){
            frag_manager=getSupportFragmentManager();
        }*/
        if(getSupportFragmentManager().findFragmentByTag(STEP_DETAIL_FRAGMENT_TAG)==null){
            details= new StepDetailsFragment();
            details.setRetainInstance(true);
            Bundle args=new Bundle();
            ArrayList<ArrayList<String>> recipe_in_list= FetchData.fetchStepsArrayList(recipe);
            args.putString("ingredients",FetchData.fetchIngredientsString(recipe));
            args.putStringArrayList("name",recipe_in_list.get(0));
            args.putStringArrayList("desc",recipe_in_list.get(1));
            args.putStringArrayList("videoURL",recipe_in_list.get(2));
            args.putInt("position",position);
            details.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_detail_list_container,details,STEP_DETAIL_FRAGMENT_TAG).addToBackStack(null).commit();
        }else
            details=(StepDetailsFragment) getSupportFragmentManager().findFragmentByTag(STEP_DETAIL_FRAGMENT_TAG);


    }
}
