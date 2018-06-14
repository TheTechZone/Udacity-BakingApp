package com.example.adrian.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.adrian.bakingapp.data.model.Ingredient;

import org.parceler.Parcels;

import java.util.List;

public class WidgetUpdateService extends IntentService{

    static String UPDATE_ACTION = "android.appwidget.action.APPWIDGET_DATA_UPDATE";
    static String KEY_INGREDIENTS = "listIngredients";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * name Used to name the worker thread, important only for debugging.
     */
    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    public static void startService(Context context, List<Ingredient> ingredients){
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.putExtra(KEY_INGREDIENTS, Parcels.wrap(ingredients));
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null){
            List<Ingredient> ingredientList = Parcels.unwrap(intent.getExtras().getParcelable(KEY_INGREDIENTS));
            passIntent(ingredientList);
        }
    }

    private void passIntent(List<Ingredient> list){
        Intent intent = new Intent(UPDATE_ACTION);
        intent.setAction(UPDATE_ACTION);
        intent.putExtra(KEY_INGREDIENTS, Parcels.wrap(list));
        sendBroadcast(intent);
    }
}
