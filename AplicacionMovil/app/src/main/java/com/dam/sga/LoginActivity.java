package com.dam.sga;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.sga.Functions_App;

public class LoginActivity extends Activity {

    private EditText user, pass;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.al_et_user);
        pass = (EditText) findViewById(R.id.al_et_pwd);
        login = (Button) findViewById(R.id.al_b_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dni = user.getText().toString();
                String pwd = pass.getText().toString();

                if (Functions_Server.check_password(dni, pwd)) {
                    //Usuario existe y es correcto
                    System.out.println("\n\n*********** Obteniendo usuario por dni");
                    com.dam.sga.UserLib usuario = Functions_Server.getUserLib(dni);
                    Functions_App.setUser(usuario);
                    System.out.println("Bienvenido " + usuario.getName());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    user.setHintTextColor(Color.parseColor("#FF4141"));
                    user.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF4141")));
                    pass.setHintTextColor(Color.parseColor("#FF4141"));
                    pass.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF4141")));
                    Toast.makeText(LoginActivity.this, "No se ha encontrado el usuario", Toast.LENGTH_LONG).show();
                    System.out.println("\n\n*********** El usuario no existe");
                }
            }
        });
    }



}