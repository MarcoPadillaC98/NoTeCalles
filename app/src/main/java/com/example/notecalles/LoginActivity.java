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

import com.example.notecalles.activity.InicioActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

        mAuth = FirebaseAuth.getInstance();

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
                try{
                    if(validar()){
                        login();
                    }else{
                        Toast.makeText(LoginActivity.this,"Complete los campos",Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    Toast.makeText(LoginActivity.this,"Se ha producido un error",Toast.LENGTH_SHORT).show();

                }

            }

        });

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

    private void login() {
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
    }



    private boolean validar(){

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
    }

    @Override
    public void onBackPressed() {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("has oprimido el botón atrás")
                .setContentText("¿Quieres cerrar la aplicación?")
                .setCancelText("No, Cancelar!").setConfirmText("Sí, Cerrar")
                .showCancelButton(true).setCancelClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Operación cancelada")
                            .setContentText("No saliste de la app")
                            .show();
                }).setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    System.exit(0);
                }).show();
    }

    }
