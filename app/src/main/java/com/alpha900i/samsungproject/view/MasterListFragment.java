package com.alpha900i.samsungproject.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alpha900i.samsungproject.R;

public class MasterListFragment extends Fragment {
    public MasterListFragment() {
        // Required empty public constructor
    }

    public static MasterListFragment newInstance() {
        MasterListFragment fragment = new MasterListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_list, container, false);
        setHasOptionsMenu(true);
        return view;
    }

}
