package de.kruemelnerd.bakersheaven.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.kruemelnerd.bakersheaven.R;
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

        if (savedInstanceState == null) {

        }
    }
}
