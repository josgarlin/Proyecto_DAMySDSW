package com.dam.sga;

import static com.dam.sga.Functions_App.*;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.sga.Functions_App;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoanActivity extends  AppCompatActivity {

    private Button confirm;
    private TextView titulo, isbn, location, fec_fin;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        titulo = (TextView) findViewById(R.id.aln_tv_tc);
        isbn = (TextView) findViewById(R.id.aln_tv_ic);
        location = (TextView) findViewById(R.id.aln_tv_cl);
        fec_fin = (TextView) findViewById(R.id.aln_tv_cfv);
        confirm = (Button) findViewById(R.id.aln_b_confirm);

        UserLib user = Functions_App.getUser();

        Intent intent = getIntent();
        String title = intent.getExtras().getString("Titulo");
        String id = intent.getExtras().getString("ISBN");
        String library = intent.getExtras().getString("Library");

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        titulo.setText(title);
        isbn.setText(id);
        location.setText(library);
        fec_fin.setText(fecha.format(date.plusDays(20)));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ADAPTAR DNI
                boolean result = com.dam.sga.Functions_Server.postLoan("77975727T", isbn.getText().toString(), location.getText().toString());

                if (result) {
                    //Préstamo realizado correctamente
                    Toast.makeText(LoanActivity.this, "Préstamo realizado", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(LoanActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //Existen penalizaciones existentes
                    Toast.makeText(LoanActivity.this,  "No se ha podido realizar el prestamo", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(LoanActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
