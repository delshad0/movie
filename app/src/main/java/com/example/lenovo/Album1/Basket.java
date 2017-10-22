package com.example.lenovo.Album1.Activity.recyclerview;

import android.content.Context;
import android.widget.TextView;

import com.example.lenovo.Album1.Activity.recyclerview.Database.Movie;
import com.example.lenovo.Album1.Activity.recyclerview.Database.SQLiteHandler;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    Context mContext;
    private List<Movie> cartList=new ArrayList<>();
  TextView txt_count;
    public void showFactor() {
        int count = 0,price=0;
        SQLiteHandler db = new SQLiteHandler(mContext);

        cartList= db.getCartMovie();
        for (int i=0;i<cartList.size();i++)
        {
            count=(cartList.get(i).getCount())+count;
            price= (Integer.parseInt(cartList.get(i).getPrice()))+price;
        }
        txt_count=(TextView) txt_count.findViewById(R.id.txt_count);
       ////// txt_allprice=(TextView) findViewById(R.id.txt_all_price);
        //txt_allprice.setText(String.valueOf(price));
        txt_count.setText(String.valueOf(count));

    }
}
