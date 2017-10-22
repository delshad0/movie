package com.example.lenovo.Album1.Activity.recyclerview.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.Album1.Activity.recyclerview.Database.Album;
import com.example.lenovo.Album1.Activity.recyclerview.ClickListener;
import com.example.lenovo.Album1.Activity.recyclerview.R;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;
    ClickListener clickListener;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public ImageView thumbnail;
        CardView cardView;
        LinearLayout layer;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            cardView=(CardView) view.findViewById(R.id.card_view);
            layer=(LinearLayout) view.findViewById(R.id.ll_movie);

        }

        @Override
        public void onClick(View view) {
            clickListener.clicklistener1(view,getAdapterPosition());
        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList,ClickListener clickListener) {
        this.mContext = mContext;
        this.albumList = albumList;
        this.clickListener=clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getName());
          holder.layer.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  clickListener.clicklistener1(view,position);
              }
          });
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}