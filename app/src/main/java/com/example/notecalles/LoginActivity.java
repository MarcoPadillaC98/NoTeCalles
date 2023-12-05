package com.example.notecalles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notecalles.activity.InicioActivity;
import com.example.notecalles.activity.ui.publicar.PublicarFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    Button btn_Login,btn_CrearCuenta;
    EditText username,password;

    FirebaseAuth mAuth;
    private TextInputLayout txt_InputUsernameLogin,txt_InputPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.txtUsernameLogin);
        password = findViewById(R.id.txtPasswordLogin);
        btn_Login = findViewById(R.id.btn_Ingresar);

        txt_InputUsernameLogin = findViewById(R.id.InputUsernameLogin);
        txt_InputPasswordLogin = findViewById(R.id.InputPasswordLogin);

        btn_CrearCuenta = findViewById(R.id.btnCrearcuenta);

        //mAuth = FirebaseAuth.getInstance();

        btn_CrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegistroActivity.class);
                startActivity(i);
            }
        });





        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUsername() | !validatePassword()){


                }else {
                    checkUser();
                }

            }
        });


        /*btn_Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try{
                    if(validar()){
                        login();
                        clear();
                    }else{
                        Toast.makeText(LoginActivity.this,"Complete los campos",Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    Toast.makeText(LoginActivity.this,"Se ha producido un error",Toast.LENGTH_SHORT).show();

                }

            }

        });*/

        //Sirve para quitar sombra a los textos ya completados
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txt_InputUsernameLogin.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txt_InputPasswordLogin.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }



    /*private void login() {
        String User = username.getText().toString();
        String Pass = password.getText().toString();

        mAuth.signInWithEmailAndPassword(User,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(LoginActivity.this, InicioActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivity.this,"El correo o contraseña son incorrectas",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/



    /*private boolean validar(){

        boolean retorno = true;
        String  usuario , pass;
        usuario = username.getText().toString();
        pass = password.getText().toString();

        if(usuario.isEmpty()){
            txt_InputUsernameLogin.setError("Ingrese su usuario y/o correo electronico");
            retorno = false;
        }else{
            txt_InputUsernameLogin.setErrorEnabled(false);
        }
        if(pass.isEmpty()){
            txt_InputPasswordLogin.setError("Ingrese su contraseña");
            retorno = false;
        }else{
            txt_InputPasswordLogin.setErrorEnabled(false);
        }
        return retorno;
    }*/

    public Boolean validateUsername(){
        String val = username.getText().toString();
        if(val.isEmpty()){
            username.setError("Usuario no encontrado");
            return false;
        }else {
            username.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = password.getText().toString();
        if(val.isEmpty()){
            password.setError("Password incorrecta");
            return false;
        }else {
            password.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = username.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarios");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    username.setError(null);
                    String passwordFromDb = snapshot.child(userUsername).child("password").getValue(String.class);

                    if(passwordFromDb.equals(userPassword)){
                        username.setError(null);
                        Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                        saveData(userUsername);
                        startActivity(intent);
                    }else {
                        username.setError("Credenciales invalidas");
                        password.requestFocus();
                    }
                }else {
                    username.setError("Usuario no existe");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    private void clear(){
        username.setText("");
        password.setText("");

        username.requestFocus();

    }

    void saveData(String username){
        SharedPreferences sharedPreferences = getSharedPreferences("logindata",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logincounter",true);
        editor.putString("usern",username);
        editor.apply();
        startActivity(new Intent(LoginActivity.this, InicioActivity.class));
        finish();

    }


}
