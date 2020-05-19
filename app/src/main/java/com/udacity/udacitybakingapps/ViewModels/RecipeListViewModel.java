package com.udacity.udacitybakingapps.ViewModels;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.R;
import com.udacity.udacitybakingapps.Utils.FetchData;
import com.udacity.udacitybakingapps.Utils.Loaders;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class RecipeListViewModel extends AndroidViewModel implements LoaderManager.LoaderCallbacks<String> {
    private MutableLiveData<ArrayList<Recipe>> mRecipeList;
    private static String TAG=RecipeListViewModel.class.getSimpleName();
    Context context;
    private static int LOADER_ID=101;
    RecipeListViewModel(Application application){
        super(application);
        context=application;
        loadRecipe();
    }


    private void loadRecipe(){
        getApplication().
                LoaderManager lm =
        if (lm.getLoader(LOADER_ID) == null) {
            lm.initLoader(LOADER_ID, null, this).forceLoad();
        } else
            lm.restartLoader(LOADER_ID, null, this).forceLoad();

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        try {
            return new Loaders(new URL(context.getString(R.string.recipe_url)),context);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.d(TAG,data);
        mRecipeList.setValue(FetchData.getRecipeList(data));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}


