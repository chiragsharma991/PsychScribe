package com.psychscribe.patients.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.psychscribe.R;
import com.psychscribe.databinding.ListItemPatientBinding;
import com.psychscribe.patients.model.PatientModel;
import com.psychscribe.utiz.AppUtils;

import java.util.List;

/**
 * Created by ubuntu on 19/4/16.
 */
public class PatientRecyclerAdapter extends RecyclerView.Adapter<PatientRecyclerAdapter.ViewHolder> {

    private static final int ANIM_DURATION = 300;
    private List<PatientModel.PatientData> mListPatient;
    private Context mContext;
    private int lastPos = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemPatientBinding binding;

        public ViewHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }
        public ListItemPatientBinding getBinding() {
            return binding;
        }
    }

    public PatientRecyclerAdapter(Context context, List<PatientModel.PatientData> mListPatient) {
        this.mListPatient = mListPatient;
        mContext = context;
    }

    public List<PatientModel.PatientData> getPatientList(){
        return mListPatient;
    }

    public void setPatientList(List<PatientModel.PatientData> patientList){
        mListPatient.clear();
        mListPatient.addAll(patientList);
        notifyDataSetChanged();
    }

    @Override
    public PatientRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_patient, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PatientModel.PatientData interestModel = mListPatient.get(position);
        holder.getBinding().setPatientModel(interestModel);
        holder.getBinding().executePendingBindings();
        holder.getBinding().txtDate.setText(AppUtils.dateConvert("yyyy-MM-dd", "dd MMMM,yyyy", interestModel.getDob()));
        setScaleAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mListPatient.size();
    }

    private void setScaleAnimation(View view, int pos) {
        if(pos > lastPos) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            anim.setDuration(ANIM_DURATION);
            view.startAnimation(anim);
            lastPos = pos;
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    private void animateStackByStack(View view, final int pos) {
        if(pos > lastPos) {
            view.animate().cancel();
            view.setTranslationY(100);
            view.setAlpha(0);
            view.animate().alpha(1.0f).translationY(0).setDuration(ANIM_DURATION).setStartDelay(100);
            lastPos = pos;
        }
    }

}
