package com.example.notecalles.activity.ui.otras.otherspubs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notecalles.R;
import com.example.notecalles.activity.ui.mispublicaciones.MisPublicacionesFragment;
import com.example.notecalles.adapter.MisPubsAdapter;
import com.example.notecalles.adapter.OthersPubsAdapter;
import com.example.notecalles.databinding.FragmentMispublicacionesBinding;
import com.example.notecalles.model.Publicacion;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class OthersPublicacionesFragment extends Fragment {

    RecyclerView recyclerView;
    OthersPubsAdapter otherAdapter;

    EditText username;

   private FragmentMispublicacionesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        OthersPublicacionesViewModel homeViewModel =
                new ViewModelProvider(this).get(OthersPublicacionesViewModel.class);

        binding = FragmentMispublicacionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_mispublicaciones);
        EditText edtusername = view.findViewById(R.id.etUsernameCrear);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listar();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        otherAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        otherAdapter.stopListening();
    }

    void listar(){



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Publicacion> options =
                new FirebaseRecyclerOptions.Builder<Publicacion>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("publicaciones").child("todos"), Publicacion.class)
                        .build();
        otherAdapter = new OthersPubsAdapter(options);
        recyclerView.setAdapter(otherAdapter);
    }


    }
