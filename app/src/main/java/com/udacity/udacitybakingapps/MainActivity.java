package com.udacity.udacitybakingapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Fragments.RecipeFragmentList;
import com.udacity.udacitybakingapps.Interface.FragmentToActivityListener;
import com.udacity.udacitybakingapps.Utils.FetchData;
import com.udacity.udacitybakingapps.Utils.Loaders;
import com.udacity.udacitybakingapps.ViewModels.RecipeListViewModel;

import org.parceler.Parcels;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, FragmentToActivityListener {
    RecipeListViewModel viewmodel;

    private static String TAG=MainActivity.class.getSimpleName();
    private static int LOADER_ID=101;
    LoaderManager manager;
    ArrayList<Recipe> mRecipeList;
    FragmentManager frag_manager;
    RecipeFragmentList recipe_frag;
    private static String RECIPE_FRAGMENT_TAG="recipeList";
    Boolean isTablet=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager= LoaderManager.getInstance(this);
        viewmodel= new ViewModelProvider(this).get(RecipeListViewModel.class);

        if(viewmodel.getmRecipeList()==null){
            loadRecipe();
            Log.d(TAG,"Load recipes");
        }else{
            mRecipeList=viewmodel.getmRecipeList();
            Log.d(TAG,"Load recipes from view model");
            setUpFragment(false);
        }

    }

    private void setUpFragment(Boolean isFirstTime){
        frag_manager= getSupportFragmentManager();
        if(isFirstTime) {
            Log.d(TAG,"Inside first time");
            recipe_frag = new RecipeFragmentList();
            recipe_frag.setRetainInstance(true);
            frag_manager.beginTransaction().add(R.id.recipe_list_container, recipe_frag, RECIPE_FRAGMENT_TAG).commit();
        }else{
            Log.d(TAG,"Inside second time");
            recipe_frag=(RecipeFragmentList) frag_manager.findFragmentByTag(RECIPE_FRAGMENT_TAG);
        }
        recipe_frag.sendData(mRecipeList);
        Log.d(TAG,"inside SetUp "+mRecipeList.get(0).getName());

    }

    private void loadRecipe(){

        if (manager.getLoader(LOADER_ID) == null) {
            manager.initLoader(LOADER_ID, null, this).forceLoad();
        } else
            manager.restartLoader(LOADER_ID, null, this).forceLoad();

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        try {
            return new Loaders(new URL(getString(R.string.recipe_url)),this);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        //Log.d(TAG,data);
        mRecipeList=FetchData.getRecipeList(data);
        viewmodel.setmRecipeList(mRecipeList);
        Log.d(TAG,"The size is "+mRecipeList.size());
        setUpFragment(true);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
    public void loadDetailActivity(int position){
        Intent intent = new Intent(MainActivity.this,RecipeDetail.class);
        intent.putExtra("recipe", Parcels.wrap(mRecipeList.get(position)));
        //intent.putExtra("recipe",mRecipeList.get(position));
        String ingredient_text_row="";
        Recipe recipe=mRecipeList.get(position);
        Log.d(TAG,""+recipe.getIngredients().size());
        /*for(int i=0; i<recipe.getIngredients().size();i++){
            ingredient_text_row =ingredient_text_row +  recipe.getIngredients().get(i).getIngredient()+"  -"+recipe.getIngredients().get(i).getQuantity()+"  "+recipe.getIngredients().get(i).getMeasure();
            ingredient_text_row=ingredient_text_row + System.getProperty("line.separator");
        }
        Log.d(TAG,ingredient_text_row);*/
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
    }

    @Override
    public void sendDataToActivity(int position) {
        Log.d(TAG,"clicked on in activity "+position);
        loadDetailActivity(position);
    }
}
