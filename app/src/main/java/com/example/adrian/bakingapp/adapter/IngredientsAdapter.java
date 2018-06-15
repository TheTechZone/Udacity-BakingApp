package com.example.adrian.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adrian.bakingapp.R;
import com.example.adrian.bakingapp.data.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<Ingredient> mIngredients;
    private Context mContext;

    public IngredientsAdapter(Context context, List<Ingredient> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.step_list_content, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.ViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.contentTextView.setText(ingredient.getListing(mContext));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public void updateSteps(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    private Ingredient getIngredients(int adapterPosition) {
        return mIngredients.get(adapterPosition);
    }

    public interface StepItemListener {
        void onStepClick(long id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView contentTextView;

        StepItemListener mStepListener;

        public ViewHolder(View view) {
            super(view);
            contentTextView = view.findViewById(R.id.content);
        }

    }
}
