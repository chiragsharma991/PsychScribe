package com.psychscribe.developmentalHistory.fragment;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.BR;
import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentCollateralBinding;
import com.psychscribe.databinding.FragmentEarlyHistoryBinding;
import com.psychscribe.developmentalHistory.adapter.EarlyHistoryQueRecyclerAdapter;
import com.psychscribe.developmentalHistory.adapter.EarlyHistorySectionRecyclerAdapter;
import com.psychscribe.developmentalHistory.model.QuestionModel;
import com.psychscribe.patients.adapter.CollateralRecyclerAdapter;
import com.psychscribe.patients.model.CollateralModel;
import com.psychscribe.utiz.AnimationUtils;
import com.psychscribe.utiz.binding.RecyclerBindingAdapter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 30/7/16.
 */
public class EarlyHistoryFragment extends BaseFragment {

    private FragmentEarlyHistoryBinding binding;
    private EarlyHistoryQueRecyclerAdapter earlyHistoryQueRecyclerAdapter;
    private EarlyHistorySectionRecyclerAdapter earlyHistorySectionRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_early_history, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            binding.recyclerViewQuestion.setNestedScrollingEnabled(false);
            binding.recyclerViewSection.setNestedScrollingEnabled(false);
        }
        setQuestionRecyclerAdapter();
        setSectionRecyclerAdapter();
    }

    private void setQuestionRecyclerAdapter() {
        earlyHistoryQueRecyclerAdapter = new EarlyHistoryQueRecyclerAdapter(getContext(), getQuestionList());
        binding.recyclerViewQuestion.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewQuestion.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewQuestion.setLayoutManager(mLayoutManager);
        binding.recyclerViewQuestion.setAdapter(earlyHistoryQueRecyclerAdapter);
    }

    private List<QuestionModel> getQuestionList() {
        List<QuestionModel> list = new ArrayList<>();
        QuestionModel questionModel = new QuestionModel();
        questionModel.types = "1";
        list.add(questionModel);

        questionModel = new QuestionModel();
        questionModel.types = "2";
        list.add(questionModel);

        questionModel = new QuestionModel();
        questionModel.types = "3";
        list.add(questionModel);

        questionModel = new QuestionModel();
        questionModel.types = "4";
        list.add(questionModel);

        questionModel = new QuestionModel();
        questionModel.types = "1";
        list.add(questionModel);

        questionModel = new QuestionModel();
        questionModel.types = "5";
        list.add(questionModel);

        questionModel = new QuestionModel();
        questionModel.types = "3";
        list.add(questionModel);

        questionModel = new QuestionModel();
        questionModel.types = "1";
        list.add(questionModel);

        questionModel = new QuestionModel();
        questionModel.types = "4";
        list.add(questionModel);

        questionModel = new QuestionModel();
        questionModel.types = "3";
        list.add(questionModel);

        questionModel = new QuestionModel();
        questionModel.types = "2";
        list.add(questionModel);

        return list;
    }

    private void setSectionRecyclerAdapter() {
        earlyHistorySectionRecyclerAdapter = new EarlyHistorySectionRecyclerAdapter(getContext(), getQuestionList());
        binding.recyclerViewSection.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewSection.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewSection.setLayoutManager(mLayoutManager);
        binding.recyclerViewSection.setAdapter(earlyHistorySectionRecyclerAdapter);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_next).setVisible(true);
    }

}
