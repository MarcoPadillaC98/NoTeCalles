package com.example.notecalles.activity.ui.mispublicaciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.notecalles.databinding.FragmentMispublicacionesBinding;

public class MisPublicacionesFragment extends Fragment {

    private FragmentMispublicacionesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MisPublicacionesViewModel homeViewModel =
                new ViewModelProvider(this).get(MisPublicacionesViewModel.class);

        binding = FragmentMispublicacionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}