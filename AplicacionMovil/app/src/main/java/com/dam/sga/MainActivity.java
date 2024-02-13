package com.dam.sga;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import org.springframework.web.client.RestTemplate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.sga.Functions_App;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.*;

public class MainActivity extends AppCompatActivity {

    private String mensaje;
    private TextView welcome;
    private Button search, location;
    private ImageButton logout;

    RestTemplate restTemplate = new RestTemplate();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        welcome = (TextView) findViewById(R.id.am_tv_wel);
        search = (Button) findViewById(R.id.am_b_search);
        location = (Button) findViewById(R.id.am_b_location);
        logout = (ImageButton) findViewById(R.id.am_ib_logout);

        //user = "Juan";
        UserLib user = Functions_App.getUser();
        mensaje = detectaHora(user);
        welcome.setText(mensaje);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BookSearchActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        BottomNavigationView bnv = findViewById(R.id.am_navbar);

        bnv.setSelectedItemId(R.id.bnv_home);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bnv_home:
                        return true;
                    case R.id.bnv_loans:
                        Intent int_Loan = new Intent(MainActivity.this, LoanResultActivity.class);
                        startActivity(int_Loan);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bnv_profile:
                        Intent int_Profile = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(int_Profile);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String detectaHora(UserLib user) {

        LocalTime horaAct = LocalTime.now();
        LocalTime refDía = LocalTime.parse("06:00:00");
        LocalTime refTarde = LocalTime.parse("12:00:00");
        LocalTime refNoche = LocalTime.parse("20:00:00");

        if (horaAct.isAfter(refDía) && horaAct.isBefore(refTarde)) {
            mensaje = "Buenos días, " + user.getName();
        } else if(horaAct.isAfter(refTarde) && horaAct.isBefore(refNoche)) {
            mensaje = "Buenas tardes, " + user.getName();
        } else {
            mensaje = "Buenas noches, " + user.getName();
        }
        return mensaje;
    }
}
