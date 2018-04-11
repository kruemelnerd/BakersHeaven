package de.kruemelnerd.bakersheaven.data.source;

import java.util.List;

import de.kruemelnerd.bakersheaven.data.Recipe;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class BakeryService {

    //private final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private final static String BASE_URL = "http://www.mocky.io/v2/5ac52aee2f00002900f5fd82/";

    public interface BakeryRestClient {

        @GET("baking.json")
        Call<List<Recipe>> getAllRecipes();

    }

    public static BakeryRestClient getApi() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit.create(BakeryRestClient.class);
    }
}
