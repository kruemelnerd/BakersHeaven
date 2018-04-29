package de.kruemelnerd.bakersheaven.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.IngredientsItem;
import timber.log.Timber;

class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private List<IngredientsItem> mIngredientsList = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;

    private WidgetPresenter mPresenter;

    public ListProvider(Context context, Intent intent) {
        this.mContext = context;
        this.mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        populateIngredientsList();
    }

    private void populateIngredientsList(){
        Timber.i("UND ALLE SO YEHA");

        for (int i = 0; i < 10; i++) {
            IngredientsItem item = new IngredientsItem();
            item.setIngredient("blabla " + i);
            item.setMeasure("CUP");
            item.setQuantity(i);
            mIngredientsList.add(item);

        }

//        BakeryRepository repository = Injection.provideBakeryRepository(mContext);
//        mPresenter = new WidgetPresenter(mContext, repository);
//        mPresenter.loadRecipes();
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
        Timber.i("Test 123");
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
