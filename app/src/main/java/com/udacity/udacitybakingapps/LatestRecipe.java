package com.udacity.udacitybakingapps;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Data.WidgetData;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class LatestRecipe extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.latest_recipe);
        SharedPreferences prefs =  context.getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String widget_string= prefs.getString("widget","");
        Gson gson = new Gson();
        WidgetData widgetData=gson.fromJson(widget_string,WidgetData.class);
        Recipe recipe= widgetData.getRecipe();
        int position= widgetData.getPosition();
        String step_description="";
        if(position==0){
            step_description="See all ingredients";
        }else
            step_description=recipe.getSteps().get(position-1).getShortDescription();

        views.setTextViewText(R.id.recipe_name, recipe.getName());
        views.setTextViewText(R.id.step_name, step_description);
        Intent intent = new Intent(context,RecipeDetail.class);
        intent.putExtra("widget",true);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_layout,pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

    }

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }


    }



    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

