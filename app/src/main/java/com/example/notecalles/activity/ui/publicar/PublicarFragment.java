package com.example.notecalles.activity.ui.publicar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.notecalles.R;
import com.example.notecalles.databinding.FragmentPublicarBinding;
import com.example.notecalles.model.Tipo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PublicarFragment extends Fragment{

    Button Calendario;
    Spinner spn_tipos;
    EditText fecha;

    FirebaseDatabase database;


    private int dia,mes,ano;
    private FragmentPublicarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PublicarViewModel slideshowViewModel =
                new ViewModelProvider(this).get(PublicarViewModel.class);

        binding = FragmentPublicarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button Calendario = view.findViewById(R.id.imb_Calendario);
        EditText fecha=view.findViewById(R.id.etFecha);
        final Spinner spn_tipos = view.findViewById(R.id.spn_Elegirtipo);

        database = FirebaseDatabase.getInstance();
        DatabaseReference ref1 = database.getReference("Tipos");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.combo_tipos, android.R.layout.simple_spinner_item);
        spn_tipos.setAdapter(adapter);


        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> tipos = new ArrayList<>();
                for(DataSnapshot tiposSnapshot : snapshot.getChildren()){
                    String tipoName = tiposSnapshot.child("nombre").getValue(String.class);
                }
                ArrayAdapter<String> tiposAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,tipos);
                tiposAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                spn_tipos.setAdapter(tiposAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        final NavController navController = Navigation.findNavController(view);

        Calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==Calendario){
                    final Calendar c = Calendar.getInstance();
                    dia = c.get(Calendar.DAY_OF_MONTH);
                    mes = c.get(Calendar.MONTH);
                    ano = c.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            fecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        }
                    },dia,mes,ano);
                    datePickerDialog.show();

                }

            }
        });

    }



}