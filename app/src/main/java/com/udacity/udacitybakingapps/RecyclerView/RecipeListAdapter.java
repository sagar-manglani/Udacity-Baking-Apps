package com.udacity.udacitybakingapps.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Interface.FragmentListOnClickListener;
import com.udacity.udacitybakingapps.R;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListVH> {

    private ArrayList<Recipe> recipeArrayList;
    private Context context;
    private FragmentListOnClickListener listener;
    private static String TAG = RecipeListAdapter.class.getSimpleName();

    public RecipeListAdapter(Context context, FragmentListOnClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipelist_row, parent, false);
        return new RecipeListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListVH holder, int position) {
        Log.d(TAG, "inside onBind " + recipeArrayList.get(0).getName());
        holder.recipe_name.setText(recipeArrayList.get(position).getName());
        if(!recipeArrayList.get(position).getImage_url().equals("N/A")){
            Picasso.get().load(recipeArrayList.get(position).getImage_url()).error(R.drawable.ic_recipe).into(holder.recipe_image);
        }

    }

    @Override
    public int getItemCount() {
        if (recipeArrayList != null)
            return recipeArrayList.size();
        return 0;
    }

    public void setList(ArrayList<Recipe> list) {
        recipeArrayList = list;
        notifyDataSetChanged();
    }


    public class RecipeListVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipe_name;
        ImageView recipe_image;
        RecipeListVH(View view) {
            super(view);
            recipe_name = view.findViewById(R.id.recipe_name);
            recipe_image=view.findViewById(R.id.recipe_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG,"clicked on in list "+getAbsoluteAdapterPosition());
            listener.listOnClick(getAbsoluteAdapterPosition());
        }
    }
}