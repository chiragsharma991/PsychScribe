package com.psychscribe.notes.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psychscribe.R;
import com.psychscribe.base.BaseFragment;
import com.psychscribe.databinding.FragmentNotesViewBinding;

/**
 * Created by ubuntu on 3/8/16.
 */
public class ProgressNoteFragment extends BaseFragment {

    FragmentNotesViewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes_view, null, false);
        return binding.getRoot();
    }
}
