package com.psychscribe.patients.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.psychscribe.R;
import com.psychscribe.databinding.ListItemCollateralBinding;
import com.psychscribe.patients.model.CollateralModel;
import com.psychscribe.utiz.AnimationUtils;

import java.util.List;

/**
 * Created by ubuntu on 19/4/16.
 */
public class CollateralRecyclerAdapter extends RecyclerView.Adapter<CollateralRecyclerAdapter.ViewHolder> {

    private List<CollateralModel> mListPatient;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemCollateralBinding binding;
        public MyCustomEditTextListener listenerName, listenerCompany, listenerPhone, listenerFaxNo, listenerAddress, listenerAdditional;

        public ViewHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);

            this.listenerName = new MyCustomEditTextListener(binding.edtName);
            this.listenerCompany = new MyCustomEditTextListener(binding.edtCompany);
            this.listenerPhone = new MyCustomEditTextListener(binding.edtPhone);
            this.listenerFaxNo = new MyCustomEditTextListener(binding.edtFaxNo);
            this.listenerAddress = new MyCustomEditTextListener(binding.edtAddress);
            this.listenerAdditional = new MyCustomEditTextListener(binding.edtAdditionalInfo);

            binding.edtName.addTextChangedListener(listenerName);
            binding.edtCompany.addTextChangedListener(listenerCompany);
            binding.edtPhone.addTextChangedListener(listenerPhone);
            binding.edtFaxNo.addTextChangedListener(listenerFaxNo);
            binding.edtAddress.addTextChangedListener(listenerAddress);
            binding.edtAdditionalInfo.addTextChangedListener(listenerAdditional);
        }
        public ListItemCollateralBinding getBinding() {
            return binding;
        }
    }

    public CollateralRecyclerAdapter(Context context, List<CollateralModel> mListPatient) {
        this.mListPatient = mListPatient;
    }

    @Override
    public CollateralRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_collateral, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CollateralModel collateralModel = mListPatient.get(position);
        holder.getBinding().setCollateralModel(collateralModel);
        holder.getBinding().executePendingBindings();

        holder.listenerName.updatePosition(position);
        holder.listenerCompany.updatePosition(position);
        holder.listenerAdditional.updatePosition(position);
        holder.listenerAddress.updatePosition(position);
        holder.listenerFaxNo.updatePosition(position);
        holder.listenerPhone.updatePosition(position);

        holder.getBinding().edtName.setText(collateralModel.getName());
        holder.getBinding().edtCompany.setText(collateralModel.getCompany());
        holder.getBinding().edtPhone.setText(collateralModel.getPhone());
        holder.getBinding().edtFaxNo.setText(collateralModel.getFaxNo());
        holder.getBinding().edtAddress.setText(collateralModel.getAddress());
        holder.getBinding().edtAdditionalInfo.setText(collateralModel.getAdditionalInfo());

        if(collateralModel.isAnim()) {
            AnimationUtils.expand(holder.getBinding().linChild);
            collateralModel.setAnim(false);
        }
        holder.getBinding().imgExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(collateralModel.isExpand()){
                    collateralModel.setExpand(false);
                    notifyDataSetChanged();
                }else {
                    collateralModel.setExpand(true);
                    collateralModel.setAnim(true);
                    notifyDataSetChanged();
                }
            }
        });
        holder.getBinding().imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListPatient.size() != 1) {
                    mListPatient.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListPatient.size();
    }

    private class MyCustomEditTextListener implements TextWatcher {

        private int position;
        private EditText editText;

        public MyCustomEditTextListener(EditText editText){
            this.editText= editText;
        }

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.length() == 0){
                return;
            }
            switch (editText.getId()){
                case R.id.edtName:
                    if (String.valueOf(mListPatient.get(position).getName()).equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setName(charSequence.toString());
                    }
                    break;
                case R.id.edtCompany:
                    if (mListPatient.get(position).getCompany().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setCompany(charSequence.toString());
                    }
                    break;
                case R.id.edtPhone:
                    if (mListPatient.get(position).getPhone().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setPhone(charSequence.toString());
                    }
                    break;
                case R.id.edtFaxNo:
                    if (mListPatient.get(position).getFaxNo().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setFaxNo(charSequence.toString());
                    }
                    break;
                case R.id.edtAddress:
                    if (mListPatient.get(position).getAddress().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setAddress(charSequence.toString());
                    }
                    break;
                case R.id.edtAdditionalInfo:
                    if (mListPatient.get(position).getAdditionalInfo().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setAdditionalInfo(charSequence.toString());
                    }
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

}
