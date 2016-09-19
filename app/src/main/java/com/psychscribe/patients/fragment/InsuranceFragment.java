package com.psychscribe.patients.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentInsuranceBinding;
import com.psychscribe.patients.adapter.InsuranceRecyclerAdapter;
import com.psychscribe.patients.model.InsuranceModel;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.fragment.StateDialogFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 22/7/16.
 */
public class InsuranceFragment extends BaseFragment {

    private FragmentInsuranceBinding binding;
    private List<InsuranceModel> mInsuranceModelList = new ArrayList<>();
    private  InsuranceRecyclerAdapter mInsuranceRecyclerAdapter;
    private int mStatePos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_insurance, null, false);
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_done).setVisible(false);
        menu.findItem(R.id.menu_next).setVisible(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerAdapter();
        binding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidateInsuranceList())
                    addInsurance();
            }
        });
    }

    public boolean isValidateInsuranceList() {
        for (int i = 0; i < mInsuranceModelList.size(); i++) {
            if (mInsuranceModelList.get(i).getCompanyName().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.company_name).toLowerCase()));
                return false;
            } else if (mInsuranceModelList.get(i).getPolicy().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.policy_hash).toLowerCase()));
                return false;
            } else if (mInsuranceModelList.get(i).getPolicyType().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.policy_type).toLowerCase()));
                return false;
            } else if (mInsuranceModelList.get(i).getPolicyHolder().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.policy_holder).toLowerCase()));
                return false;
            } else if (mInsuranceModelList.get(i).getAddress().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.address).toLowerCase()));
                return false;
            } else if (mInsuranceModelList.get(i).getCity().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.city).toLowerCase()));
                return false;
            } else if (mInsuranceModelList.get(i).getState().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_select), getString(R.string.state).toLowerCase()));
                return false;
            } else if (mInsuranceModelList.get(i).getZipCode().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.zip_code).toLowerCase()));
                return false;
            } else if (mInsuranceModelList.get(i).getEmail().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.email).toLowerCase()));
                return false;
            } else if (mInsuranceModelList.get(i).getPhoneOne().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.phone1).toLowerCase()));
                return false;
            }
        }
        return true;
    }

    private void addInsurance() {
        mInsuranceModelList.add(getInsuranceModel(mInsuranceModelList.size()+1));
        mInsuranceRecyclerAdapter.notifyItemInserted(mInsuranceModelList.size());
    }

    private void setRecyclerAdapter() {
        mInsuranceModelList = getList();
        mInsuranceRecyclerAdapter = new InsuranceRecyclerAdapter(InsuranceFragment.this, mInsuranceModelList);
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemAnimator itemAnimator = binding.recyclerView.getItemAnimator();
        itemAnimator.setAddDuration(300);
        itemAnimator.setRemoveDuration(300);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(mInsuranceRecyclerAdapter);
    }

    private List<InsuranceModel> getList() {
        List<InsuranceModel> modelArrayList = new ArrayList<>();
        modelArrayList.add(getInsuranceModel(1));
        return modelArrayList;
    }

    InsuranceModel getInsuranceModel(int size){
        InsuranceModel insuranceModel = new InsuranceModel();
        insuranceModel.setInsuranceTitle("Insurance "+size);
        insuranceModel.setCompanyName("");
        insuranceModel.setPolicy("");
        insuranceModel.setAddress("");
        insuranceModel.setCity("");
        insuranceModel.setEmail("");
        insuranceModel.setPhoneOne("");
        insuranceModel.setPhoneTwo("");
        insuranceModel.setPolicyHolder("");
        insuranceModel.setPolicyType("");
        insuranceModel.setState("");
        insuranceModel.setZipCode("");
        return insuranceModel;
    }

    public void onClickState(int position) {
        mStatePos = position;
        openStateDialog();
    }

    void openStateDialog(){
        StateDialogFragment dialogFragment = new StateDialogFragment();
        dialogFragment.setTargetFragment(this, Constants.REQUEST_STATE_DIALOG);
        dialogFragment.show(getChildFragmentManager(), "State");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == Constants.REQUEST_STATE_DIALOG) {
                String stateName = data.getStringExtra(Constants.ExtraStateName);
                mInsuranceModelList.get(mStatePos).setState(stateName);
                mInsuranceRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }

    public JSONArray getSelectedValue() {
        JSONArray insuranceJsonArray = new JSONArray();
        try {
            for (int i = 0; i < mInsuranceModelList.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("address", "" + mInsuranceModelList.get(i).getAddress());
                object.put("city", "" + mInsuranceModelList.get(i).getCity());
                object.put("company", "" + mInsuranceModelList.get(i).getCompanyName());
                object.put("email", "" + mInsuranceModelList.get(i).getEmail());
                object.put("phone_1", "" + mInsuranceModelList.get(i).getPhoneOne());
                object.put("phone_2", "" + mInsuranceModelList.get(i).getPhoneTwo());
                object.put("policy", "" + mInsuranceModelList.get(i).getPolicy());
                object.put("policy_holder", "" + mInsuranceModelList.get(i).getPolicyHolder());
                object.put("policy_type", "" + mInsuranceModelList.get(i).getPolicyType());
                object.put("state", "" + mInsuranceModelList.get(i).getState());
                object.put("zip_code", "" + mInsuranceModelList.get(i).getZipCode());
                insuranceJsonArray.put(object);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return insuranceJsonArray;
    }
}
