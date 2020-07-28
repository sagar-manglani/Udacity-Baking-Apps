package com.udacity.udacitybakingapps;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;

import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Fragments.RecipeDetailsFragmentsList;
import com.udacity.udacitybakingapps.Fragments.StepDetailsFragment;
import com.udacity.udacitybakingapps.Interface.FragmentToActivityListener;
import com.udacity.udacitybakingapps.Utils.FetchData;

import org.parceler.Parcels;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class RecipeDetail extends AppCompatActivity implements FragmentToActivityListener {

    private static String RECIPE_DETAIL_FRAGMENT_TAG="detail_fragment";
    private static String STEP_DETAIL_FRAGMENT_TAG="step_fragment";
    Recipe recipe;
    private static String TAG=RecipeDetail.class.getSimpleName();
    RecipeDetailsFragmentsList frag_details;
    StepDetailsFragment details;
    ActionBar actionBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recipe);
        Log.d(TAG,"Inside recipe detail");
        actionBar=getSupportActionBar();

        if(savedInstanceState==null) {
            frag_details = new RecipeDetailsFragmentsList(this);
            frag_details.setRetainInstance(true);
            getSupportFragmentManager().beginTransaction().add(R.id.recipe_detail_list_container,frag_details,RECIPE_DETAIL_FRAGMENT_TAG).addToBackStack(null).commit();
            ArrayList<Recipe> recipelist=new ArrayList<Recipe>();
            Bundle b=new Bundle();
            b.putString("test","testing");
            frag_details.setArguments(b);
            Intent intent= getIntent();

            if(intent.getExtras()!=null){
                Log.d(TAG,"inside if");
                recipe=(Recipe) Parcels.unwrap(intent.getParcelableExtra("recipe"));
                Log.d(TAG,"Inside recipe detail if "+recipe.getName());
                Log.d(TAG,recipe.getName());
                actionBar.setTitle(recipe.getName());
                String ingredient_text_row="";
                recipelist.add(recipe);
            }
               frag_details.sendData(recipelist);
        }else{
            recipe= Parcels.unwrap(savedInstanceState.getParcelable("recipe"));

          //   frag_details=(RecipeDetailsFragmentsList)getSupportFragmentManager().findFragmentByTag(RECIPE_DETAIL_FRAGMENT_TAG);

        }




        //frag_manager.beginTransaction().add(R.id.recipe_detail_list_container,frag_details,RECIPE_DETAIL_FRAGMENT_TAG).commit();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recipe",Parcels.wrap(recipe));
    }

    @Override
    public void sendDataToActivity(int position) {

        Log.d(TAG,"Inside send Data to Activity");
        /*if(getSupportFragmentManager().isDestroyed()){
            frag_manager=getSupportFragmentManager();
        }*/
        Bundle args=new Bundle();
        ArrayList<ArrayList<String>> recipe_in_list= FetchData.fetchStepsArrayList(recipe);
        args.putString("ingredients",FetchData.fetchIngredientsString(recipe,getApplicationContext()));
        args.putStringArrayList("name",recipe_in_list.get(0));
        args.putStringArrayList("desc",recipe_in_list.get(1));
        args.putStringArrayList("videoURL",recipe_in_list.get(2));
        args.putStringArrayList("imageURL",recipe_in_list.get(3));

        args.putInt("position",position);
        if(getSupportFragmentManager().findFragmentByTag(STEP_DETAIL_FRAGMENT_TAG)==null){
            details= new StepDetailsFragment();
            details.setRetainInstance(true);
            details.setArguments(args);
            }
        else{
            details=(StepDetailsFragment) getSupportFragmentManager().findFragmentByTag(STEP_DETAIL_FRAGMENT_TAG);
            details.setData(args);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_out_bottom, R.anim.slide_in_bottom);

        if(getResources().getBoolean(R.bool.isTablet)){
            ft.replace(R.id.step_detail_list_container,details,STEP_DETAIL_FRAGMENT_TAG).commit();
        }else {
            ft.replace(R.id.recipe_detail_list_container, details, STEP_DETAIL_FRAGMENT_TAG).addToBackStack(null).commit();
        }


    }

    @Override
    public void onBackPressed() {
        int fragment_count=getSupportFragmentManager().getBackStackEntryCount();
        if(fragment_count>1)
            super.onBackPressed();
        else if(fragment_count==1)
            finish();
        Log.d(TAG,"count is "+fragment_count);
    }

}
