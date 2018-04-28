package de.kruemelnerd.bakersheaven.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.StepsItem;

public class DetailInformationFragment extends Fragment {
    public static final String EXTRA_STEP = "extra_step";

    private StepsItem mStep;

    public DetailInformationFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(EXTRA_STEP)) {
            mStep = getArguments().getParcelable(EXTRA_STEP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(EXTRA_STEP);
        }

        View rootView = inflater.inflate(R.layout.activity_detail_information, container, false);


        final TextView stepInstruction = rootView.findViewById(R.id.detail_step_instruction);
        stepInstruction.setText(mStep.getDescription());

        return rootView;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(EXTRA_STEP, mStep);
        super.onSaveInstanceState(outState);
    }
}
