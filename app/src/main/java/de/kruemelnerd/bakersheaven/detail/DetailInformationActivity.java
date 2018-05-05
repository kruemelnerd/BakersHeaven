package de.kruemelnerd.bakersheaven.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.kruemelnerd.bakersheaven.R;

public class DetailInformationActivity extends AppCompatActivity {


/*
    @BindView(R.id.detail_step_image)
    ImageView mStepImage;

    @BindView(R.id.detail_step_instruction)
    TextView mStepInstruction;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_information);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailInformationFragment.EXTRA_STEP,
                    getIntent().getParcelableExtra(DetailInformationFragment.EXTRA_STEP));
            DetailInformationFragment fragment = new DetailInformationFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        }

       /* ButterKnife.bind(this);

        if(savedInstanceState == null){
            StepsItem item = getIntent().getParcelableExtra(DetailInformationFragment.EXTRA_STEP);
            String instructions = item.getDescription();
            instructions = StepUtil.removeFirstNumber(instructions);
            mStepInstruction.setText(instructions);

            String thumbnailPath = item.getThumbnailURL();
            if (StringUtils.isBlank(thumbnailPath)) {
                thumbnailPath = "isEmpty";
            }
            Picasso
                    .with(this)
                    .load(thumbnailPath)
                    .fit()
                    .error(R.drawable.ic_chef_round)
                    .into(mStepImage);

        }*/
    }
}
