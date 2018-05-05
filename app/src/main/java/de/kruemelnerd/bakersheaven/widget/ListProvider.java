package de.kruemelnerd.bakersheaven.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.IngredientsItem;
import de.kruemelnerd.bakersheaven.data.Recipe;

class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    public static final String SHARED_WIDGET_RECIPES = "shared_recipes";
    private List<IngredientsItem> mIngredientsList = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;

    private WidgetPresenter mPresenter;

    public ListProvider(Context context, Intent intent) {
        this.mContext = context;
        this.mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        populateIngredientsList();
    }

    private void populateIngredientsList() {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("widget_bakersheaven_recipe", Context.MODE_PRIVATE);

        if(sharedPreferences != null){
            String json = sharedPreferences.getString(SHARED_WIDGET_RECIPES, null);
            Recipe recipe = json == null ? null : new Gson().fromJson(json, Recipe.class);

            if(recipe != null){
                mIngredientsList = recipe.getIngredients();
            }
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_row);

        IngredientsItem item = mIngredientsList.get(position);
        remoteView.setTextViewText(R.id.widget_ingredient_quantity, String.valueOf(item.getQuantity()));
        remoteView.setTextViewText(R.id.widget_ingredient_measure, item.getMeasure());
        remoteView.setTextViewText(R.id.widget_ingredient_description, item.getIngredient());

        return remoteView;
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
