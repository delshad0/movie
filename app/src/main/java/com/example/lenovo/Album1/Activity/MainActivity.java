package com.example.lenovo.Album1.Activity.recyclerview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lenovo.Album1.Activity.recyclerview.AppConfig;
import com.example.lenovo.Album1.Activity.recyclerview.AppController;
import com.example.lenovo.Album1.Activity.recyclerview.ClickListener;
import com.example.lenovo.Album1.Activity.recyclerview.Database.Movie;
import com.example.lenovo.Album1.Activity.recyclerview.Adapter.MovieAdapter;
import com.example.lenovo.Album1.Activity.recyclerview.Database.SQLiteHandler;
import com.example.lenovo.Album1.Activity.recyclerview.DateClass;
import com.example.lenovo.Album1.Activity.recyclerview.R;
import com.example.lenovo.Album1.Activity.recyclerview.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity  extends AppCompatActivity{

    private List<Movie> movieList;
    private List<Movie> countList,cartList;
    private MovieAdapter movieadapter;
    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private DateClass date;
    SQLiteHandler db;
    int POS;
    ImageView  menu,basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        db = new SQLiteHandler(this);
        movieList = new ArrayList<>();
        cartList = new ArrayList<>();
        setupRecycler();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            POS = extras.getInt("pos");
        }
        sendRequest(POS);
        //String search_text=search.getText().toString();
       //// EditText search=(EditText) findViewById(R.id.edt_search);
        //search(search);


        basket=(ImageView)findViewById(R.id.basket);
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if ((db.getCartMovie().isEmpty()))
                {

                    Toast.makeText(MainActivity.this,"سبدخرید خالی است.",Toast.LENGTH_LONG).show();
                }else {
                Intent intent=new Intent (MainActivity.this,CartActivity.class);
                startActivity(intent);
              }
            }
        });
        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu p=new PopupMenu(MainActivity.this,menu);
                getMenuInflater().inflate(R.menu.main_menu,p.getMenu());
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.logout:
                             logoutUser();

                             break;
                         case R.id.back:
                             Intent intent1=new Intent(MainActivity.this,ShowMovie.class);
                             startActivity(intent1);
                            break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                p.show();
            }
        });

    }

    private void setupRecycler() {
        sessionManager = new SessionManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem search = menu.findItem(R.id.search1);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }
*/

    private void search(EditText search) {
          //final String search_text=search.getText().toString();
        search.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                 movieadapter.getFilter().filter(s.toString());
             }

             @Override
             public void afterTextChanged(Editable editable) {

             }
         });



        /*

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                movieadapter.getFilter().filter(newText);
                return true;
            }
        });*/
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }*/

    /*private void setupSearch() {

        final EditText search=(EditText) findViewById(R.id.edt_search);
        search.addTextChangedListener(new SliderPagerAdapter() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int befor, int count) {
                textlenght = search.getText().length();
                //array_sort.clear();
                for (int i = 0; i < movieList.size(); i++) {
                    if (textlenght <= movieList.get(i).getName().length()) {
                        //Log.d("ertyyy", movieList.get(i).getName().toLowerCase().trim());
                        if (movieList.get(i).getName().toLowerCase().trim().contains(
                                search.getText().toString().toLowerCase().trim())) {
                            array_sort.add(movieList.get(i));
                        }
                    }
                }
                adapter = new RecyclerView.Adapter(MainActivity.this, array_sort);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }*/

    private void logoutUser() {

        sessionManager.setLogin(false);
        db.deleteAllCart();
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }
    private void sendRequest(final int position){
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(AppConfig.Movie_URL,
                null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                JSONObject kind = null;
                JSONArray a;
                for (int i = 0; i < response.length(); i++) {
                    try {

                        kind = (JSONObject) response.get(String.valueOf(position));
                        JSONArray tv = kind.getJSONArray("TV");
                        JSONArray  movie=kind.getJSONArray("MOVIE");
                        a=concatArray(tv,movie);
                        movieList.add(new Movie(String.valueOf(a.getJSONObject(i).get("id")),String.valueOf(a.getJSONObject(i).get("name"))
                                ,String.valueOf(a.getJSONObject(i).get("price"))
                                ,String.valueOf(a.getJSONObject(i).get("image"))));

                        movieadapter=new MovieAdapter(getApplicationContext(),movieList, position, new ClickListener() {
                            @Override
                            public void clicklistener1(View view, int pos) {
                             /*   FragmentManager manager = getFragmentManager();
                                //ConnectToBank dialogFragment = new ConnectToBank();
                                ConnectToBank dialogFragment = new ConnectToBank().newInstance(movieList,pos);
                                dialogFragment.show(manager, "Sample Fragment");
*/
                                // clickListener.clicklistener(view,position);
                                if (!db.getCartMovie().isEmpty() && (db.getSerial(movieList.get(pos).getName())==true))
                                {
                                    Toast.makeText(MainActivity.this, "این فیلم قبلا انتخاب شده است.", Toast.LENGTH_LONG).show();
                                }
                           //    if (db.getSerial(movieList.get(pos).getName())){

                           //
                                else {
                                    db.addCart(new Movie(movieList.get(pos).getMovie_ID(),movieList.get(pos).getName(), movieList.get(pos).getPrice(),movieList.get(pos).getTumbnail(),new DateClass().setDate(), 1));
                                }
                                count();
                            }

                            @Override
                            public void clicklistener2(View view, int pos) {

                                Intent des=new Intent(MainActivity.this, ShowDes.class);
                                des.putExtra("id",movieList.get(pos).getMovie_ID());
                                //Log.i("iii",movieList.get(pos).getMovie_ID());
                                des.putExtra("pos",position);
                                startActivity(des);
                            }

                        });
                        recyclerView.setAdapter(movieadapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }// results.setText(data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " +"ERROR");
                Toast.makeText(getApplicationContext(),
                        parsFailure(error.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    public static String parsFailure(String error){
        // Log.i("leyla0",error);
        try{

            if (error.contains("Unable to resolve host"))
            { error="اتصال به اینترنت امکان پذیر نیست";
                //Log.i("leyla",error);
            }
            else
            { error="ورود به نرم افزار امکان پذیرنیست.";}
        }
        catch (Exception ex){
            error="خطای ناشناخته ای پیش آمده است.";
            Log.i("error",error);
        }
        return  error;

    }


    private void count() {
           int count=0;
        //String price = null;
            countList= db.getCartMovie();
            for (int i=0;i<countList.size();i++)
            {
                count=(countList.get(i).getCount())+count;
            }
           TextView txt_count=(TextView) findViewById(R.id.badge_notification_3);
            txt_count.setText(String.valueOf(count));
    }

    private JSONArray concatArray(JSONArray... arrs)
            throws JSONException {
        JSONArray result = new JSONArray();
        for (JSONArray arr : arrs) {
            for (int i = 0; i < arr.length(); i++) {
                result.put(arr.get(i));
            }
        }
        return result;
    }


}
