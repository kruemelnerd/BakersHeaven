package de.kruemelnerd.bakersheaven.overview;

import java.util.ArrayList;
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
        Recipe recipe = new Recipe();
        recipe.setName("Blaubeerkuchen");
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        recipe.setName("Torte");
        recipes.add(recipe);
        mView.showRecipes(recipes);
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
