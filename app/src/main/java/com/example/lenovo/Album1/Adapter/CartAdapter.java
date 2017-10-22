package com.example.lenovo.Album1.Activity.recyclerview.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.Album1.Activity.recyclerview.ClickListener;
import com.example.lenovo.Album1.Activity.recyclerview.Database.Movie;
import com.example.lenovo.Album1.Activity.recyclerview.Database.SQLiteHandler;
import com.example.lenovo.Album1.Activity.recyclerview.R;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<Movie> cartList;
    ClickListener clicklistener;
    Context mContext;
    private SQLiteHandler db;

    public CartAdapter(Context application, List<Movie> cartList, ClickListener clickListener) {
        this.cartList=cartList;
        this.mContext=application;
        this.clicklistener=clickListener;
        this.db = new SQLiteHandler(mContext.getApplicationContext());
    }


    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, price,badge,date;
        ImageView image,delete;

        public MyViewHolder(View view) {
            super(view);
             //new SQLiteHandler(mContext);
            name = (TextView) view.findViewById(R.id.txt_name);
            price = (TextView) view.findViewById(R.id.txt_price);
            image = (ImageView) view.findViewById(R.id.image);
            delete=(ImageView) view.findViewById(R.id.delete);
            badge=(TextView)view.findViewById(R.id.badge_notification_3);

        }

        @Override
        public void onClick(View view) {
            clicklistener.clicklistener1(view,getAdapterPosition());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cart, parent, false);
        return new CartAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Movie movie = cartList.get(position);
        holder.name.setText(movie.getName());
        holder.price.setText(movie.getPrice());
        Glide.with(mContext).load(movie.getTumbnail()).into(holder.image);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Log.i("lili",cartList.get(position).getMovie_ID());
                String id= cartList.get(position).getMovie_ID();
                if(db.deleteCart(id)>0) {

                    cartList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,cartList.size());

               // if(mContext instanceof CartActivity) {
                    //Log.i("leyla","leyla");
                    //((CartActivity) mContext).showFactor();
               }
                clicklistener.clicklistener1(view,position);

                //Toast.makeText(,"Removed done",Toast.LENGTH_SHORT).show();
              //  }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
