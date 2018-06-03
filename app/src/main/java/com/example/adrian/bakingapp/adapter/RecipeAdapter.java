package com.example.adrian.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adrian.bakingapp.R;
import com.example.adrian.bakingapp.data.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> mRecepies;
    private Context mContext;
    private RecipeItemListener mRecipeListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView titleTv;
        public ImageView coverIv;

        RecipeItemListener mRecipeListener;

        public ViewHolder(View itemView, RecipeItemListener recipeItemListener) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.recipe_name_tv);
            coverIv = itemView.findViewById(R.id.recipe_cover_iv);

            this.mRecipeListener = recipeItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = getRecipe(getAdapterPosition());
            Log.e("Recipe img:", recipe.getImage());
            this.mRecipeListener.onRecipeClick(recipe.getId(), recipe);

            notifyDataSetChanged();
        }
    }

    public RecipeAdapter(Context context, List<Recipe> recipes, RecipeItemListener recipeListener) {
        mRecepies = recipes;
        mContext = context;
        mRecipeListener = recipeListener;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.recipe_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mRecipeListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {
        Recipe recipe = mRecepies.get(position);
        TextView textView = holder.titleTv;
        textView.setText(recipe.getName());
        ImageView imageView = holder.coverIv;
        Picasso.get().load(recipe.getImage()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mRecepies.size();
    }

    public void updateRecepies(List<Recipe> recipes) {
        mRecepies = recipes;
        // a little bit of a hack
        for (Recipe r: mRecepies) {
            r.setImage("");
        }
        notifyDataSetChanged();
    }

    private Recipe getRecipe(int adapterPosition) {
        return mRecepies.get(adapterPosition);
    }

    public interface RecipeItemListener {
        void onRecipeClick(long id, Recipe recipe);
    }
}