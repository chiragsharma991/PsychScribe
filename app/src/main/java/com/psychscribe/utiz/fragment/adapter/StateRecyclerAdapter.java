package com.psychscribe.utiz.fragment.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.R;
import com.psychscribe.databinding.ListItemPatientBinding;
import com.psychscribe.databinding.ListItemStateBinding;
import com.psychscribe.model.StateModel;

import java.util.List;

/**
 * Created by ubuntu on 19/4/16.
 */
public class StateRecyclerAdapter extends RecyclerView.Adapter<StateRecyclerAdapter.ViewHolder> {

    private static final int FADE_DURATION = 500;
    private List<StateModel.StateData> mListPatient;

    public String getSelectedState(int position) {
        return mListPatient.get(position).getName();
    }

    public void setList(List<StateModel.StateData> filter) {
        this.mListPatient.clear();
        mListPatient.addAll(filter);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemStateBinding binding;

        public ViewHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }
        public ListItemStateBinding getBinding() {
            return binding;
        }
    }

    public StateRecyclerAdapter(Context context, List<StateModel.StateData> mListPatient) {
        this.mListPatient = mListPatient;
    }

    @Override
    public StateRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_state, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final StateModel.StateData stateModel = mListPatient.get(position);
        holder.getBinding().setStateModel(stateModel);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mListPatient.size();
    }

}
