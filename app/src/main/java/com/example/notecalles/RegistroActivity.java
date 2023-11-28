package com.example.notecalles;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    Button btn_registrar,btn_IniciarSesion;
    EditText username,password,nombre,apellido,celular,documento;
    private TextInputLayout txtIUsernameR, txtIPasswordR,txtINameR, txtIApellidoR, txtINumeroR, txtIDocumentoR;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Registro
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        celular = findViewById(R.id.txtCelular);
        documento = findViewById(R.id.txtDocumento);

        //Botones
        btn_registrar= findViewById(R.id.btn_Registrar);
        btn_IniciarSesion = findViewById(R.id.btn_IniciarSesionR);

        //Instanciar para validaciones
        txtIUsernameR = findViewById(R.id.InputUsernameR);
        txtIPasswordR = findViewById(R.id.InputPasswordR);
        txtINameR = findViewById(R.id.InputNameR);
        txtIApellidoR = findViewById(R.id.InputApellidoR);
        txtINumeroR = findViewById(R.id.InputNumeroR);
        txtIDocumentoR = findViewById(R.id.InputDocumentoR);

        //Firebase
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
                try{
                    if(validarRegistro()){
                        register();
                    }else{
                        Toast.makeText(RegistroActivity.this,"Complete los campos",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(RegistroActivity.this,"Se ha producido un error",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Quitar sombra a los InputsLayout que ya estan completos
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtIUsernameR.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtIPasswordR.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtINameR.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        apellido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtIApellidoR.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        celular.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtINumeroR.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        documento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtIDocumentoR.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

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
            if(isEmailValid(User))
                if(password.length()> 5){
                createUser(User, Pass, Nom, Ape, Cel, Doc);
                }else {
                Toast.makeText(RegistroActivity.this,"La contraseña debe tener al menos 6 caracteres",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(RegistroActivity.this,"Para continuar inserte todos los datos",Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isEmailValid(String email){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void createUser(String User, String Pass, String Nom, String Ape, String Cel,String Doc) {
        mAuth.createUserWithEmailAndPassword(User,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("username",User);
                    //Se comenta para no guardar en la firestore la contraseña , ya que esta encriptada en la autenticacion
                    //map.put("password",Pass);
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
                                clear();
                            }

                        }
                    });
                }else {
                    Toast.makeText(RegistroActivity.this,"No se registro el usuario, intente de nuevo",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    //Validar InputsLayouts
    private boolean validarRegistro(){

        boolean retorno = true;
        String usuarioR,passR,nombreR, apellidoR, celularR, documentoR;

        usuarioR = username.getText().toString();
        passR = password.getText().toString();
        nombreR = nombre.getText().toString();
        apellidoR = apellido.getText().toString();
        celularR = celular.getText().toString();
        documentoR = documento.getText().toString();

        if(usuarioR.isEmpty()){
            txtIUsernameR.setError("Ingrese su correo electronico");
        }else{
            txtIUsernameR.setErrorEnabled(false);
        }if(passR.isEmpty()){
            txtIPasswordR.setError("Ingrese su contraseña");
        }else{
            txtIPasswordR.setErrorEnabled(false);
        }if(nombreR.isEmpty()){
            txtINameR.setError("Ingrese su nombre");
        }else{
            txtINameR.setErrorEnabled(false);
        }if(apellidoR.isEmpty()){
            txtIApellidoR.setError("Ingrese su apellido");
        }else{
            txtIApellidoR.setErrorEnabled(false);
        }if(celularR.isEmpty()){
            txtINumeroR.setError("Ingrese su celular");
        }else{
            txtINumeroR.setErrorEnabled(false);
        }if(documentoR.isEmpty()){
            txtIDocumentoR.setError("Ingrese su documento");
        }else{
            txtIDocumentoR.setErrorEnabled(false);
        }
        return retorno;
    }

    private void clear(){
        username.setText("");
        password.setText("");
        nombre.setText("");
        apellido.setText("");
        celular.setText("");
        documento.setText("");


    }






}