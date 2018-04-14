package de.kruemelnerd.bakersheaven.overview;

import java.util.List;

import de.kruemelnerd.bakersheaven.data.Recipe;
import de.kruemelnerd.bakersheaven.data.source.BakeryDataSource;
import de.kruemelnerd.bakersheaven.data.source.BakeryRepository;

public class OverviewPresenter implements OverviewContract.PresenterOps{

    private OverviewContract.ViewOps mView;
    private BakeryRepository mRepository;


    public OverviewPresenter(OverviewContract.ViewOps viewOps, BakeryRepository repository){
        this.mView = viewOps;
        this.mRepository = repository;
    }



    @Override
    public void start() {
        loadRecipes();
    }

    @Override
    public void loadRecipes() {
        mRepository.getRecipes(new BakeryOverviewCallback());
    }

    @Override
    public void loadRecipe(final int recipeId){
        mRepository.getRecipe(recipeId, new BakeryDataSource.LoadSingleRecipeCallback(){

            @Override
            public void onRecipeLoaded(final Recipe recipe) {
                mView.showRecipeDetail(recipe);
            }

            @Override
            public void onDataNotAvailable() {
                mView.showErrorLoadingRecipes();
            }
        });
    }

    private class BakeryOverviewCallback implements BakeryDataSource.LoadRecipesCallback{

        @Override
        public void onRecipesLoaded(List<Recipe> recipes) {
            mView.showRecipes(recipes);
        }

        @Override
        public void onDataNotAvailable() {
            mView.showErrorLoadingRecipes();
        }
    }

}
