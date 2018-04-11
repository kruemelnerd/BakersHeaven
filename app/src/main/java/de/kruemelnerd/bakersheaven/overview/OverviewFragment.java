package de.kruemelnerd.bakersheaven.overview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.kruemelnerd.bakersheaven.R;

public class OverviewFragment extends Fragment {

    View mView;

    public OverviewFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.overview_fragment, container, false);
        return mView;
    }
}
