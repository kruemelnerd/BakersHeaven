package de.kruemelnerd.bakersheaven.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.StepsItem;

public class DetailInformationActivity extends AppCompatActivity {



    @BindView(R.id.detail_step_image)
    ImageView mStepImage;

    @BindView(R.id.detail_step_instruction)
    TextView mStepInstruction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_information);

        ButterKnife.bind(this);

        if(savedInstanceState == null){
            StepsItem item = getIntent().getParcelableExtra(DetailInformationFragment.EXTRA_STEP);
            String instructions = item.getDescription();
            instructions = removeNumber(instructions);
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

        }
    }

    private String removeNumber(String instructions) {
        return instructions.replaceFirst("(^\\d+\\. )", "");

    }
}
