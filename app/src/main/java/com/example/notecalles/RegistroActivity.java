package com.example.notecalles;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    Button btn_registrar,btn_IniciarSesion;
    EditText username,password,nombre,apellido,celular,documento;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    TextInputLayout txtUser; // Colocar todos los inputs


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        celular = findViewById(R.id.txtCelular);
        documento = findViewById(R.id.txtDocumento);
        btn_registrar= findViewById(R.id.btn_Registrar);

        btn_IniciarSesion = findViewById(R.id.btn_IniciarSesionR);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        btn_IniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistroActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


    }

    private void register() {

        String User = username.getText().toString();
        String Pass = password.getText().toString();
        String Nom = nombre.getText().toString();
        String Ape = apellido.getText().toString();
        String Cel = celular.getText().toString();
        String Doc = documento.getText().toString();

        if(!User.isEmpty() && !Pass.isEmpty() && !Nom.isEmpty() && !Ape.isEmpty() && !Cel.isEmpty() && !Doc.isEmpty() ){
            if (password.length()> 5){
                createUser(User, Pass, Nom, Ape, Cel, Doc);
            }else {
                Toast.makeText(RegistroActivity.this,"La contraseña debe tener al menos 6 caracteres",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(RegistroActivity.this,"Para continuar inserte todos los datos",Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser(String User, String Pass, String Nom, String Ape, String Cel,String Doc) {
        mAuth.createUserWithEmailAndPassword(User,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("username",User);
                    map.put("password",Pass);
                    map.put("nombre",Nom);
                    map.put("apellido",Ape);
                    map.put("celular",Cel);
                    map.put("documento",Doc);
                    mFirestore.collection("usuarios").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(RegistroActivity.this,LoginActivity.class);
                                startActivity(i);
                                Toast.makeText(RegistroActivity.this,"El usuario se creo correctamente",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else {
                    Toast.makeText(RegistroActivity.this,"No se registro el usuario, intente de nuevo",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}