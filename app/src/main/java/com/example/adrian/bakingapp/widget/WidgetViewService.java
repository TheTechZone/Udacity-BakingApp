package com.example.adrian.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.adrian.bakingapp.R;
import com.example.adrian.bakingapp.data.model.Ingredient;

import java.util.List;
import static com.example.adrian.bakingapp.widget.IngredientsWidgetProvider.ingredients;

public class WidgetViewService extends RemoteViewsService{

    List<Ingredient> ingredientList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        public Context context = null;

        public WidgetRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            ingredientList = ingredients;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            android.os.Debug.waitForDebugger();
            views.setTextViewText(R.id.widget_item, ingredientList.get(position).getListing(context));
            Intent fillInIntent = new Intent();
//            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
