package de.kruemelnerd.bakersheaven.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.IngredientsItem;
import de.kruemelnerd.bakersheaven.data.Recipe;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "extra_recipe";

    @BindView(R.id.detail_headline)
    TextView mTextViewHeadline;

    @BindView(R.id.detail_ingredients_number)
    TextView mIngredientsNumbers;

    @BindView(R.id.detail_serving_number)
    TextView mServingNumbers;

    List<IngredientsItem> mIngredientsList;
    DetailIngredientsAdapter mIngredientsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        Timber.i("Recipe: " + recipe.toString());

        mTextViewHeadline.setText(recipe.getName());
        mIngredientsNumbers.setText(String.valueOf(recipe.getIngredients().size()));
        mServingNumbers.setText(String.valueOf(recipe.getServings()));

        initIngredientRecyclerView(recipe);

        if (savedInstanceState == null) {

        }
    }

    private void initIngredientRecyclerView(Recipe recipe){
        mIngredientsList = new ArrayList<>();
        mIngredientsAdapter = new DetailIngredientsAdapter(this, mIngredientsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = this.findViewById(R.id.detail_recyclerView_ingredients);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mIngredientsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        showIngredients(recipe.getIngredients());
    }
    public void showIngredients(List<IngredientsItem> ingredients){
        mIngredientsList = ingredients;
        mIngredientsAdapter.setIngredients(mIngredientsList);
        mIngredientsAdapter.notifyDataSetChanged();
    }
}
