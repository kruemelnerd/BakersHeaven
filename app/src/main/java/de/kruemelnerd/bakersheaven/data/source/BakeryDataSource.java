package de.kruemelnerd.bakersheaven.data.source;

import java.util.List;

import de.kruemelnerd.bakersheaven.data.Recipe;

public interface BakeryDataSource {
    interface LoadRecipesCallback{
        void onRecipesLoaded(List<Recipe> recipes);
        void onDataNotAvailable();
    }
}
