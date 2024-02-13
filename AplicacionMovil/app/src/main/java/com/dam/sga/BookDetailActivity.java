package com.dam.sga;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity {

    //Declaración de elementos gráficos
    private TextView title, author, publisher,description, avcopy, mclibrary;
    private ImageView bthumbnail, fthumbnail;
    private Button reqloan;
    private ImageButton back, gplshop;

    //Declaración de variables
    private int count = 2;

    private int img;

    private String copyMessage, ImageLink, BuyLink, Title, Author, ISBN, Publisher, Desc;

    //Creación de la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        //Imágenes mostradas mediante URL's
        bthumbnail = (ImageView) findViewById(R.id.abd_iv_bth);
        fthumbnail = (ImageView) findViewById(R.id.abd_iv_fth);

        title = (TextView) findViewById(R.id.abd_tv_title);
        author = (TextView) findViewById(R.id.abd_tv_author);
        publisher = (TextView) findViewById(R.id.abd_tv_publisher);
        description = (TextView) findViewById(R.id.abd_tv_content);
        avcopy = (TextView) findViewById(R.id.abd_tv_avcopy);
        mclibrary = (TextView) findViewById(R.id.abd_tv_mcl);
        reqloan = (Button) findViewById(R.id.abd_b_reqloan);
        gplshop = (ImageButton) findViewById(R.id.abd_ib_gp);
        back = (ImageButton) findViewById(R.id.abd_ib_back);

        //Recuperamos los datos recibidos
        Intent intent = getIntent();

        Title = intent.getExtras().getString("Título");
        Author = intent.getExtras().getString("Autor");
        ISBN = intent.getExtras().getString("ISBN");
        Publisher = intent.getExtras().getString("Editorial");
        Desc = intent.getExtras().getString("Description");
        ImageLink = intent.getExtras().getString("ImgLink");
        BuyLink = intent.getExtras().getString("BuyLink");

        Glide.with(this).load(ImageLink).into(bthumbnail);
        Glide.with(this).load(ImageLink).into(fthumbnail);


        //Configuramos los valores
        title.setText(Title);
        author.setText(Author);
        publisher.setText(Publisher);
        description.setText(Desc);

        List<Book> lstBook = Functions_Server.getBooks(ISBN, Author, Title);  // Buscamos el libro pasando el isbn

        String[] library = new String[lstBook.size()];

        for (int i=0; i<lstBook.size(); i++){
            library[i] = lstBook.get(i).getLocation();
        }

        copyMessage = "Seleccione facultad";
        avcopy.setText(copyMessage);

        mclibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);

                //Título
                builder.setTitle("Selecciona una facultad");
                builder.setCancelable(false);
                int checkedItem = 1;

                builder.setSingleChoiceItems(library, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogSCh, int which) {
                        mclibrary.setText(library[which]);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogPos, int i) {
                        dialogPos.dismiss();
                        imprime_copias(lstBook, mclibrary.getText().toString());
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogNeg, int i) {
                        dialogNeg.dismiss();
                    }
                });
                AlertDialog alertdiag = builder.create();
                alertdiag.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            //Controlador onClick: Atrás
            //view: Vista en la que se hizo click
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetailActivity.this, BookResultActivity.class);
                startActivity(intent);
            }
        });

        reqloan.setOnClickListener(new View.OnClickListener() {
            //Controlador onClick: Solicitar préstamo
            //view: Vista en la que se hizo click
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetailActivity.this, LoanActivity.class);
                intent.putExtra("Titulo", Title);
                intent.putExtra("ISBN", ISBN);
                intent.putExtra("Library", mclibrary.getText().toString());
                startActivity(intent);
            }
        });

        gplshop.setOnClickListener(new View.OnClickListener() {
            //Controlador onClick: Tienda Amazon
            //view: Vista en la que se hizo click
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(BuyLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    public void imprime_copias(List<Book> lista, String biblioteca){
        Book book = null;

        for (int i=0; i<lista.size(); i++){
            if (lista.get(i).getLocation().equals(biblioteca)){
                book = lista.get(i);
            }
        }

        if (book.getCopies() != 0) {
            if (book.getCopies() == 1){
                copyMessage = "Hay " + book.getCopies() + " ejemplar disponible";
                avcopy.setText(copyMessage);
                mclibrary.setVisibility(View.VISIBLE);
                reqloan.setVisibility(View.VISIBLE);
            } else {
                copyMessage = "Hay " + book.getCopies() + " ejemplares disponibles";
                avcopy.setText(copyMessage);
                mclibrary.setVisibility(View.VISIBLE);
                reqloan.setVisibility(View.VISIBLE);
            }
        } else {
            copyMessage = "No hay ejemplares disponibles";
            avcopy.setText(copyMessage);
            avcopy.setTextColor(Color.parseColor("#F50057"));
            //txlibrary.setVisibility(View.INVISIBLE);
            //bt_loan.setVisibility(View.INVISIBLE);
        }
    }
}
