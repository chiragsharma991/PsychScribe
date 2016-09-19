package com.psychscribe.developmentalHistory.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.psychscribe.R;
import com.psychscribe.databinding.ListItemEarlyHistoryQueBinding;
import com.psychscribe.databinding.ListItemPatientBinding;
import com.psychscribe.developmentalHistory.model.QuestionModel;
import com.psychscribe.patients.model.PatientModel;
import com.psychscribe.utiz.AppUtils;

import java.util.List;

/**
 * Created by ubuntu on 19/4/16.
 */
public class EarlyHistoryQueRecyclerAdapter extends RecyclerView.Adapter<EarlyHistoryQueRecyclerAdapter.ViewHolder> {

    private List<QuestionModel> mListPatient;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemEarlyHistoryQueBinding binding;

        public ViewHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }
        public ListItemEarlyHistoryQueBinding getBinding() {
            return binding;
        }
    }

    public EarlyHistoryQueRecyclerAdapter(Context context, List<QuestionModel> mListPatient) {
        this.mListPatient = mListPatient;
        mContext = context;
    }

    @Override
    public EarlyHistoryQueRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_early_history_que, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final QuestionModel interestModel = mListPatient.get(position);
        //holder.getBinding().setPatientModel(interestModel);
        holder.getBinding().executePendingBindings();
        switch (Integer.parseInt(interestModel.types)){
            case 1:
                holder.getBinding().edtTextbox.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.getBinding().edtDropdown.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.getBinding().relSwitch.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.getBinding().edtDropdown.setVisibility(View.VISIBLE);
                break;
            case 5:
                holder.getBinding().relSwitch.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mListPatient.size();
    }

}
