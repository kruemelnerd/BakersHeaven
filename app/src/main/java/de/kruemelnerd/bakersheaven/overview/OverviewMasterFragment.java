package de.kruemelnerd.bakersheaven.overview;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
import timber.log.Timber;

public class OverviewMasterFragment extends Fragment  implements OverviewContract.ViewOps{

    private final String TAG = this.getClass().getSimpleName();

    View mView;
    Context mContext;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OverviewRecyclerViewAdapter mAdapter;
    List<Recipe> mRecipeList;

    OverviewContract.PresenterOps presenter;

    public OverviewMasterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.overview_fragment, container, false);

        initRecyclerView(mView, savedInstanceState);

        BakeryRepository repository = Injection.provideBakeryRepository(mContext);
        presenter = new OverviewPresenter(this, repository);
        presenter.start();

        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        mContext=activity;

    }

    private void initRecyclerView(View rootView, Bundle savedInstanceState) {
        mRecyclerView = rootView.findViewById(R.id.recyclerViewOverview);

        mRecipeList = new ArrayList<>();
        mAdapter = new OverviewRecyclerViewAdapter(getActivity(), mRecipeList, null);
        mLayoutManager = new GridLayoutManager(getActivity(), calculateNoOfColumns(getActivity()));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

    }


    public static int calculateNoOfColumns(Context context) {
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
}
