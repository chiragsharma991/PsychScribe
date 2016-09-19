package com.psychscribe.patients.fragment;

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
import com.psychscribe.databinding.FragmentCollateralBinding;
import com.psychscribe.patients.adapter.CollateralRecyclerAdapter;
import com.psychscribe.patients.model.CollateralModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 22/7/16.
 */
public class CollateralFragment extends BaseFragment {

    private FragmentCollateralBinding binding;
    private List<CollateralModel> mCollateralModelList = new ArrayList<>();
    private CollateralRecyclerAdapter mCollateralRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_collateral, null, false);
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_next).setVisible(false);
        menu.findItem(R.id.menu_done).setVisible(true);
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
        for (int i = 0; i < mCollateralModelList.size(); i++) {
            if (mCollateralModelList.get(i).getName().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.name).toLowerCase()));
                return false;
            } else if (mCollateralModelList.get(i).getCompany().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.company).toLowerCase()));
                return false;
            } else if (mCollateralModelList.get(i).getPhone().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.phone).toLowerCase()));
                return false;
            } else if (mCollateralModelList.get(i).getFaxNo().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.fax_no).toLowerCase()));
                return false;
            } else if (mCollateralModelList.get(i).getAddress().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.address).toLowerCase()));
                return false;
            } else if (mCollateralModelList.get(i).getAdditionalInfo().equalsIgnoreCase("")) {
                showSnackbar(binding.getRoot(), String.format(getString(R.string.please_enter), getString(R.string.additional_info).toLowerCase()));
                return false;
            }
        }
        return true;
    }

    private void addInsurance() {
        mCollateralModelList.add(getCollateralModel(mCollateralModelList.size()+1));
        mCollateralRecyclerAdapter.notifyItemInserted(mCollateralModelList.size());
    }

    private void setRecyclerAdapter() {
        mCollateralModelList = getList();
        mCollateralRecyclerAdapter = new CollateralRecyclerAdapter(getContext(), mCollateralModelList);
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemAnimator itemAnimator = binding.recyclerView.getItemAnimator();
        itemAnimator.setAddDuration(300);
        itemAnimator.setRemoveDuration(300);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(mCollateralRecyclerAdapter);
    }

    private List<CollateralModel> getList() {
        List<CollateralModel> modelArrayList = new ArrayList<>();
        modelArrayList.add(getCollateralModel(1));
        return modelArrayList;
    }

    CollateralModel getCollateralModel(int size){
        CollateralModel collateralModel = new CollateralModel();
        collateralModel.setCollateralName("Collaterals "+size);
        collateralModel.setName("");
        collateralModel.setCompany("");
        collateralModel.setAddress("");
        collateralModel.setPhone("");
        collateralModel.setAdditionalInfo("");
        collateralModel.setFaxNo("");
        return collateralModel;
    }

    public JSONArray getSelectedValue() {
        JSONArray collateralJsonArray = new JSONArray();
        try {
            for (int i = 0; i < mCollateralModelList.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("additional_info", "" + mCollateralModelList.get(i).getAdditionalInfo());
                object.put("address", "" + mCollateralModelList.get(i).getAddress());
                object.put("company", "" + mCollateralModelList.get(i).getCompany());
                object.put("fax_number", "" + mCollateralModelList.get(i).getFaxNo());
                object.put("name", "" + mCollateralModelList.get(i).getName());
                object.put("phone", "" + mCollateralModelList.get(i).getPhone());
                collateralJsonArray.put(object);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return collateralJsonArray;
    }
}
