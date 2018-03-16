package appkite.jordiguzman.com.backingapp.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.model.Ingredients;
import appkite.jordiguzman.com.backingapp.ui.DetailRecipes;

public class AdapterIngredients  extends RecyclerView.Adapter<AdapterIngredients.AdapterIngredientsViewHolder>{

    private List<Ingredients> mIngredients = null;
    private Context mContext;

    public AdapterIngredients(List<Ingredients> mIngredients, Context mContext) {
        this.mIngredients = mIngredients;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AdapterIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.list_items_ingredients, parent, false);
        return new AdapterIngredientsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterIngredientsViewHolder holder, int position) {

        String quantity = String.valueOf(DetailRecipes.ingredients.get(position).quantity());
        holder.tv_item_quantity.setText(quantity);
        holder.tv_item_measure.setText(DetailRecipes.ingredients.get(position).measure());
        holder.tv_item_ingredient.setText(DetailRecipes.ingredients.get(position).ingredient());
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    class AdapterIngredientsViewHolder extends RecyclerView.ViewHolder{

        TextView tv_item_quantity, tv_item_measure, tv_item_ingredient;

        AdapterIngredientsViewHolder(View itemView) {
            super(itemView);
            tv_item_quantity = itemView.findViewById(R.id.tv_item_quantity);
            tv_item_measure = itemView.findViewById(R.id.tv_item_measure);
            tv_item_ingredient = itemView.findViewById(R.id.tv_item_ingredient);
        }
    }
}
