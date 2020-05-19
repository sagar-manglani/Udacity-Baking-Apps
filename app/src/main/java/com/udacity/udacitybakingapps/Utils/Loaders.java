package com.udacity.udacitybakingapps.Utils;

import android.content.Context;

import java.io.IOException;
import java.net.URL;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class Loaders extends AsyncTaskLoader<String> {
    URL receipeURL;

    public Loaders(URL url, Context context){
        super(context);
        receipeURL=url;

    }

    @Nullable
    @Override
    public String loadInBackground() {
        try {
            String jsonData = FetchData.getResponseFromHttpUrl(receipeURL);
            return jsonData;

        }catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(@Nullable String data) {
        super.deliverResult(data);
    }
}
