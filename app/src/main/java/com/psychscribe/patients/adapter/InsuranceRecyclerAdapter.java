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
import com.psychscribe.databinding.ListItemInsuranceBinding;
import com.psychscribe.patients.fragment.InsuranceFragment;
import com.psychscribe.patients.model.InsuranceModel;
import com.psychscribe.utiz.AnimationUtils;

import java.util.List;

/**
 * Created by ubuntu on 19/4/16.
 */
public class InsuranceRecyclerAdapter extends RecyclerView.Adapter<InsuranceRecyclerAdapter.ViewHolder> {

    private List<InsuranceModel> mListPatient;
    private InsuranceFragment insuranceFragment;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ListItemInsuranceBinding binding;
        public MyCustomEditTextListener listenerCompany, listenerPolicy, listenerPolicyType, listenerPolicyHolder, listenerAddress, listenerCity,
                listenerZipCode, listenerEmail, listenerPhoneOne, listenerPhoneTwo;

        public ViewHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);

            this.listenerCompany = new MyCustomEditTextListener(binding.edtCompanyName);
            this.listenerPolicy = new MyCustomEditTextListener(binding.edtPolicy);
            this.listenerPolicyType = new MyCustomEditTextListener(binding.edtPolicyType);
            this.listenerPolicyHolder = new MyCustomEditTextListener(binding.edtPolicyHolder);
            this.listenerAddress = new MyCustomEditTextListener(binding.edtAddress);
            this.listenerCity = new MyCustomEditTextListener(binding.edtCity);
            this.listenerZipCode = new MyCustomEditTextListener(binding.edtZip);
            this.listenerEmail = new MyCustomEditTextListener(binding.edtEmail);
            this.listenerPhoneOne = new MyCustomEditTextListener(binding.edtPhoneNoOne);
            this.listenerPhoneTwo = new MyCustomEditTextListener(binding.edtPhoneNoTwo);

            binding.edtCompanyName.addTextChangedListener(listenerCompany);
            binding.edtPolicy.addTextChangedListener(listenerPolicy);
            binding.edtPolicyType.addTextChangedListener(listenerPolicyType);
            binding.edtPolicyHolder.addTextChangedListener(listenerPolicyHolder);
            binding.edtAddress.addTextChangedListener(listenerAddress);
            binding.edtCity.addTextChangedListener(listenerCity);
            binding.edtZip.addTextChangedListener(listenerZipCode);
            binding.edtEmail.addTextChangedListener(listenerEmail);
            binding.edtPhoneNoOne.addTextChangedListener(listenerPhoneOne);
            binding.edtPhoneNoTwo.addTextChangedListener(listenerPhoneTwo);
        }
        public ListItemInsuranceBinding getBinding() {
            return binding;
        }
    }

    public InsuranceRecyclerAdapter(InsuranceFragment insuranceFragment, List<InsuranceModel> mListPatient) {
        this.mListPatient = mListPatient;
        this.insuranceFragment = insuranceFragment;
    }

    @Override
    public InsuranceRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_insurance, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final InsuranceModel insuranceModel = mListPatient.get(position);
        holder.getBinding().setInsuranceModel(insuranceModel);
        holder.getBinding().executePendingBindings();

        holder.listenerCompany.updatePosition(position);
        holder.listenerPolicy.updatePosition(position);
        holder.listenerPolicyType.updatePosition(position);
        holder.listenerPolicyHolder.updatePosition(position);
        holder.listenerAddress.updatePosition(position);
        holder.listenerCity.updatePosition(position);
        holder.listenerZipCode.updatePosition(position);
        holder.listenerEmail.updatePosition(position);
        holder.listenerPhoneOne.updatePosition(position);
        holder.listenerPhoneTwo.updatePosition(position);


        holder.getBinding().edtCompanyName.setText(insuranceModel.getCompanyName());
        holder.getBinding().edtPolicy.setText(insuranceModel.getPolicy());
        holder.getBinding().edtPolicyType.setText(insuranceModel.getPolicyType());
        holder.getBinding().edtPolicyHolder.setText(insuranceModel.getPolicyHolder());
        holder.getBinding().edtAddress.setText(insuranceModel.getAddress());
        holder.getBinding().edtCity.setText(insuranceModel.getCity());
        holder.getBinding().edtZip.setText(insuranceModel.getZipCode());
        holder.getBinding().edtEmail.setText(insuranceModel.getEmail());
        holder.getBinding().edtPhoneNoOne.setText(insuranceModel.getPhoneOne());
        holder.getBinding().edtPhoneNoTwo.setText(insuranceModel.getPhoneTwo());
        holder.getBinding().edtState.setText(insuranceModel.getState());


        if(insuranceModel.isAnim()) {
            AnimationUtils.expand(holder.getBinding().linChild);
            insuranceModel.setAnim(false);
        }
        holder.getBinding().imgExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(insuranceModel.isExpand()){
                    insuranceModel.setExpand(false);
                    notifyDataSetChanged();
                }else {
                    insuranceModel.setExpand(true);
                    insuranceModel.setAnim(true);
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
        holder.getBinding().edtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insuranceFragment.onClickState(position);
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

            switch (editText.getId()){
                case R.id.edtCompanyName:
                    if (String.valueOf(mListPatient.get(position).getCompanyName()).equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setCompanyName(charSequence.toString());
                    }
                    break;
                case R.id.edtPolicy:
                    if (mListPatient.get(position).getPolicy().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setPolicy(charSequence.toString());
                    }
                    break;
                case R.id.edtPolicyType:
                    if (mListPatient.get(position).getPolicyType().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setPolicyType(charSequence.toString());
                    }
                    break;
                case R.id.edtPolicyHolder:
                    if (mListPatient.get(position).getPolicyHolder().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setPolicyHolder(charSequence.toString());
                    }
                    break;
                case R.id.edtAddress:
                    if (mListPatient.get(position).getAddress().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setAddress(charSequence.toString());
                    }
                    break;
                case R.id.edtCity:
                    if (mListPatient.get(position).getCity().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setCity(charSequence.toString());
                    }
                    break;
                case R.id.edtZip:
                    if (mListPatient.get(position).getZipCode().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setZipCode(charSequence.toString());
                    }
                    break;
                case R.id.edtEmail:
                    if (mListPatient.get(position).getEmail().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setEmail(charSequence.toString());
                    }
                    break;
                case R.id.edtPhoneNoOne:
                    if (mListPatient.get(position).getPhoneOne().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setPhoneOne(charSequence.toString());
                    }
                    break;
                case R.id.edtPhoneNoTwo:
                    if (mListPatient.get(position).getPhoneTwo().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setPhoneTwo(charSequence.toString());
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
