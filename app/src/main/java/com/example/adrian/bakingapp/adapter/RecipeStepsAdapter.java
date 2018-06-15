package com.example.adrian.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adrian.bakingapp.R;
import com.example.adrian.bakingapp.ui.StepDetailActivity;
import com.example.adrian.bakingapp.ui.StepDetailFragment;
import com.example.adrian.bakingapp.ui.StepListActivity;
import com.example.adrian.bakingapp.data.model.Step;

import org.parceler.Parcels;

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {

    private final StepListActivity mParentActivity;
    private final boolean mTwoPane;
    private List<Step> mSteps;
    private Context mContext;
    private StepItemListener mStepListener;

    public RecipeStepsAdapter(Context context, StepListActivity parent, List<Step> steps, boolean twoPane, StepItemListener stepListener) {
        mContext = context;
        mSteps = steps;
        mStepListener = stepListener;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public RecipeStepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.step_list_content, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mStepListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeStepsAdapter.ViewHolder holder, int position) {
        Step step = mSteps.get(position);
        holder.contentTextView.setText(step.getShortDescription());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public void updateSteps(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    private Step getStep(int adapterPosition) {
        return mSteps.get(adapterPosition);
    }

    public interface StepItemListener {
        void onStepClick(long id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView contentTextView;

        StepItemListener mStepListener;

        public ViewHolder(View view, StepItemListener stepListener) {
            super(view);
            contentTextView = view.findViewById(R.id.content);

            this.mStepListener = stepListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Step step = getStep(getAdapterPosition());
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(StepDetailFragment.ARG_ITEM_ID, Parcels.wrap(step));
                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, fragment)
                        .commit();
            } else {
                Context context = v.getContext();
                Intent intent = new Intent(context, StepDetailActivity.class);
                intent.putExtra(StepDetailFragment.ARG_ITEM_ID, Parcels.wrap(step));

                context.startActivity(intent);
            }
            this.mStepListener.onStepClick(step.getId());
        }
    }
}
