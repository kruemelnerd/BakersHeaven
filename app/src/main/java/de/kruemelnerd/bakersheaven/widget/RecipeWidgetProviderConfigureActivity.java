package de.kruemelnerd.bakersheaven.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.lang.StringUtils;

import java.util.List;

import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.Recipe;
import de.kruemelnerd.bakersheaven.data.source.BakeryRepository;
import de.kruemelnerd.bakersheaven.data.source.Injection;
import timber.log.Timber;

/**
 * The configuration screen for the {@link RecipeWidgetProvider RecipeWidgetProvider} AppWidget.
 */
public class RecipeWidgetProviderConfigureActivity extends Activity implements WidgetView {

    private WidgetPresenter mPresenter;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText;

    static List<Recipe> mRecipes;

    public RecipeWidgetProviderConfigureActivity() {
        super();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = RecipeWidgetProviderConfigureActivity.this;

            // When the button is clicked, store the string locally
            String recipeNameText = mAppWidgetText.getText().toString();
            saveRecipeNamePref(context, mAppWidgetId, recipeNameText);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews = RecipeWidgetProvider.updateAppWidget(context, mAppWidgetId);
            appWidgetManager.updateAppWidget(mAppWidgetId, remoteViews);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };


    // Write the prefix to the SharedPreferences object for this widget
    static void saveRecipeNamePref(Context context, int appWidgetId, String text) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("widget_bakersheaven_recipe", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(ListProvider.SHARED_RECIPE_NAME, text).apply();

            if (StringUtils.isBlank(text)) {
                Toast.makeText(context, "Did you enter the wrong recipename? Please remove the widget and try again.", Toast.LENGTH_SHORT).show();
                return;
            }
            Recipe recipe = null;

            for (Recipe singleRecipe : mRecipes) {
                if (text.toLowerCase().equals(singleRecipe.getName().toLowerCase())) {
                    recipe = singleRecipe;
                    break;
                }
            }
            if (recipe == null) {
                Toast.makeText(context, "Did you enter the wrong recipename? Please remove the widget and try again.", Toast.LENGTH_SHORT).show();
                return;
            }


            String json = recipe == null ? null : new Gson().toJson(recipe);
            sharedPreferences.edit().putString(ListProvider.SHARED_WIDGET_RECIPES, json).apply();
        } else {
            Timber.e("Something went wrong with the sharedPreferences?");
        }
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.recipe_widget_provider_configure);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        mAppWidgetText = findViewById(R.id.appwidget_text);


        BakeryRepository repository = Injection.provideBakeryRepository(this);
        mPresenter = new WidgetPresenter(this, repository);
        mPresenter.loadRecipes();


        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
    }


    public void showRecipes(List<Recipe> recipes) {
        Timber.d("Recipe loaded: " + recipes.toString() + " Number of Recipes loaded: " + recipes.size());
        if (recipes.size() > 0) {
            // For helping a little
            mAppWidgetText.setText(recipes.get(0).getName());
        }
        mRecipes = recipes;

    }
}

