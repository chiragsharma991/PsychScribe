package com.psychscribe.notes.fragment;

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

import com.psychscribe.BR;
import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentNotesListBinding;
import com.psychscribe.databinding.FragmentNotesViewBinding;
import com.psychscribe.notes.NotesDetailActivity;
import com.psychscribe.notes.NotesListActivity;
import com.psychscribe.notes.model.NotesListModel;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.binding.RecyclerBindingAdapter;

import java.util.ArrayList;

/**
 * Created by ubuntu on 3/8/16.
 */
public class AddNotesListFragment extends BaseFragment {

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
        setRecyclerAdapter();
    }

    private void setRecyclerAdapter() {
        RecyclerBindingAdapter adapter = new RecyclerBindingAdapter<>(R.layout.list_item_add_notes_list, BR.notesListModel, getList());
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                moveAddNotesFragment(position);
            }
        });
    }

    private void moveAddNotesFragment(int position) {
        ((NotesListActivity)getActivity()).binding.includeToolbar.toolbarTitle.setText(getString(R.string.notes));
        switch (position){
            case 0:
                addFragmentWithAnim(new InitialIntakeFragment(), Constants.KEY_INITIAL_INTAKE_FRAGMENT);
                break;
            case 1:
                addFragmentWithAnim(new MentalStatusExamFragment(), Constants.KEY_MENTAL_STATUS_EXAM_FRAGMENT);
                break;
            case 2:
                addFragmentWithAnim(new InitialIntakeFragment(), Constants.KEY_INITIAL_INTAKE_FRAGMENT);
                break;
            case 3:
                addFragmentWithAnim(new InitialIntakeFragment(), Constants.KEY_INITIAL_INTAKE_FRAGMENT);
                break;
            case 4:
                addFragmentWithAnim(new InitialIntakeFragment(), Constants.KEY_INITIAL_INTAKE_FRAGMENT);
                break;
            case 5:
                addFragmentWithAnim(new InitialIntakeFragment(), Constants.KEY_INITIAL_INTAKE_FRAGMENT);
                break;
            case 6:
                addFragmentWithAnim(new InitialIntakeFragment(), Constants.KEY_INITIAL_INTAKE_FRAGMENT);
                break;
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_search).setVisible(false);
        menu.findItem(R.id.menu_add_notes).setVisible(false);

    }

    private ArrayList<NotesListModel> getList() {
        ArrayList<NotesListModel> list = new ArrayList<>();
        NotesListModel  notesModel= new NotesListModel();
        notesModel.setName("INITIAL INTAKE REPORT");
        notesModel.setImage(R.drawable.ic_initial_intake_note);
        list.add(notesModel);

        notesModel= new NotesListModel();
        notesModel.setName("MENTAL STATUS EXAM");
        notesModel.setImage(R.drawable.ic_mental_note);
        list.add(notesModel);

        notesModel= new NotesListModel();
        notesModel.setName("PROGRESS NOTES");
        notesModel.setImage(R.drawable.ic_progress_note);
        list.add(notesModel);

        notesModel= new NotesListModel();
        notesModel.setName("PSYCHOTHERAPY NOTE");
        notesModel.setImage(R.drawable.ic_psychotherapy_note);
        list.add(notesModel);

        notesModel= new NotesListModel();
        notesModel.setName("SOAP NOTE");
        notesModel.setImage(R.drawable.ic_psychotherapy_note);
        list.add(notesModel);

        notesModel= new NotesListModel();
        notesModel.setName("PLAY ASSESSMENT SUMMARY");
        notesModel.setImage(R.drawable.ic_play_assessement_note);
        list.add(notesModel);

        notesModel= new NotesListModel();
        notesModel.setName("SANDTRAY THERAPY SUMMARY");
        notesModel.setImage(R.drawable.ic_sand_note);
        list.add(notesModel);

        return list;
    }
}
