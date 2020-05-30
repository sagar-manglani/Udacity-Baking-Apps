package com.udacity.udacitybakingapps.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.udacitybakingapps.Data.Ingredients;
import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Interface.FragmentListOnClickListener;
import com.udacity.udacitybakingapps.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeDetailListAdapter extends RecyclerView.Adapter<RecipeDetailListAdapter.RecipeDetailListVH> {
    Context context;
    Recipe recipe;
    FragmentListOnClickListener parent;
    private static String TAG=RecipeDetailListAdapter.class.getSimpleName();

    public RecipeDetailListAdapter(Context context, FragmentListOnClickListener parent){
        this.context= context;
        this.parent=parent;
    }
    @NonNull
    @Override
    public RecipeDetailListAdapter.RecipeDetailListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipelist_row, parent, false);
        return new RecipeDetailListAdapter.RecipeDetailListVH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailListAdapter.RecipeDetailListVH holder, int position) {
        if(position==0){
            holder.textview.setText("See all ingredients");
        }else
            holder.textview.setText(recipe.getSteps().get(position-1).getShortDescription());
    }

    public void setRecipe(Recipe recipe){
        this.recipe=recipe;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        if(recipe!=null)
            return recipe.getSteps().size()+1;
        return 0;
    }

    class RecipeDetailListVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textview;
        RecipeDetailListVH(View view){
            super(view);
            textview=view.findViewById(R.id.recipe_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG,"Inside onClick of RecipeDetailAdapter "+ getAbsoluteAdapterPosition());
            parent.listOnClick(getAbsoluteAdapterPosition());
        }
    }
}

