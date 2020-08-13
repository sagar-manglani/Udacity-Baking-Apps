package com.udacity.udacitybakingapps.ViewModels;

import android.app.Application;
import com.udacity.udacitybakingapps.Data.Recipe;
import java.util.ArrayList;
import androidx.lifecycle.AndroidViewModel;


public class RecipeListViewModel extends AndroidViewModel {
    private ArrayList<Recipe> mRecipeList;
    //private static String TAG=RecipeListViewModel.class.getSimpleName();

    public RecipeListViewModel(Application application) {
        super(application);
    }

    public ArrayList<Recipe> getmRecipeList(){
        return mRecipeList;
    }
    public void setmRecipeList(ArrayList<Recipe> list){
        mRecipeList=list;
    }





}


