package de.kruemelnerd.bakersheaven.widget;

import java.util.List;

import de.kruemelnerd.bakersheaven.data.Recipe;
import de.kruemelnerd.bakersheaven.data.source.BakeryDataSource;
import de.kruemelnerd.bakersheaven.data.source.BakeryRepository;
import timber.log.Timber;

public class WidgetPresenter {

    private WidgetView mWidgetConfigurationView;
    private BakeryRepository mRepository;

    public WidgetPresenter(WidgetView mWidgetConfigurationView, BakeryRepository mRepository) {
        this.mWidgetConfigurationView = mWidgetConfigurationView;
        this.mRepository = mRepository;
    }

    public void loadRecipes(){
        mRepository.getRecipes(new WidgetLoadAllRecipesCallback());
    }


    private class WidgetLoadAllRecipesCallback implements BakeryDataSource.LoadRecipesCallback{

        @Override
        public void onRecipesLoaded(List<Recipe> recipes) {
          mWidgetConfigurationView.showRecipes(recipes);
        }

        @Override
        public void onDataNotAvailable() {
            Timber.e("ERROR while loading the mRecipes for the widget");
        }
    }
}
