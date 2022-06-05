package com.alpha900i.samsungproject.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alpha900i.samsungproject.R;
import com.alpha900i.samsungproject.view_model.MasterListViewModel;

public class MasterListFragment extends Fragment {
    LogAdapter adapter;

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

        RecyclerView masterList = view.findViewById(R.id.master_list_recycler);
        masterList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new LogAdapter(requireContext());
        masterList.setAdapter(adapter);

        setupViewModel();

        return view;
    }

    private void setupViewModel() {
        MasterListViewModel viewModel = new ViewModelProvider(this).get(MasterListViewModel.class);
        viewModel.getList().observe(getViewLifecycleOwner(), list -> adapter.initialize(list));
    }
}
