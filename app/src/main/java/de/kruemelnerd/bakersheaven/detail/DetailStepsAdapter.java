package de.kruemelnerd.bakersheaven.detail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.StepsItem;
import timber.log.Timber;

public class DetailStepsAdapter extends RecyclerView.Adapter<DetailStepsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.detail_single_step_position)
        public TextView stepPosition;
        @BindView(R.id.detail_single_step_short_description)
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context mContext;
    private final boolean mTwoPane;
    private LayoutInflater mInflater;
    private List<StepsItem> mSteps;


    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            StepsItem item = (StepsItem) view.getTag();
            if(mTwoPane){
                Timber.i("mTwoPane true" + item.getDescription() );
            }else {
                Timber.i("mTwoPane fallse" + item.getDescription() );
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailInformationActivity.class);
                intent.putExtra(DetailInformationActivity.EXTRA_STEP, item);

                context.startActivity(intent);
            }
        }
    };



    public DetailStepsAdapter(Context context, List<StepsItem> steps, final boolean twoPane) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mSteps = steps;
        this.mTwoPane = twoPane;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.detail_single_step, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();

        StepsItem item = mSteps.get(adapterPosition);
        holder.description.setText(item.getShortDescription());
        holder.stepPosition.setText(String.valueOf(adapterPosition + 1));

        holder.itemView.setTag(item);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public void setSteps(List<StepsItem> mSteps) {
        this.mSteps = mSteps;
    }



}
