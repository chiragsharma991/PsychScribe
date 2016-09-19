package com.psychscribe.patients.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentPatientBinding;
import com.psychscribe.patients.AddPatientActivity;
import com.psychscribe.patients.PatientDetailActivity;
import com.psychscribe.patients.adapter.PatientRecyclerAdapter;
import com.psychscribe.patients.model.PatientModel;
import com.psychscribe.rest.APIRequest;
import com.psychscribe.rest.ResponseInterface;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 20/7/16.
 */
public class PatientListFragment extends BaseFragment {

    private FragmentPatientBinding binding;
    private PatientRecyclerAdapter patientRecyclerAdapter;
    private List<PatientModel.PatientData> mPatientList;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_patient, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPatientList();
        //setRecyclerAdapter(response.body().getData());
        binding.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(binding.recyclerView, new RecyclerTouchListener.OnRecyclerClickListener() {
            @Override
            public void onClick(View v, int position) {
                if(patientRecyclerAdapter != null) {
                    Intent intent = new Intent(getActivity(), PatientDetailActivity.class);
                    intent.putExtra(Constants.ExtraPatient, patientRecyclerAdapter.getPatientList().get(position));
                    transitionToActivity(intent, getActivity(), v.findViewById(R.id.img_patient));
                }
            }
            @Override
            public void onLongClick(View v, int position) {

            }
        }));
    }

    private void setRecyclerAdapter(List<PatientModel.PatientData> mList) {
        mPatientList = new ArrayList<>();
        mPatientList.addAll(mList);
        patientRecyclerAdapter = new PatientRecyclerAdapter(getContext(), filterListForSticky(mList));
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(patientRecyclerAdapter);
    }


    private List<PatientModel.PatientData> filterListForSticky(List<PatientModel.PatientData> list){
        String mSticky = "";
        for (int i = 0; i < list.size(); i++) {
            if(mSticky.equals("")){
                list.get(i).setStickyVisible(true);
                mSticky = String.valueOf(list.get(i).getFirstName().charAt(0));
            }else{
                if(mSticky.equalsIgnoreCase(String.valueOf(list.get(i).getFirstName().charAt(0)))){
                    list.get(i).setStickyVisible(false);
                }else{
                    mSticky = list.get(i).getFirstName().substring(0, 1);
                    list.get(i).setStickyVisible(true);
                }
            }
        }
        return list;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_search).setVisible(true);
        menu.findItem(R.id.menu_add_patient).setVisible(true);
    }

    void getPatientList(){
        if(isOnline(getContext())){
            showProgressDialog();
            ResponseInterface userDataRequest = APIRequest.provideInterface();
            HashMap<String, String> data = new HashMap<>();
            data.put(Constants.Token, SharedPreferenceUtil.getString(Constants.KEY_TOKEN, ""));
            Call<PatientModel> getUserCall = userDataRequest.getPatientList(SharedPreferenceUtil.getString(Constants.KEY_TOKEN, ""));

            getUserCall.enqueue(new Callback<PatientModel>() {
                @Override
                public void onResponse(Call<PatientModel> call,
                                       Response<PatientModel> response) {
                    stopProgressDialog();
                    if (response.isSuccessful()) {
                        if(response.body().getData().size() > 0){
                            setRecyclerAdapter(response.body().getData());
                        }else{
                            moveActivity(new Intent(getActivity(), AddPatientActivity.class), getActivity());
                        }
                    }
                }
                @Override
                public void onFailure(Call<PatientModel> call, Throwable t) {
                    showErrorLog("Error "+t.getMessage());
                    stopProgressDialog();
                }
            });

        }else{
            showAlertDialog(getString(R.string.alert), getString(R.string.error_internet_connect));
        }
    }

    public void onSearch(String newText) {
        if(newText.equals("")){
            if(patientRecyclerAdapter != null){
                patientRecyclerAdapter.setPatientList(filterListForSticky(mPatientList));
            }
        }else{
            if(patientRecyclerAdapter != null){
                List<PatientModel.PatientData> mListPatient = new ArrayList<>();
                for (int i = 0; i < mPatientList.size(); i++) {
                    if(mPatientList.get(i).getFirstName().toLowerCase().contains(newText.toLowerCase())){
                        mListPatient.add(mPatientList.get(i));
                    }
                }
                patientRecyclerAdapter.setPatientList(filterListForSticky(mListPatient));
            }
        }
    }
}
