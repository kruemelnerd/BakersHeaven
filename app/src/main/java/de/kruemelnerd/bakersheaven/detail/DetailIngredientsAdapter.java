package de.kruemelnerd.bakersheaven.detail;

import android.content.Context;
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
import de.kruemelnerd.bakersheaven.data.IngredientsItem;

public class DetailIngredientsAdapter extends RecyclerView.Adapter<DetailIngredientsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_measure)
        public TextView measure;
        @BindView(R.id.ingredient_quantity)
        public TextView quantity;
        @BindView(R.id.ingredient_description)
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context mContext;
    private LayoutInflater mInflater;
    private List<IngredientsItem> mIngredients;

    public DetailIngredientsAdapter(Context context, List<IngredientsItem> mIngredients) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mIngredients = mIngredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.detail_single_ingredient, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();

        IngredientsItem item = mIngredients.get(adapterPosition);
        holder.measure.setText(item.getMeasure());
        holder.quantity.setText(String.valueOf(item.getQuantity()));
        holder.description.setText(item.getIngredient());
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public void setIngredients(List<IngredientsItem> ingredients) {
        this.mIngredients = ingredients;
    }
}
