package com.example.lenovo.Album1.Activity.recyclerview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.Album1.Activity.recyclerview.Adapter.CartAdapter;
import com.example.lenovo.Album1.Activity.recyclerview.ClickListener;
import com.example.lenovo.Album1.Activity.recyclerview.Database.Movie;
import com.example.lenovo.Album1.Activity.recyclerview.Database.SQLiteHandler;
import com.example.lenovo.Album1.Activity.recyclerview.DateClass;
import com.example.lenovo.Album1.Activity.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private CartAdapter cartadapter;
    private List<Movie> cartList1,cartList;
    private RecyclerView recyclerView;
    private SQLiteHandler db;
    private DateClass dateClass;
    TextView txt_count,txt_allprice,date;
    Button back,keepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        db=new SQLiteHandler(this);
        cartList=new ArrayList<>();
        cartList1=new ArrayList<>();
        date=(TextView) findViewById(R.id.txt_date);
        back=(Button) findViewById(R.id.btn_back);
        dateClass=new DateClass();
        date.setText(dateClass.setDate());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CartActivity.this,MainActivity.class);
                startActivity(intent);
                db.deleteAllCart();
            }
        });
        keepon=(Button) findViewById(R.id.btn_keepon);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList=db.getCartMovie();
        showFactor();
        cartadapter=new CartAdapter(getApplicationContext(), cartList, new ClickListener() {
            @Override
            public void clicklistener1(View view, int pos) {
                if(db.getCartMovie().isEmpty())
                {
                    Intent intent=new Intent(CartActivity.this,MainActivity.class);
                    startActivity(intent);}
                //date.setText(cartList.get(pos).getDate());
                else showFactor();
            }

            @Override
            public void clicklistener2(View view, int pos) {


            }


        });
        recyclerView.setAdapter(cartadapter);
    }

    public void showFactor() {
        int count = 0,price=0;
        cartList1= db.getCartMovie();
        for (int i=0;i<cartList1.size();i++)
        {
            count=(cartList1.get(i).getCount())+count;
            price= (Integer.parseInt(cartList1.get(i).getPrice()))+price;
        }
        txt_count=(TextView) findViewById(R.id.txt_count);
        txt_allprice=(TextView) findViewById(R.id.txt_all_price);
        txt_allprice.setText(String.valueOf(price));
        txt_count.setText(String.valueOf(count));

    }
}