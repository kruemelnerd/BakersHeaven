package de.kruemelnerd.bakersheaven.overview;

import java.util.List;

import de.kruemelnerd.bakersheaven.data.Recipe;

public interface OverviewContract {

    interface ViewOps {
        void showRecipes(List<Recipe> recipes);
        void showErrorLoadingRecipes();

        void showRecipeDetail(Recipe recipe);
    }

    interface PresenterOps {
        void start();
        void loadRecipes();

        void loadRecipe(int recipeId);
    }
}
