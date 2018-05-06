package de.kruemelnerd.bakersheaven.overview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.Recipe;

public class OverviewRecyclerViewAdapter extends RecyclerView.Adapter<OverviewRecyclerViewAdapter.ViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView title;
        public ImageView image;
        public int recipeId;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.overview_recipe_title);
            image = itemView.findViewById(R.id.overview_recipe_thumbnail);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(recipeId);
        }
    }

    private Context mContext;
    private List<Recipe> recipeList;
    private OnItemClickListener listener;

    public OverviewRecyclerViewAdapter(Context mContext, List<Recipe> recipeList, OnItemClickListener listener) {
        this.mContext = mContext;
        this.recipeList = recipeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.overview_single_card, parent, false);
        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();
        final Recipe recipe = recipeList.get(adapterPosition);
        holder.recipeId = recipe.getId();
        holder.title.setText(recipe.getName());
        String imageUrl = recipe.getImage();
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "xxx";
           // holder.image.setBackgroundColor(mContext.getColor(R.color.cardview_background_color));
        }
        Picasso.
                with(mContext)
                .load(imageUrl)
                .fit()
                .error(R.drawable.ic_chef_round)
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public void setRecipes(List<Recipe> recipes) {
        this.recipeList = recipes;
    }


}
