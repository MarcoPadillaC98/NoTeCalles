package com.example.notecalles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notecalles.activity.InicioActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btn_Login,btn_CrearCuenta;
    EditText username,password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.txtUsernameLogin);
        password = findViewById(R.id.txtPasswordLogin);
        btn_Login = findViewById(R.id.btn_Ingresar);

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
                login();

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
}