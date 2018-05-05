package de.kruemelnerd.bakersheaven.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import org.apache.commons.lang.StringUtils;

import de.kruemelnerd.bakersheaven.R;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link RecipeWidgetProviderConfigureActivity RecipeWidgetProviderConfigureActivity}
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static RemoteViews updateAppWidget(Context context, int appWidgetId) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("widget_bakersheaven_recipe", Context.MODE_PRIVATE);
        String recipeName = sharedPreferences.getString(ListProvider.SHARED_RECIPE_NAME, null);


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(appWidgetId, R.id.appwidget_ingredients, intent);
        views.setEmptyView(R.id.appwidget_ingredients, R.id.appwidget_picture);

        if (StringUtils.isNotBlank(recipeName)) {
            views.setTextViewText(R.id.appwidget_headline, recipeName);
        }

        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = updateAppWidget(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
        Timber.i("UpdatingWidget: onUpdate");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Timber.i("Updating Widget: onReceive");

        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            RemoteViews remoteViews = updateAppWidget(context, appWidgetId);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

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

