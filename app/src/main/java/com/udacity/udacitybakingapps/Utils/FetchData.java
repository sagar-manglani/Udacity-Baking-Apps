package com.udacity.udacitybakingapps.Utils;

import com.udacity.udacitybakingapps.Data.Ingredients;
import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Data.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class FetchData {
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<Recipe> getRecipeList(String data){
        try {
            ArrayList<Recipe> recipeList=new ArrayList<Recipe>();
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Recipe recipe=new Recipe();
                if(jsonObject.getString("name")!=null && !jsonObject.getString("name").isEmpty())
                    recipe.setName(jsonObject.getString("name"));
                else
                    recipe.setName("N/A");
                if(jsonObject.getString("ingredients")!=null && !jsonObject.getString("ingredients").isEmpty()){
                    ArrayList<Ingredients> ingredients=new ArrayList<Ingredients>();
                    JSONArray ingredientsArray=jsonObject.getJSONArray("ingredients");
                    for(int j=0;j<ingredientsArray.length();j++){
                        JSONObject ingredientsObject=ingredientsArray.getJSONObject(i);
                        Ingredients ingredient= new Ingredients();
                        if(ingredientsObject.getString("quantity")!=null && !ingredientsObject.getString("quantity").isEmpty())
                            ingredient.setQuantity(ingredientsObject.getString("quantity"));
                        if(ingredientsObject.getString("measure")!=null && !ingredientsObject.getString("measure").isEmpty())
                            ingredient.setMeasure(ingredientsObject.getString("measure"));
                        if(ingredientsObject.getString("ingredient")!=null && !ingredientsObject.getString("ingredient").isEmpty())
                            ingredient.setIngredient(ingredientsObject.getString("ingredient"));
                        ingredients.add(ingredient);
                    }
                    recipe.setIngredients(ingredients);
                }
                if(jsonObject.getString("steps")!=null && !jsonObject.getString("steps").isEmpty()) {
                    ArrayList<Steps> steps = new ArrayList<Steps>();
                    JSONArray stepsArray = jsonObject.getJSONArray("steps");
                    for (int j = 0; j < stepsArray.length(); j++) {
                        JSONObject stepsObject = stepsArray.getJSONObject(i);
                        Steps step = new Steps();
                        if (stepsObject.getString("shortDescription") != null && !stepsObject.getString("shortDescription").isEmpty())
                            step.setShortDescription(stepsObject.getString("shortDescription"));
                        if (stepsObject.getString("description") != null && !stepsObject.getString("description").isEmpty())
                            step.setDescription(stepsObject.getString("description"));
                        if (stepsObject.getString("videoURL") != null && !stepsObject.getString("videoURL").isEmpty())
                            step.setVideoURL(stepsObject.getString("videoURL"));
                        else
                            step.setVideoURL("N/A");
                        if (stepsObject.getString("thumbnailURL") != null && !stepsObject.getString("thumbnailURL").isEmpty())
                            step.setImageURL(stepsObject.getString("thumbnailURL"));
                        else
                            step.setImageURL("N/A");
                        steps.add(step);
                    }
                    recipe.setSteps(steps);
                }
                if(jsonObject.getString("servings")!=null && !jsonObject.getString("servings").isEmpty())
                    recipe.setServings(jsonObject.getString("servings"));
                else
                    recipe.setServings("N/A");
                if(jsonObject.getString("image")!=null && !jsonObject.getString("image").isEmpty())
                    recipe.setImage_url(jsonObject.getString("image"));
                else
                    recipe.setImage_url("N/A");
                recipeList.add(recipe);

            }

            return recipeList;
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        return null;

    }
}
