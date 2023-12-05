package com.example.notecalles.activity;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.notecalles.LoginActivity;
import com.example.notecalles.R;
import com.example.notecalles.activity.ui.mispublicaciones.MisPublicacionesFragment;
import com.example.notecalles.activity.ui.publicar.PublicarFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notecalles.databinding.ActivityInicioBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class InicioActivity extends AppCompatActivity {

    FirebaseUser current;
    FirebaseAuth mAuth;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarInicio.toolbar);



        //ini
        /*mAuth = FirebaseAuth.getInstance();
        current = mAuth.getCurrentUser();*/


        binding.appBarInicio.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_publicar, R.id.nav_mispublicaciones, R.id.nav_otrasPublicaciones,R.id.nav_glosario,R.id.g_CallejeroActivity)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        checkuserstatus();

        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            String us = extras.getString("user");
            Bundle bundle = new Bundle();
            bundle.putString("user",us);

            MisPublicacionesFragment f = new MisPublicacionesFragment();
            f.setArguments(bundle);      // aquí envías los datos al fragment
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.crearpubs, f).addToBackStack(null).commit();
        }

    }

    public void checkuserstatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("logindata",MODE_PRIVATE);
        Boolean counter=sharedPreferences.getBoolean("logincounter",Boolean.valueOf(String.valueOf(MODE_PRIVATE)));
        String user = sharedPreferences.getString("usern",String.valueOf(MODE_PRIVATE));
        if (counter){
            NavigationView navigationView = binding.navView;
            View headerView = navigationView.getHeaderView(0);
            TextView navName = headerView.findViewById(R.id.nav_name);
            navName.setText(user);


        }
        else{
            startActivity(new Intent(InicioActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.it_cerrarsesion){
            this.logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("username");
        editor.apply();
        this.finish();

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Deseas salir de la app")
                .setContentText("¿Estás seguro?")
                .setCancelText("No, Cancelar!").setConfirmText("Sí, Cerrar")
                /*.showCancelButton(true).setCancelClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Operación cancelada")
                            .setContentText("No saliste de la app")
                            .show();
                })*/.setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    System.exit(0);
                }).show();
    }

    void saveData(String username){
        SharedPreferences sharedPreferences = getSharedPreferences("logindata",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logincounter",true);
        editor.putString("usern",username);
        editor.apply();
        startActivity(new Intent(getApplicationContext(), PublicarFragment.class));
        finish();

    }

    /*public void updateNavHeader(){
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        TextView navName = headerView.findViewById(R.id.nav_name);
        TextView navUser = headerView.findViewById(R.id.nav_username);

        navName.setText(current.getDisplayName());
        navUser.setText(current.getEmail());
    }*/

    /*public void updateNavHeader(){

        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        TextView navName = headerView.findViewById(R.id.nav_name);
        TextView navUser = headerView.findViewById(R.id.nav_username);

        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("nombre");
        String usuarioUser = intent.getStringExtra("username");

        navName.setText(nameUser);
        navUser.setText(usuarioUser);


    }*/




}