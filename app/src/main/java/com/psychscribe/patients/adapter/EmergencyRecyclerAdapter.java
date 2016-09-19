package com.psychscribe.patients.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.psychscribe.R;
import com.psychscribe.databinding.ListItemEmergencyBinding;
import com.psychscribe.patients.fragment.EmergencyFragment;
import com.psychscribe.patients.model.EmergencyModel;
import com.psychscribe.utiz.AnimationUtils;

import java.util.List;

/**
 * Created by ubuntu on 19/4/16.
 */
public class EmergencyRecyclerAdapter extends RecyclerView.Adapter<EmergencyRecyclerAdapter.ViewHolder> {

    private List<EmergencyModel> mListPatient;
    private EmergencyFragment emergencyFragment;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemEmergencyBinding binding;
        public MyCustomEditTextListener listenerCompany, listenerLastname, listenerMiddle, listenerRelationShip, listenerAddress,
                listenerCity, listenerZipCode, listenerEmail, listenerPhoneOne, listenerPhoneTwo;

        public ViewHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);

            this.listenerCompany = new MyCustomEditTextListener(binding.edtFirstName);
            this.listenerLastname = new MyCustomEditTextListener(binding.edtLastName);
            this.listenerMiddle = new MyCustomEditTextListener(binding.edtMiddleInitial);
            this.listenerRelationShip = new MyCustomEditTextListener(binding.edtRelationship);
            this.listenerAddress = new MyCustomEditTextListener(binding.edtAddress);
            this.listenerCity = new MyCustomEditTextListener(binding.edtCity);
            this.listenerZipCode = new MyCustomEditTextListener(binding.edtZip);
            this.listenerEmail = new MyCustomEditTextListener(binding.edtEmail);
            this.listenerPhoneOne = new MyCustomEditTextListener(binding.edtPhoneNoOne);
            this.listenerPhoneTwo = new MyCustomEditTextListener(binding.edtPhoneNoTwo);

            binding.edtFirstName.addTextChangedListener(listenerCompany);
            binding.edtLastName.addTextChangedListener(listenerLastname);
            binding.edtMiddleInitial.addTextChangedListener(listenerMiddle);
            binding.edtRelationship.addTextChangedListener(listenerRelationShip);
            binding.edtAddress.addTextChangedListener(listenerAddress);
            binding.edtCity.addTextChangedListener(listenerCity);
            binding.edtZip.addTextChangedListener(listenerZipCode);
            binding.edtEmail.addTextChangedListener(listenerEmail);
            binding.edtPhoneNoOne.addTextChangedListener(listenerPhoneOne);
            binding.edtPhoneNoTwo.addTextChangedListener(listenerPhoneTwo);
        }
        public ListItemEmergencyBinding getBinding() {
            return binding;
        }
    }

    public EmergencyRecyclerAdapter(EmergencyFragment emergencyFragment, List<EmergencyModel> mListPatient) {
        this.mListPatient = mListPatient;
        this.emergencyFragment = emergencyFragment;
    }

    @Override
    public EmergencyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_emergency, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final EmergencyModel emergencyModel = mListPatient.get(position);
        holder.getBinding().setEmergencyModel(emergencyModel);
        holder.getBinding().executePendingBindings();

        holder.listenerCompany.updatePosition(position);
        holder.listenerLastname.updatePosition(position);
        holder.listenerMiddle.updatePosition(position);
        holder.listenerRelationShip.updatePosition(position);
        holder.listenerAddress.updatePosition(position);
        holder.listenerCity.updatePosition(position);
        holder.listenerZipCode.updatePosition(position);
        holder.listenerEmail.updatePosition(position);
        holder.listenerPhoneOne.updatePosition(position);
        holder.listenerPhoneTwo.updatePosition(position);

        holder.getBinding().edtFirstName.setText(emergencyModel.getFirstName());
        holder.getBinding().edtLastName.setText(emergencyModel.getLastName());
        holder.getBinding().edtMiddleInitial.setText(emergencyModel.getMiddleInitial());
        holder.getBinding().edtRelationship.setText(emergencyModel.getRelationShip());
        holder.getBinding().edtAddress.setText(emergencyModel.getAddress());
        holder.getBinding().edtCity.setText(emergencyModel.getCity());
        holder.getBinding().edtZip.setText(emergencyModel.getZipCode());
        holder.getBinding().edtEmail.setText(emergencyModel.getEmail());
        holder.getBinding().edtPhoneNoOne.setText(emergencyModel.getPhoneOne());
        holder.getBinding().edtPhoneNoTwo.setText(emergencyModel.getPhoneTwo());
        holder.getBinding().edtState.setText(emergencyModel.getState());

        if(emergencyModel.isAnim()) {
            AnimationUtils.expand(holder.getBinding().linChild);
            emergencyModel.setAnim(false);
        }
        holder.getBinding().imgExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emergencyModel.isExpand()){
                    emergencyModel.setExpand(false);
                    notifyDataSetChanged();
                }else {
                    emergencyModel.setExpand(true);
                    emergencyModel.setAnim(true);
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
                emergencyFragment.onClickState(position);
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
                case R.id.edtFirstName:
                    if (String.valueOf(mListPatient.get(position).getFirstName()).equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setFirstName(charSequence.toString());
                    }
                    break;
                case R.id.edtLastName:
                    if (mListPatient.get(position).getLastName().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setLastName(charSequence.toString());
                    }
                    break;
                case R.id.edtMiddleInitial:
                    if (mListPatient.get(position).getMiddleInitial().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setMiddleInitial(charSequence.toString());
                    }
                    break;
                case R.id.edtRelationship:
                    if (mListPatient.get(position).getRelationShip().equals(charSequence.toString())){
                        return;
                    }else {
                        mListPatient.get(position).setRelationShip(charSequence.toString());
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
