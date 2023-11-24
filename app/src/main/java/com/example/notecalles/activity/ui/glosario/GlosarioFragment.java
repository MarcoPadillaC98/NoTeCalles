package com.example.notecalles.activity.ui.glosario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.notecalles.R;
import com.example.notecalles.databinding.FragmentGlosarioBinding;

public class GlosarioFragment extends Fragment {

    Button laboral,callejero,ciberacoso;


    private FragmentGlosarioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GlosarioViewModel galleryViewModel =
                new ViewModelProvider(this).get(GlosarioViewModel.class);

        binding = FragmentGlosarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button laboral=view.findViewById(R.id.btn_Laboral);
        Button callejero=view.findViewById(R.id.btn_Callejero);
        Button ciberacoso=view.findViewById(R.id.btn_Ciberacoso);


        laboral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.g_LaboralActivity);
            }
        });

        callejero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.g_CallejeroActivity);
            }
        });

        ciberacoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.g_CiberacosoActivity);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}