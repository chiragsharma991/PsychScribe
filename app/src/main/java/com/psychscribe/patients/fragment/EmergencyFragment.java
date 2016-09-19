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
import com.psychscribe.databinding.FragmentEmergencyBinding;
import com.psychscribe.patients.adapter.EmergencyRecyclerAdapter;
import com.psychscribe.patients.model.EmergencyModel;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.fragment.StateDialogFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 22/7/16.
 */
public class EmergencyFragment extends BaseFragment {

    private FragmentEmergencyBinding binding;
    private List<EmergencyModel> mEmergencyModelList = new ArrayList<>();
    private EmergencyRecyclerAdapter mEmergencyRecyclerAdapter;
    private int mStatePos = 0 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_emergency, null, false);
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
        for (int i = 0; i < mEmergencyModelList.size(); i++) {
            if(mEmergencyModelList.get(i).getFirstName().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), getString(R.string.error_enter_first_name));
                return false;
            } else if(mEmergencyModelList.get(i).getLastName().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), getString(R.string.error_enter_last_name));
                return false;
            }
            else if(mEmergencyModelList.get(i).getMiddleInitial().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), getString(R.string.error_enter_middle_initial));
                return false;
            }
            else if(mEmergencyModelList.get(i).getRelationShip().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.relationship).toLowerCase()));
                return false;
            }
            else if(mEmergencyModelList.get(i).getAddress().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.address).toLowerCase()));
                return false;
            }
            else if(mEmergencyModelList.get(i).getCity().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.city).toLowerCase()));
                return false;
            }
            else if(mEmergencyModelList.get(i).getState().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_select), getString(R.string.state).toLowerCase()));
                return false;
            }
            else if(mEmergencyModelList.get(i).getZipCode().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.zip_code).toLowerCase()));
                return false;
            }
            else if(mEmergencyModelList.get(i).getEmail().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.email).toLowerCase()));
                return false;
            }
            else if(mEmergencyModelList.get(i).getPhoneOne().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.phone1).toLowerCase()));
                return false;
            }
        }
        return true;
    }

    private void addInsurance() {
        mEmergencyModelList.add(getEmergencyModel(mEmergencyModelList.size()+1));
        mEmergencyRecyclerAdapter.notifyItemInserted(mEmergencyModelList.size());
    }

    private void setRecyclerAdapter() {
        mEmergencyModelList = getList();
        mEmergencyRecyclerAdapter = new EmergencyRecyclerAdapter(EmergencyFragment.this, mEmergencyModelList);
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemAnimator itemAnimator = binding.recyclerView.getItemAnimator();
        itemAnimator.setAddDuration(300);
        itemAnimator.setRemoveDuration(300);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(mEmergencyRecyclerAdapter);
    }

    private List<EmergencyModel> getList() {
        List<EmergencyModel> modelArrayList = new ArrayList<>();
        modelArrayList.add(getEmergencyModel(1));
        return modelArrayList;
    }

    EmergencyModel getEmergencyModel(int size){
        EmergencyModel emergencyModel = new EmergencyModel();
        emergencyModel.setEmergencyContact("Contact - "+size);
        emergencyModel.setFirstName("");
        emergencyModel.setLastName("");
        emergencyModel.setMiddleInitial("");
        emergencyModel.setAddress("");
        emergencyModel.setCity("");
        emergencyModel.setEmail("");
        emergencyModel.setPhoneOne("");
        emergencyModel.setPhoneTwo("");
        emergencyModel.setRelationShip("");
        emergencyModel.setState("");
        emergencyModel.setZipCode("");
        return emergencyModel;
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
                mEmergencyModelList.get(mStatePos).setState(stateName);
                mEmergencyRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }

    public JSONArray getSelectedValue() {
        JSONArray emergencyJsonArray = new JSONArray();
        try {
            for (int i = 0; i < mEmergencyModelList.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("address", "" + mEmergencyModelList.get(i).getAddress());
                object.put("city", "" +  mEmergencyModelList.get(i).getCity());
                object.put("email", "" +  mEmergencyModelList.get(i).getEmail());
                object.put("first_name", "" +  mEmergencyModelList.get(i).getFirstName());
                object.put("last_name", "" +  mEmergencyModelList.get(i).getLastName());
                object.put("middle_initial", "" +  mEmergencyModelList.get(i).getMiddleInitial());
                object.put("phone_1", "" +  mEmergencyModelList.get(i).getPhoneOne());
                object.put("phone_2", "" +  mEmergencyModelList.get(i).getPhoneTwo());
                object.put("relatinship", "" +  mEmergencyModelList.get(i).getRelationShip());
                object.put("state", "" +  mEmergencyModelList.get(i).getState());
                object.put("zip_code", "" +  mEmergencyModelList.get(i).getZipCode());

                emergencyJsonArray.put(object);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return emergencyJsonArray;
    }
}
