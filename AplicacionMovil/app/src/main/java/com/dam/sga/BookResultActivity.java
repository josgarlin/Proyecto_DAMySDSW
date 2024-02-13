package com.dam.sga;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class BookResultActivity extends AppCompatActivity {

    private List<Book> lstBook;
    private TextView tx_mContainer;
    private String message;
    private int count;
    private ImageButton back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_result);

        back = (ImageButton) findViewById(R.id.abr_ib_back);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("Title");
        String author = intent.getExtras().getString("Author");
        String isbn = intent.getExtras().getString("ISBN");

        lstBook = Functions_Server.getBooks(isbn, author,title);

        //Creamos el array de libros
        //lstBook = new ArrayList<>();

        List<Book> lista_simple = lista_simple(lstBook);

        //Instancia de un objeto del tipo RecyclerView
        RecyclerView myrv = (RecyclerView) findViewById(R.id.abr_rcv);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lista_simple);
        myrv.setLayoutManager(new GridLayoutManager(this,1));
        myrv.setAdapter(myAdapter);

        //Modificamos el mensaje superior
        count = lista_simple.size();
        tx_mContainer = (TextView) findViewById(R.id.abr_tv_result);

        if (count > 1) {
            message = count + " resultados:";
        } else if (count == 1){
            message = count + " resultado:";
        } else {
            message = "No hay resultados";
        }

        tx_mContainer.setText(message);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookResultActivity.this, BookSearchActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bnv = findViewById(R.id.abr_navbar);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bnv_home:
                        Intent int_Home = new Intent(BookResultActivity.this, MainActivity.class);
                        startActivity(int_Home);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bnv_loans:
                        Intent int_Loan = new Intent(BookResultActivity.this, LoanResultActivity.class);
                        startActivity(int_Loan);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bnv_profile:
                        Intent int_Profile = new Intent(BookResultActivity.this, ProfileActivity.class);
                        startActivity(int_Profile);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    public List<Book> lista_simple(List<Book> lista_repetidos){
        List<Book> lista_simple = new ArrayList<Book>();
        if(lista_repetidos.size() > 0) {
            lista_simple.add(lista_repetidos.get(0));
            for (Book book_rep : lista_repetidos){
                for(Book book_simple: lista_simple){
                    if(book_simple.getIsbn().compareTo(book_rep.getIsbn()) !=0 && book_simple.getLocation().compareTo(book_rep.getLocation())!=0){
                        lista_simple.add(book_rep);
                    }
                }
            }
        }
        return lista_simple;
    }
}
