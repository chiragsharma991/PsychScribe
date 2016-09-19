package com.psychscribe.utiz.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.psychscribe.R;
import com.psychscribe.databinding.DialogStateBinding;
import com.psychscribe.model.StateModel;
import com.psychscribe.patients.PatientDetailActivity;
import com.psychscribe.sqlite.StateHandler;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.RecyclerTouchListener;
import com.psychscribe.utiz.fragment.adapter.StateRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 26/7/16.
 */
public class StateDialogFragment extends DialogFragment {

    DialogStateBinding binding;
    private StateHandler stateHandler;
    private StateRecyclerAdapter stateRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_state, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDialog();
        initToolbar();
        stateHandler = new StateHandler();
        setRecyclerAdapter();

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (stateRecyclerAdapter != null) {
                    stateRecyclerAdapter.setList(filter(s.toString().trim().toUpperCase()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(binding.recyclerView, new RecyclerTouchListener.OnRecyclerClickListener() {
            @Override
            public void onClick(View v, int position) {
                if(stateRecyclerAdapter != null) {
                    dismissDialog(stateRecyclerAdapter.getSelectedState(position));
                }
            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));
    }

    public List<StateModel.StateData> filter(String search) {
        List<StateModel.StateData> arrayList = new ArrayList<>();
        List<StateModel.StateData> countryList = stateHandler.getAllState();
        for (int i = 0; i < countryList.size(); i++){
            if(countryList.get(i).getName().trim().toUpperCase().contains(search)){
                arrayList.add(countryList.get(i));
            }
        }
        return arrayList;
    }

    private void initToolbar() {
        binding.includeToolbar.toolbarTitle.setText(getString(R.string.select_state));
        binding.includeToolbar.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.includeToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private void initDialog() {
        getDialog().getWindow().getAttributes().windowAnimations = R.style.ThemeDialogFit;
        WindowManager.LayoutParams wmlp = getDialog().getWindow().getAttributes();
        wmlp.gravity = Gravity.FILL_HORIZONTAL;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void setRecyclerAdapter() {
        stateRecyclerAdapter = new StateRecyclerAdapter(getContext(), stateHandler.getAllState());
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(stateRecyclerAdapter);
    }

    private void dismissDialog(String stateName) {
        Intent intent = new Intent();
        intent.putExtra(Constants.ExtraStateName, stateName);
        getTargetFragment().onActivityResult(Constants.REQUEST_STATE_DIALOG, Activity.RESULT_OK, intent);
        dismiss();
    }
}
