package com.dam.sga;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.sga.Functions_App;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {

    private TextView name, surname1, surname2, dni, phone, email, rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView) findViewById(R.id.ap_tv_cname);
        surname1 = (TextView) findViewById(R.id.ap_tv_csr1);
        surname2 = (TextView) findViewById(R.id.ap_tv_csr2);
        dni = (TextView) findViewById(R.id.ap_tv_cdni);
        phone = (TextView) findViewById(R.id.ap_tv_ctlf);
        email = (TextView) findViewById(R.id.ap_tv_cemail);
        rol = (TextView) findViewById(R.id.ap_tv_role);

        UserLib user = Functions_App.getUser();
        name.setText(user.getName());
        surname1.setText(user.getSurname1());
        surname2.setText(user.getSurname2());
        dni.setText(user.getDni());
        phone.setText(user.getPhone());
        email.setText(user.getMail());



        BottomNavigationView bnv = findViewById(R.id.ap_navbar);

        bnv.setSelectedItemId(R.id.bnv_profile);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bnv_home:
                        Intent int_Home = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(int_Home);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bnv_loans:
                        Intent int_Loan = new Intent(ProfileActivity.this, LoanResultActivity.class);
                        startActivity(int_Loan);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bnv_profile:
                        return true;
                }
                return false;
            }
        });

    }
}
