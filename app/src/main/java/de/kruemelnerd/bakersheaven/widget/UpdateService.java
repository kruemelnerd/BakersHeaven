package de.kruemelnerd.bakersheaven.widget;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import java.util.List;

import de.kruemelnerd.bakersheaven.data.Recipe;
import de.kruemelnerd.bakersheaven.data.source.BakeryRepository;
import de.kruemelnerd.bakersheaven.data.source.Injection;
import timber.log.Timber;

public class UpdateService extends RemoteViewsService implements WidgetView {

    public static final String SHARED_RECIPE_NAME = "shared_recipe_name";
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

        SharedPreferences sharedPreferences = this.getSharedPreferences("widget_bakersheaven_recipe", Context.MODE_PRIVATE);

//        String recipeName = sharedPreferences.getString(UpdateService.SHARED_RECIPE_NAME, null);
//        if (StringUtils.isBlank(recipeName)) {
//            return;
//        }
//        Recipe recipe = null;
//
//        for (Recipe singleRecipe : recipes) {
//            if(recipeName.toLowerCase().equals(singleRecipe.getName().toLowerCase())){
//                recipe = singleRecipe;
//                break;
//            }
//        }
//        if (recipe == null){
//            Toast.makeText(this, "Did you enter the wrong recipename? Please remove the widget and try again.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        String nameRecipe = recipe.getName();
//
//        RemoteViews view = new RemoteViews(getPackageName(), R.layout.recipe_widget_provider);
//        view.setTextViewText(R.id.appwidget_headline, nameRecipe);
//
//        String json = recipe == null ? null : new Gson().toJson(recipe);
//        sharedPreferences.edit().putString(ListProvider.SHARED_WIDGET_RECIPES, json).apply();
//
//
//        ComponentName theWidget = new ComponentName(this, RecipeWidgetProvider.class);
//        AppWidgetManager manager = AppWidgetManager.getInstance(this);
//        manager.updateAppWidget(theWidget, view);
    }


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return (new ListProvider(this.getApplicationContext(), intent));

    }
}

