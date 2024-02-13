package com.dam.sga;

import android.app.Activity;
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

import java.util.List;

public class LoanResultActivity extends AppCompatActivity {

    private List<Loan> lstLoan;
    private TextView tx_mContainer;
    private String message;
    private int count;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_result);

        back = (ImageButton) findViewById(R.id.alr_ib_back);

        UserLib user = Functions_App.getUser();

        lstLoan = Functions_Server.getLoan(user.getDni());


        //Instancia de un objeto del tipo RecyclerView
        RecyclerView myrv = (RecyclerView) findViewById(R.id.alr_rcv);
        RecyclerViewAdapterLoan myAdapter = new RecyclerViewAdapterLoan(this,lstLoan);
        myrv.setLayoutManager(new GridLayoutManager(this,1));
        myrv.setAdapter(myAdapter);

        //Modificamos el mensaje superior
        count = lstLoan.size();
        tx_mContainer = (TextView) findViewById(R.id.alr_tv_result);

        if (count > 1) {
            message = count + " préstamos:";
        } else if (count == 1){
            message = count + " prestamo:";
        } else {
            message = "No hay resultados";
        }

        tx_mContainer.setText(message);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoanResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bnv = findViewById(R.id.alr_navbar);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bnv_home:
                        Intent int_Home = new Intent(LoanResultActivity.this, MainActivity.class);
                        startActivity(int_Home);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bnv_loans:
                        Intent int_Loan = new Intent(LoanResultActivity.this, LoanResultActivity.class);
                        startActivity(int_Loan);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bnv_profile:
                        Intent int_Profile = new Intent(LoanResultActivity.this, ProfileActivity.class);
                        startActivity(int_Profile);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

}
