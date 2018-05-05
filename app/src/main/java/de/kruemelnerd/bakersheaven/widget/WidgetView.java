package de.kruemelnerd.bakersheaven.widget;

import java.util.List;

import de.kruemelnerd.bakersheaven.data.Recipe;

public interface WidgetView {
    void showRecipes(List<Recipe> recipes);
}
