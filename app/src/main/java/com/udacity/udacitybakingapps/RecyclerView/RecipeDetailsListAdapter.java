package com.udacity.udacitybakingapps.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeDetailsListAdapter extends RecyclerView.Adapter<RecipeDetailsListVH> {
    @NonNull
    @Override
    public RecipeDetailsListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailsListVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}


class RecipeDetailsListVH extends RecyclerView.ViewHolder{

    RecipeDetailsListVH(View view){
        super(view);

    }
}