package com.dam.sga;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class BookSearchActivity extends AppCompatActivity {

    private EditText title, author, isbn;
    private ImageButton qr;
    private Button search;

    private int itemcount;
    private String qrcode;

    ActivityResultLauncher<ScanOptions> qrLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(BookSearchActivity.this, "Cancelado", Toast.LENGTH_LONG).show();
                } else {
                    qrcode = result.getContents();
                    Toast.makeText(BookSearchActivity.this, "Scanned: " + qrcode, Toast.LENGTH_LONG).show();
                    isbn.setText(qrcode);
                }
            });

    //Creación de la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        title = (EditText) findViewById(R.id.abs_et_title);
        author = (EditText) findViewById(R.id.abs_et_author);
        isbn = (EditText) findViewById(R.id.abs_et_isbn);

        qr = (ImageButton) findViewById(R.id.abs_ib_qr);
        search = (Button) findViewById(R.id.abs_b_search);

        //Asignamos el listener al botón QR
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrLauncher.launch(new ScanOptions());
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookSearchActivity.this, BookResultActivity.class);

                String tit = title.getText().toString();
                String aut = author.getText().toString();
                String isb = isbn.getText().toString();

                if ( !tit.isEmpty() || !aut.isEmpty() || !isb.isEmpty() ) {
                    //Campos rellenos
                    intent.putExtra("Title", tit);
                    intent.putExtra("Author", aut);
                    intent.putExtra("ISBN", isb);

                    startActivity(intent);
                } else {
                    //Campos vacíos
                    title.setHintTextColor(Color.parseColor("#FF0000"));
                    title.setBackgroundColor(Color.parseColor("#45FFE7E7"));
                    author.setHintTextColor(Color.parseColor("#FF0000"));
                    author.setBackgroundColor(Color.parseColor("#45FFE7E7"));
                    isbn.setHintTextColor(Color.parseColor("#FF0000"));
                    isbn.setBackgroundColor(Color.parseColor("#45FFE7E7"));
                }
            }
        });

        BottomNavigationView bnv = findViewById(R.id.abs_navbar);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bnv_home:
                        Intent int_Home = new Intent(BookSearchActivity.this, MainActivity.class);
                        startActivity(int_Home);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bnv_loans:
                        Intent int_Loan = new Intent(BookSearchActivity.this, LoanResultActivity.class);
                        startActivity(int_Loan);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bnv_profile:
                        Intent int_Profile = new Intent(BookSearchActivity.this, ProfileActivity.class);
                        startActivity(int_Profile);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}
