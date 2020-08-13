package com.udacity.udacitybakingapps.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Interface.FragmentListOnClickListener;
import com.udacity.udacitybakingapps.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeDetailListAdapter extends RecyclerView.Adapter<RecipeDetailListAdapter.RecipeDetailListVH> {
    private Context context;
    private Recipe recipe;
    //private int prev_position=-1;
    private FragmentListOnClickListener parent;
    private static String TAG=RecipeDetailListAdapter.class.getSimpleName();

    public RecipeDetailListAdapter(Context context, FragmentListOnClickListener parent){
        this.context= context;
        this.parent=parent;
    }
    @NonNull
    @Override
    public RecipeDetailListAdapter.RecipeDetailListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_detail_list_row, parent, false);
        return new RecipeDetailListAdapter.RecipeDetailListVH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailListAdapter.RecipeDetailListVH holder, int position) {
        if(position==0){
            holder.textview.setText(context.getResources().getString(R.string.ingredients_row_text));
        }else
            holder.textview.setText(recipe.getSteps().get(position-1).getShortDescription());
        //String s=holder.textview.getText().toString();
        //int steps=recipe.getSteps().size();


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
            textview=view.findViewById(R.id.step_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG,"Inside onClick of RecipeDetailAdapter "+ getAbsoluteAdapterPosition());
            /*if(rightArrow.getVisibility()==View.INVISIBLE){
                if(prev_arrow!=null)
                    prev_arrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.VISIBLE);
                prev_arrow=rightArrow;
            }
            if(prev_position==-1){
               // view.setBackgroundColor(Color.RED);
            }
            else if(prev_position!=getAbsoluteAdapterPosition()) {
                //prev_arrow.setBackgroundColor(Color.WHITE);
               // view.setBackgroundColor(Color.RED);
            }
            prev_arrow=view;
            prev_position=getAbsoluteAdapterPosition();*/
            parent.listOnClick(getAbsoluteAdapterPosition());

        }
    }
}

