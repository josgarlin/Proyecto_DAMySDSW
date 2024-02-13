package com.dam.sga;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class LocationActivity extends Activity {

    private EditText isbn;
    private TextView mclibrary, shelve;
    private CardView position;

    //Creada dinámicamente
    List<Book> lstBook;
    String[] library;
    String location;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        isbn = (EditText) findViewById(R.id.alc_et_isbn);
        mclibrary = (TextView) findViewById(R.id.alc_tv_library);
        position = (CardView) findViewById(R.id.alc_cv);
        shelve = (TextView) findViewById(R.id.alc_tv_position);

        mclibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isbn.getText().toString();
                lstBook = Functions_Server.getBooks(isbn.getText().toString(), "", "");
                library = new String[lstBook.size()];

                for (int j=0; j<lstBook.size(); j++){
                    library[j] = lstBook.get(j).getLocation();
                }

                System.out.println(library);

                Dialog alertdiag = onCreateDialog(savedInstanceState);
                alertdiag.show();
            }
        });

        BottomNavigationView bnv = findViewById(R.id.alc_navbar);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bnv_home:
                        Intent int_Home = new Intent(LocationActivity.this, MainActivity.class);
                        startActivity(int_Home);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bnv_loans:
                        Intent int_Loan = new Intent(LocationActivity.this, LoanResultActivity.class);
                        startActivity(int_Loan);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bnv_profile:
                        Intent int_Profile = new Intent(LocationActivity.this, ProfileActivity.class);
                        startActivity(int_Profile);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(LocationActivity.this);

        builder.setTitle("Selecciona una facultad")
                .setItems(library, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mclibrary.setText(library[i]);

                        for(Book book_aux: lstBook) {
                            if(book_aux.getLocation().compareTo(library[i]) == 0)
                            {
                                book=book_aux;
                            }
                        }
                        position.setVisibility(View.VISIBLE);
                        shelve.setText(book.getShelve());
                    }
                });

        return builder.create();
    }



}
