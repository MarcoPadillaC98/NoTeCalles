package com.example.notecalles.activity.ui.publicar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.notecalles.LoginActivity;
import com.example.notecalles.R;
import com.example.notecalles.RegistroActivity;
import com.example.notecalles.activity.UbicacionActivity;
import com.example.notecalles.databinding.FragmentPublicarBinding;
import com.example.notecalles.model.Publicacion;
import com.example.notecalles.model.Tipo;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PublicarFragment extends Fragment{

    Button Calendario,btnEntrarUbicacion,btn_SavePub,btn_Cancelar,btn_Imagen;
    Spinner spn_tipos;
    EditText edttipo, edthecho,inv_lat,inv_long,edtfecha,imagen,edtusername;

    FirebaseUser current;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    private int dia,mes,ano;
    //Guardar imagen
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 1;
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

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        mStorage = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        current = mAuth.getCurrentUser();

        Calendario = view.findViewById(R.id.imb_Calendario);
        btnEntrarUbicacion = view.findViewById(R.id.btn_IngresarMaps);
        btn_SavePub = view.findViewById(R.id.btn_GuardarPub);
        btn_Cancelar = view.findViewById(R.id.btn_CancelarPub);
        btn_Imagen = view.findViewById(R.id.btn_IngresarImagen);

        CheckBox checkBox = view.findViewById(R.id.chk_Anonimo);

        final Spinner spn_tipos = view.findViewById(R.id.spn_Elegirtipo);

        edttipo = view.findViewById(R.id.et_sp);
        edthecho = view.findViewById(R.id.txtHecho);
        inv_lat = view.findViewById(R.id.et_lat);
        inv_long = view.findViewById(R.id.et_lon);
        //imagen = view.findViewById(R.id.btn_IngresarImagen);
        edtfecha=view.findViewById(R.id.etFecha);
        edtusername = view.findViewById(R.id.etUsernameCrear);
        edtusername.setText(current.getEmail());



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox.isChecked()){
                    edtusername.setText("Anonimo");
                }else{
                    edtusername.setText(current.getEmail());
                }
            }
        });
        btn_Imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);

            }
        });

        if(getArguments()!=null){
            inv_lat.setText(this.getArguments().getString("lat"));
            inv_long.setText(this.getArguments().getString("lon"));
        }

        guardarDatos();

        //DatabaseReference ref1 = database.getReference("Tipos");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.combo_tipos, android.R.layout.simple_spinner_item);
        spn_tipos.setAdapter(adapter);

        spn_tipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                edttipo.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnEntrarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.ubicacionActivity);
            }
        });


        /*ref1.addValueEventListener(new ValueEventListener() {
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
        });*/

        //final NavController navController = Navigation.findNavController(view);

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
                            edtfecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        }
                    },dia,mes,ano);
                    datePickerDialog.show();

                }

            }
        });

    }

    private void guardarDatos() {
        btn_SavePub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tipo  = edttipo.getText().toString().trim();
                String hecho = edthecho.getText().toString().trim();
                String latitud = inv_lat.getText().toString().trim();
                String longitud = inv_long.getText().toString().trim();
                String fecha = edtfecha.getText().toString().trim();
                String username = edtusername.getText().toString().trim();
                btn_Imagen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,GALLERY_INTENT);

                    }
                });


                if(TextUtils.isEmpty(hecho)){
                    Toast.makeText(getActivity(),"Ingrese la descripcion",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(fecha)) {
                    Toast.makeText(getActivity(), "Ingrese la fecha", Toast.LENGTH_LONG).show();
                }else {
                    Publicacion publicacion = new Publicacion(tipo,hecho,fecha,username,Double.valueOf(latitud),Double.valueOf(longitud));
                    //databaseReference.child("publicaciones").push().setValue(publicacion);
                    //databaseReference.child("publicaciones").child(username).setValue(publicacion);
                    databaseReference = database.getReference("publicaciones");
                    databaseReference.child(tipo).setValue(publicacion);
                    Toast.makeText(getActivity(),"Se creo correctamente",Toast.LENGTH_SHORT).show();





                }
            }
        });
    }


}