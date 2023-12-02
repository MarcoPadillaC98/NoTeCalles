package com.example.notecalles.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notecalles.R;
import com.example.notecalles.activity.ui.publicar.PublicarFragment;
import com.example.notecalles.model.Ubicacion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UbicacionActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener{

    EditText latitud, longitud;
    GoogleMap mMap;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        latitud= findViewById(R.id.txtLatitud);
        longitud= findViewById(R.id.txtLongitud);

        iniciarFirebase();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button bGuardar = (Button) findViewById(R.id.btn_GuardarMaps);
        bGuardar.setOnClickListener(this);

    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onClick(View view) {
        PublicarFragment publicarFragment = new PublicarFragment();
        Bundle bundle = new Bundle();
        bundle.putString("lat",latitud.getText().toString());
        bundle.putString("lon",longitud.getText().toString());

        publicarFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeholder,publicarFragment,null);
        fragmentTransaction.commit();

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        latitud.setText(""+latLng.latitude);
        longitud.setText(""+latLng.longitude);
        mMap.clear();
        LatLng lima = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(lima).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lima));

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        latitud.setText(""+latLng.latitude);
        longitud.setText(""+latLng.longitude);
        LatLng lima = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(lima).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lima));

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);
        LatLng lima = new LatLng(-12.0675693,-77.036889);
        mMap.addMarker(new MarkerOptions().position(lima).title("Lima"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lima));

    }
}