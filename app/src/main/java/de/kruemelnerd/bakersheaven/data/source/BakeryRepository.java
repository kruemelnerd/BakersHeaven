package de.kruemelnerd.bakersheaven.data.source;

import android.content.Context;

import java.util.List;

import de.kruemelnerd.bakersheaven.data.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class BakeryRepository {

    private static BakeryRepository INSTANCE;
    private Context mContext;

    private BakeryRepository() {

    }

    private BakeryRepository(Context context) {
        this.mContext = context;
    }

    public static BakeryRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new BakeryRepository(context);
        }
        return INSTANCE;
    }


    public void getRecipes(final BakeryDataSource.LoadRecipesCallback callback) {
        Call<List<Recipe>> call = BakeryService.getApi().getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> allRecipes = response.body();
                if (allRecipes != null && !allRecipes.isEmpty()) {
                    callback.onRecipesLoaded(allRecipes);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Timber.e(t);
                callback.onDataNotAvailable();
            }
        });
    }
}
