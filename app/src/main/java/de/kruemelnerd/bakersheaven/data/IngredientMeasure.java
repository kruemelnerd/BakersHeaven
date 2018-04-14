package de.kruemelnerd.bakersheaven.data;

public enum IngredientMeasure {
    CUP("CUP"),
    TABLESPOON("TBLSP"),
    TEASPOON("TSP"),
    GRAM("G"),
    KILO("K"),
    UNIT("UNIT");

    private final String value;

    IngredientMeasure(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static IngredientMeasure fromString(String value) {
        for (IngredientMeasure ingredientMeasure : IngredientMeasure.values()) {
            if (ingredientMeasure.getValue().equals(value)) {
                return ingredientMeasure;
            }
        }
        return null;
    }

}
