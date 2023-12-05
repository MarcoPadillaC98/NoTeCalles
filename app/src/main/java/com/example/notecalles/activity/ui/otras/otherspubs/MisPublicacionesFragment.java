package com.example.notecalles.activity.ui.otras.otherspubs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notecalles.R;
import com.example.notecalles.adapter.MisPubsAdapter;
import com.example.notecalles.databinding.FragmentMispublicacionesBinding;
import com.example.notecalles.model.Publicacion;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MisPublicacionesFragment extends Fragment {

    RecyclerView recyclerView;
    MisPubsAdapter mispubsAdapter;

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

        recyclerView = view.findViewById(R.id.rv_mispublicaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Publicacion> options =
                new FirebaseRecyclerOptions.Builder<Publicacion>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("publicaciones").child("todos"), Publicacion.class)
                        .build();
        mispubsAdapter = new MisPubsAdapter(options);
        recyclerView.setAdapter(mispubsAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        mispubsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mispubsAdapter.stopListening();
    }


    }
