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
import appkite.jordiguzman.com.backingapp.model.Step;



public class AdapterDetail extends RecyclerView.Adapter<AdapterDetail.AdapterDetailViewHolder>{

    private  List<Step> mSteps = null;
    private Context mContext;
    private StepsClickListener mStepsClickListener= null;


    public AdapterDetail(List<Step> steps, Context context, StepsClickListener stepsClickListener){
        mSteps= steps;
        mContext= context;
        mStepsClickListener = stepsClickListener;
    }



    @NonNull
    @Override
    public AdapterDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.list_items_detail, parent, false);

        return new AdapterDetailViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDetailViewHolder holder, int position) {

        if (position==0){
            holder.tv_item_detail_step.setText(mContext.getResources().getString(R.string.introduction));
            holder.tv_item_detail.setText(mSteps.get(position).shortDescription);
        }else {
            holder.tv_item_detail_step.setText(mContext.getResources().getString(R.string.step).concat(" ").concat(String.valueOf(position)));
            holder.tv_item_detail.setText(mSteps.get(position).shortDescription);
        }

    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public interface StepsClickListener{
        void onClickStep(int position);
    }
    class AdapterDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView tv_item_detail, tv_item_detail_step;


        AdapterDetailViewHolder(View itemView) {
            super(itemView);
            tv_item_detail = itemView.findViewById(R.id.tv_item_detail);
            tv_item_detail_step = itemView.findViewById(R.id.tv_item_detail_step);
            tv_item_detail.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int clickPosition = getAdapterPosition();
            mStepsClickListener.onClickStep(clickPosition);

        }
}




}
