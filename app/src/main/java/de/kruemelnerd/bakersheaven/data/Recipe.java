package de.kruemelnerd.bakersheaven.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe implements Parcelable {

    @SerializedName("image")
    private String image;

    @SerializedName("servings")
    private int servings;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List<IngredientsItem> ingredients;

    @SerializedName("id")
    private int id;

    @SerializedName("steps")
    private List<StepsItem> steps;


    protected Recipe(Parcel in) {
        image = in.readString();
        servings = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(IngredientsItem.CREATOR);
        id = in.readInt();
        steps = in.createTypedArrayList(StepsItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeInt(servings);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeInt(id);
        dest.writeTypedList(steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getServings() {
        return servings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIngredients(List<IngredientsItem> ingredients) {
        this.ingredients = ingredients;
    }

    public List<IngredientsItem> getIngredients() {
        return ingredients;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSteps(List<StepsItem> steps) {
        this.steps = steps;
    }

    public List<StepsItem> getSteps() {
        return steps;
    }



    @Override
    public String toString() {
        return
                "Recipe{" +
                        "image = '" + image + '\'' +
                        ",servings = '" + servings + '\'' +
                        ",name = '" + name + '\'' +
                        ",ingredients = '" + ingredients + '\'' +
                        ",id = '" + id + '\'' +
                        ",steps = '" + steps + '\'' +
                        "}";
    }
}