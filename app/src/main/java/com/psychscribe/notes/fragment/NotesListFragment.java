package com.psychscribe.notes.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.BR;
import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentNotesListBinding;
import com.psychscribe.databinding.FragmentNotesViewBinding;
import com.psychscribe.notes.NotesDetailActivity;
import com.psychscribe.notes.model.NotesListModel;
import com.psychscribe.utiz.binding.RecyclerBindingAdapter;

import java.util.ArrayList;

/**
 * Created by ubuntu on 3/8/16.
 */
public class NotesListFragment extends BaseFragment {

    FragmentNotesListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes_list, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setList(getList());
        binding.setModel(BR.notesListModel);
        binding.setListener(new RecyclerBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                Log.d("NotesListFragment", "onItemClick() called with: " + "position = [" + position + "], item = [" + item + "]");
            }
        });
        //setRecyclerAdapter();
    }

    void setRecyclerAdapter(){
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            binding.recyclerView.setNestedScrollingEnabled(false);
            binding.recyclerViewAnother.setNestedScrollingEnabled(false);
        }
        RecyclerBindingAdapter adapter = new RecyclerBindingAdapter<>(R.layout.list_item_notes, BR.notesListModel, getList());
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                showToast(""+position);
               // moveActivity(new Intent(getActivity(), NotesDetailActivity.class), getActivity());
            }
        });



        RecyclerBindingAdapter adapter1 = new RecyclerBindingAdapter<>(R.layout.list_item_notes, BR.notesListModel, getList());
        binding.recyclerViewAnother.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewAnother.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewAnother.setLayoutManager(mLayoutManager1);
        binding.recyclerViewAnother.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new RecyclerBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
               showToast(""+position);
            }
        });
    }

    private ArrayList<NotesListModel> getList() {
        ArrayList<NotesListModel> list = new ArrayList<>();
        NotesListModel  patientModel= new NotesListModel();
        patientModel.setName("hjii");
        list.add(patientModel);

        patientModel= new NotesListModel();
        patientModel.setName("hjii");
        list.add(patientModel);

        patientModel= new NotesListModel();
        patientModel.setName("Woogg");
        list.add(patientModel);

        patientModel= new NotesListModel();
        patientModel.setName("Lissss");
        list.add(patientModel);

        patientModel= new NotesListModel();
        patientModel.setName("hjii");
        list.add(patientModel);

        patientModel= new NotesListModel();
        patientModel.setName("hjii");
        list.add(patientModel);

        patientModel= new NotesListModel();
        patientModel.setName("hhasss");
        list.add(patientModel);

        patientModel= new NotesListModel();
        patientModel.setName("hjii");
        list.add(patientModel);

        patientModel= new NotesListModel();
        patientModel.setName("hjii");
        list.add(patientModel);

        patientModel= new NotesListModel();
        patientModel.setName("hjii");
        list.add(patientModel);

        patientModel= new NotesListModel();
        patientModel.setName("hjii");
        list.add(patientModel);

        patientModel= new NotesListModel();
        patientModel.setName("Last");
        list.add(patientModel);
        return list;
    }

}
