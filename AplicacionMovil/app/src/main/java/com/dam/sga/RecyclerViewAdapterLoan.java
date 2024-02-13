package com.dam.sga;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapterLoan extends RecyclerView.Adapter<RecyclerViewAdapterLoan.MyViewHolder> {

    private Context mContext;
    private List<Loan> loan_data;

    private static Book data_book;

    //Constructor de RecyclerViewAdapter
    public RecyclerViewAdapterLoan(Context mContext, List<Loan> loan_data) {
        this.mContext = mContext;
        this.loan_data = loan_data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, isbn, fecFin;
        ImageView thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView){
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.cdv_tv_title);
            isbn = (TextView) itemView.findViewById(R.id.cdv_tv_isbn);
            fecFin = (TextView) itemView.findViewById(R.id.cdv_tv_fecFin);
            thumbnail = (ImageView) itemView.findViewById(R.id.cdv_iv_th);
            cardView = (CardView) itemView.findViewById(R.id.cdv_item);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_loan, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        List<Book> lstBook = Functions_Server.getBooks(loan_data.get(position).getIsbn(),"","");
        for(int i=0; i<lstBook.size(); i++){
            if (loan_data.get(position).getLocation().equals(lstBook.get(i).getLocation())){
                data_book = lstBook.get(i);
            }
        }
        System.out.println("******"+data_book.getImageLinks());
        holder.title.setText(data_book.getTitle());
        holder.isbn.setText(loan_data.get(position).getIsbn());
        holder.fecFin.setText(loan_data.get(position).getEnd_date());
        Glide.with(mContext).load(data_book.getImageLinks()).into(holder.thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LoanDetailActivity.class);

                //Pasamos los datos a la actividad Book
                intent.putExtra("ISBN", loan_data.get(position).getIsbn());
                intent.putExtra("Library", loan_data.get(position).getLocation());
                intent.putExtra("FecFin", loan_data.get(position).getEnd_date());
                intent.putExtra("DNI", loan_data.get(position).getDni());
                intent.putExtra("ImgLink", data_book.getImageLinks());
                intent.putExtra("BuyLink", data_book.getBuyLink());

                //Comienza la actividad
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return loan_data.size();
    }
}
