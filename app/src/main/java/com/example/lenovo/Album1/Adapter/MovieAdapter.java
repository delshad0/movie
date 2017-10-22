package com.example.lenovo.Album1.Activity.recyclerview.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.Album1.Activity.recyclerview.ClickListener;
import com.example.lenovo.Album1.Activity.recyclerview.Database.Movie;
import com.example.lenovo.Album1.Activity.recyclerview.R;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> implements Filterable {
    ClickListener clickListener;
    private List<Movie> movieList;
    private List<Movie> filterList;
    Context mContext;
    int pos;

    public MovieAdapter(Context context,List<Movie> list,int pos,ClickListener clickListener){
        this.clickListener=clickListener;
        this.mContext=context;
        this.movieList=list;
        this.pos=pos;
        this.filterList=list;

    }



    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,price,des;
        ImageView image,basket;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txt_name);
            price = (TextView) view.findViewById(R.id.txt_price);
            image = (ImageView) view.findViewById(R.id.image2);
            basket = (ImageView) view.findViewById(R.id.img_basket);
            des=(TextView) view.findViewById(R.id.txt_des3);

        }

        @Override
        public void onClick(View view) {
            clickListener.clicklistener1(view,getAdapterPosition());
            clickListener.clicklistener2(view,getAdapterPosition());

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_movie_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Movie movie = filterList.get(position);
        holder.name.setText(movie.getName());
        holder.price.setText(movie.getPrice());
        Glide.with(mContext).load(movie.getTumbnail()).into(holder.image);

        holder.basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 clickListener.clicklistener1(view,position);
            }
        });

        holder.des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     clickListener.clicklistener2(view,position);
              //FragmentManager manager = ((MainActivity) mContext).getFragmentManager();
                 //ConnectToBank dialogFragment = new ConnectToBank().newInstance(movieList,position);
                 //dialogFragment.show(manager, "Sample Fragment");
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }



    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
               // Log.i("search",charString);
                if (charString.isEmpty()) {
                    filterList = movieList;
                } else {
                    ArrayList<Movie> filteredList = new ArrayList<>();

                    for (Movie movie : movieList) {

                        if (movie.getPrice().toLowerCase().contains(charString) || movie.getName().toLowerCase().contains(charString)) {
                           Log.i("get",movie.getName().toString()+""+movie.getPrice().toString());
                            filteredList.add(movie);
                        }
                    }
                    filterList = filteredList;
                   // Log.i("list", String.valueOf(filterList));
                    //Log.i("list", String.valueOf(filteredList));
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                //Log.i("list", String.valueOf(filterResults));
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<Movie>) filterResults.values;
                Log.i("list", String.valueOf(filterList));
                notifyDataSetChanged();
            }
        };
    }
}
