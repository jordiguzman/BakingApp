package appkite.jordiguzman.com.backingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.model.Recipe;



public class AdapterMain extends RecyclerView.Adapter<AdapterMain.AdapterMainViewHolder> {


    private List<Recipe> mReciples;
    final private ListItemClickListener mListItemClickListener;
    private Context mContext;


    public AdapterMain(List<Recipe> reciples, ListItemClickListener listItemClickListener, Context context){
        this.mReciples= reciples;
        this.mListItemClickListener = listItemClickListener;
        this.mContext = context;

    }


    public interface ListItemClickListener {
        void onListItemClicked(int clickedItemIndex);
    }
    @NonNull
    @Override
    public AdapterMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.row_item_main, parent, false);

        return new AdapterMainViewHolder(rootView);




    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMainViewHolder holder, int position) {

        Recipe recipe = mReciples.get(position);
        String name = recipe.name;
        holder.tv_name.setText(name);


            switch (position){
                case 0:
                    holder.iv_main.setImageResource(R.drawable.nutella_pie);
                    break;
                case 1:
                    holder.iv_main.setImageResource(R.drawable.brownie);
                    break;
                case 2:
                    holder.iv_main.setImageResource(R.drawable.yellow_cake);
                    break;
                case 3:
                    holder.iv_main.setImageResource(R.drawable.cheesecake);
                    break;
            }





    }

    @Override
    public int getItemCount() {
        return mReciples.size();
    }

    public class AdapterMainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

         TextView  tv_name;
         ImageView iv_main;

        AdapterMainViewHolder(View itemView) {
            super(itemView);
            iv_main = itemView.findViewById(R.id.iv_main);
            tv_name = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int clickPosition = getAdapterPosition();
            mListItemClickListener.onListItemClicked(clickPosition);

        }
    }
}
