package de.kruemelnerd.bakersheaven.overview;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.Recipe;
import de.kruemelnerd.bakersheaven.data.source.BakeryRepository;
import de.kruemelnerd.bakersheaven.data.source.Injection;
import de.kruemelnerd.bakersheaven.detail.DetailActivity;
import timber.log.Timber;

public class OverviewMasterFragment extends Fragment implements OverviewContract.ViewOps {

    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private OverviewRecyclerViewAdapter mAdapter;
    private List<Recipe> mRecipeList;

    private OverviewContract.PresenterOps presenter;

    public OverviewMasterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.overview_fragment, container, false);

        initRecyclerView(mView);

        BakeryRepository repository = Injection.provideBakeryRepository(mContext);
        presenter = new OverviewPresenter(this, repository);
        presenter.start();

        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        mContext = activity;

    }

    private void initRecyclerView(View rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewOverview);

        mRecipeList = new ArrayList<>();
        mAdapter = new OverviewRecyclerViewAdapter(getActivity(), mRecipeList, new OverviewRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Timber.i("Clicked on " + position +". ");
                presenter.loadRecipe(position);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), calculateNoOfColumns(getActivity()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }


    private static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if (noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }


    @Override
    public void showRecipes(List<Recipe> recipes) {
        this.mRecipeList = recipes;
        mAdapter.setRecipes(mRecipeList);
        mAdapter.notifyDataSetChanged();
        for (Recipe recipe : recipes) {
            Timber.i(recipe.getName());
        }
    }

    @Override
    public void showErrorLoadingRecipes() {
        Toast.makeText(getActivity(), "Error while loading recipes", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRecipeDetail(Recipe recipe) {
        boolean mTwoPane = false;
        if(mTwoPane){

        }else {
            launchDetailActivity(recipe);
        }
    }

    private void launchDetailActivity(Recipe recipe) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_RECIPE, recipe);
        getActivity().startActivity(intent);
    }
}
