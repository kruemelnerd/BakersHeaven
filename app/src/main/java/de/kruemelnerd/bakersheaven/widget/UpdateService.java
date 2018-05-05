package de.kruemelnerd.bakersheaven.widget;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.Recipe;
import de.kruemelnerd.bakersheaven.data.source.BakeryRepository;
import de.kruemelnerd.bakersheaven.data.source.Injection;
import timber.log.Timber;

public class UpdateService extends RemoteViewsService implements WidgetView {

    private WidgetPresenter mPresenter;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        BakeryRepository repository = Injection.provideBakeryRepository(this);
        mPresenter = new WidgetPresenter(this, repository);
        mPresenter.loadRecipes();


        return super.onStartCommand(intent, flags, startId);
    }

    public void showRecipes(List<Recipe> recipes) {

        Timber.i("Service Recipe loaded: " + recipes.toString());
        Toast.makeText(this, recipes.size() + " Recipes loaded in Service ", Toast.LENGTH_SHORT).show();

        Recipe recipe = recipes.get(0);
        String nameRecipe = recipe.getName();

        RemoteViews view = new RemoteViews(getPackageName(), R.layout.recipe_widget_provider);
        view.setTextViewText(R.id.appwidget_headline, nameRecipe);

        SharedPreferences sharedPreferences = this.getSharedPreferences("widget_bakersheaven", Context.MODE_PRIVATE);
        String json = recipe == null ? null : new Gson().toJson(recipe);
        sharedPreferences.edit().putString(ListProvider.SHARED_WIDGET_RECIPES, json).apply();


        ComponentName theWidget = new ComponentName(this, RecipeWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(theWidget, view);
    }


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return (new ListProvider(this.getApplicationContext(), intent));

    }
}

