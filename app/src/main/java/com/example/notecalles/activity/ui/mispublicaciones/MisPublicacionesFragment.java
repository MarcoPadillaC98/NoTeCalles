package com.example.notecalles.activity.ui.mispublicaciones;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.notecalles.R;
import com.example.notecalles.databinding.FragmentMispublicacionesBinding;

import java.util.Calendar;

public class MisPublicacionesFragment extends Fragment {

   private FragmentMispublicacionesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MisPublicacionesViewModel homeViewModel =
                new ViewModelProvider(this).get(MisPublicacionesViewModel.class);

        binding = FragmentMispublicacionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}