package com.dam.sga;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

public class LoanDetailActivity extends AppCompatActivity {

    //Declaración de elementos gráficos
    private TextView title, author, fecFin, mclibrary;
    private EditText code;
    private ImageView bthumbnail, fthumbnail;

    private Button borrar;
    private ImageButton back, qr;

    private static Book book;

    private String qrcode;

    //Declaración de variables
    private int img;

    private String Title, Author, ISBN, library, FecFin, Dni, ImageLink, BuyLink;

    ActivityResultLauncher<ScanOptions> qrLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(LoanDetailActivity.this, "Cancelado", Toast.LENGTH_LONG).show();
                } else {
                    qrcode = result.getContents();  // Resultado del qr
                    Toast.makeText(LoanDetailActivity.this, "Scanned: " + qrcode, Toast.LENGTH_LONG).show();
                    code.setText(qrcode);
                }
            });

    //Creación de la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_detail);

        bthumbnail = (ImageView) findViewById(R.id.ald_iv_bth);
        fthumbnail = (ImageView) findViewById(R.id.ald_iv_fth);

        title = (TextView) findViewById(R.id.ald_tv_title);
        author = (TextView) findViewById(R.id.ald_tv_author);
        fecFin = (TextView) findViewById(R.id.ald_tv_fecFin);
        mclibrary = (TextView) findViewById(R.id.ald_tv_mcl);
        qr = (ImageButton) findViewById(R.id.ald_ib_qr);
        borrar = (Button) findViewById(R.id.ald_b_search);
        back = (ImageButton) findViewById(R.id.ald_ib_back);
        code = (EditText) findViewById(R.id.ald_et_code);

        //Mostramos los datos recibidos
        Intent intent = getIntent();
        ISBN = intent.getExtras().getString("ISBN");
        library = intent.getExtras().getString("Library");
        FecFin = intent.getExtras().getString("FecFin");
        Dni = intent.getExtras().getString("DNI");
        ImageLink = intent.getExtras().getString("ImgLink");
        BuyLink = intent.getExtras().getString("BuyLink");
        System.out.println("******"+ImageLink);
        Glide.with(this).load(ImageLink).into(bthumbnail);
        Glide.with(this).load(ImageLink).into(fthumbnail);

        List<Book> lstBook = Functions_Server.getBooks(ISBN, "", "");
        for(int i=0; i<lstBook.size(); i++){
            if (lstBook.get(i).getLocation().equals(library)) {
                book = lstBook.get(i);
            }
        }

        Title = book.getTitle().toString();
        Author = book.getAuthor().toString();

        //Configuramos los valores
        //bthumbnail.setImageResource(img);
        //fthumbnail.setImageResource(img);
        title.setText(Title);
        author.setText(Author);
        fecFin.setText(FecFin);
        mclibrary.setText(library);

        back.setOnClickListener(new View.OnClickListener() {
            //Controlador onClick: Atrás
            //view: Vista en la que se hizo click
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoanDetailActivity.this, LoanResultActivity.class);
                startActivity(intent);
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrLauncher.launch(new ScanOptions());
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo = code.getText().toString();
                if (!codigo.isEmpty()){

                    if (Functions_Server.checkLibraryPass(library, codigo) ) {
                        Functions_Server.deleteLoan(Dni, ISBN);
                        Intent intent = new Intent(LoanDetailActivity.this, MainActivity.class);
                        startActivity(intent);



                    }
                }
            }
        });
    }
}