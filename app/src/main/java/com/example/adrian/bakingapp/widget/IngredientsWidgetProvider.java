package com.example.adrian.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.adrian.bakingapp.R;
import com.example.adrian.bakingapp.StepListActivity;
import com.example.adrian.bakingapp.data.model.Ingredient;
import com.google.gson.Gson;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static List<Ingredient> ingredients = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_listview);

        Intent appIntent = new Intent(context, StepListActivity.class);
        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_listview, pendingIntent);

        Intent adapterIntent = new Intent(context, WidgetViewService.class);

        updatePref(context);


        views.setRemoteAdapter(R.id.widget_listview, adapterIntent);

        Log.e("service", "Shared pref changed");
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static private void updatePref(Context context){
        Gson gson = new Gson();
        String jsonData = gson.toJson(ingredients);
        SharedPreferences prefs = context.getSharedPreferences(WidgetUpdateService.KEY_INGREDIENTS,
                Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = prefs.edit();
        if(prefs.contains(WidgetUpdateService.KEY_INGREDIENTS)) {
            editor.remove(WidgetUpdateService.KEY_INGREDIENTS);
            Log.e("service", "Shared pref removed");
        }
        editor.putString(WidgetUpdateService.KEY_INGREDIENTS, jsonData);
        editor.apply();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        updateWidgets(context,appWidgetManager, appWidgetIds);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientsWidgetProvider.class));

        final String action = intent.getAction();

        if (action.equals(WidgetUpdateService.UPDATE_ACTION)){
            ingredients = Parcels.unwrap(intent.getExtras().getParcelable(WidgetUpdateService.KEY_INGREDIENTS));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview);

            IngredientsWidgetProvider.updateWidgets(context,appWidgetManager,appWidgetIds);
            super.onReceive(context, intent);
        }
    }
}

