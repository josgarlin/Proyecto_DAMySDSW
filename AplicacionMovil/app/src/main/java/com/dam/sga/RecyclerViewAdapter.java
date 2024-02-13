package com.dam.sga;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> book_data;

    //Constructor de RecyclerViewAdapter
    public RecyclerViewAdapter(Context mContext, List<Book> book_data) {
        this.mContext = mContext;
        this.book_data = book_data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, author, publisher;
        ImageView thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.cdv_tv_title);
            author = (TextView) itemView.findViewById(R.id.cdv_tv_author);
            publisher = (TextView) itemView.findViewById(R.id.cdv_tv_publisher);
            thumbnail = (ImageView) itemView.findViewById(R.id.cdv_iv_th);
            cardView = (CardView) itemView.findViewById(R.id.cdv_item);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_book, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(book_data.get(position).getTitle());
        holder.author.setText(book_data.get(position).getAuthor());
        holder.publisher.setText(book_data.get(position).getPublisher());
        Glide.with(mContext).load(book_data.get(position).getImageLinks()).into(holder.thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookDetailActivity.class);

                //Pasamos los datos a la actividad Book
                intent.putExtra("Título", book_data.get(position).getTitle());
                intent.putExtra("Autor", book_data.get(position).getAuthor());
                intent.putExtra("Editorial", book_data.get(position).getPublisher());
                intent.putExtra("ISBN", book_data.get(position).getIsbn());
                intent.putExtra("Portada", book_data.get(position).getImageLinks());
                intent.putExtra("ImgLink", book_data.get(position).getImageLinks());
                intent.putExtra("BuyLink", book_data.get(position).getBuyLink());

                //Comienza la actividad
                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return book_data.size();
    }
}
