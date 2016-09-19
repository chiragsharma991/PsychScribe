package com.psychscribe.developmentalHistory.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.R;
import com.psychscribe.databinding.ListItemEarlyHistoryBinding;
import com.psychscribe.databinding.ListItemPatientBinding;
import com.psychscribe.developmentalHistory.model.QuestionModel;
import com.psychscribe.patients.model.PatientModel;

import java.util.List;

/**
 * Created by ubuntu on 19/4/16.
 */
public class EarlyHistorySectionRecyclerAdapter extends RecyclerView.Adapter<EarlyHistorySectionRecyclerAdapter.ViewHolder> {

    private List<QuestionModel> mListPatient;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemEarlyHistoryBinding binding;

        public ViewHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }
        public ListItemEarlyHistoryBinding getBinding() {
            return binding;
        }
    }

    public EarlyHistorySectionRecyclerAdapter(Context context, List<QuestionModel> mListPatient) {
        this.mListPatient = mListPatient;
        mContext = context;
    }

    @Override
    public EarlyHistorySectionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_early_history, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final QuestionModel interestModel = mListPatient.get(position);
       // holder.getBinding().setPatientModel(interestModel);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mListPatient.size();
    }

}
