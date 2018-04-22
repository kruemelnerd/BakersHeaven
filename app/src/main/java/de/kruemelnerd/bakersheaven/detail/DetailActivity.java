package de.kruemelnerd.bakersheaven.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.IngredientsItem;
import de.kruemelnerd.bakersheaven.data.Recipe;
import de.kruemelnerd.bakersheaven.data.StepsItem;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "extra_recipe";

    private boolean mTwoPane;
    private List<IngredientsItem> mIngredientsList;
    private DetailIngredientsAdapter mIngredientsAdapter;

    private List<StepsItem> mStepsList;
    private DetailStepsAdapter mStepsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_marginal);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        Timber.i("Recipe: " + recipe.toString());

        this.mTwoPane = findViewById(R.id.item_detail_container) != null;

        initIngredientRecyclerView(recipe);
        initStepsRecyclerView(recipe);

        if (savedInstanceState == null) {

        }
    }

    private void initStepsRecyclerView(Recipe recipe) {
        mStepsList = new ArrayList<>();
        mStepsAdapter = new DetailStepsAdapter(this, mStepsList, mTwoPane);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = this.findViewById(R.id.detail_recyclerView_steps);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mStepsAdapter);

        showSteps(recipe.getSteps());
    }

    private void showSteps(List<StepsItem> steps) {
        mStepsList = steps;
        mStepsAdapter.setSteps(mStepsList);
        mStepsAdapter.notifyDataSetChanged();
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
