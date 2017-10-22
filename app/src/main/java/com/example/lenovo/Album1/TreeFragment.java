package com.example.lenovo.Album1.Activity.recyclerview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lenovo.Album1.Activity.recyclerview.Adapter.MovieAdapter;
import com.example.lenovo.Album1.Activity.recyclerview.Database.Movie;
import com.example.lenovo.Album1.Activity.recyclerview.Database.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.lenovo.Album1.Activity.recyclerview.Activity.MainActivity.parsFailure;


@SuppressLint("ValidFragment")
public class TreeFragment extends Fragment {
    private List<Movie> movieList;
    private RecyclerView mRecyclerView;
    private List<Movie> countList,cartList;
    private MovieAdapter movieadapter;
    private int pos;
    private EditText search;
    private View view;
    private SQLiteHandler db;


    TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            movieadapter.getFilter().filter(s.toString());

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @SuppressLint("ValidFragment")
    public TreeFragment(int i) {
        this.pos=i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.start, container, false);
        cartList=new ArrayList<>();
        movieList=new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        db=new SQLiteHandler(getContext());
        // setMenu();

        search=(EditText) view.findViewById(R.id.edt_search1);
       search.addTextChangedListener(textWatcher);
       //search(search);
        sendRequest(pos);
        return view;
    }

    private void search(EditText search) {
        //final String search_text=search.getText().toString();

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

                        movieadapter=new MovieAdapter(getContext(),movieList, position, new ClickListener() {
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
                                    Toast.makeText(getContext(), "این فیلم قبلا انتخاب شده است.", Toast.LENGTH_LONG).show();
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

                              //  Intent des=new Intent(TreeFragment.this, ShowDes.class);
                               // des.putExtra("id",movieList.get(pos).getMovie_ID());
                                //Log.i("iii",movieList.get(pos).getMovie_ID());
                                //des.putExtra("pos",position);
                                //startActivity(des);
                            }

                        });
                        mRecyclerView.setAdapter(movieadapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }// results.setText(data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " +"ERROR");
                Toast.makeText(getContext(),
                        parsFailure(error.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void count() {
        int count=0;
        //String price = null;
        countList= db.getCartMovie();
        for (int i=0;i<countList.size();i++)
        {
            count=(countList.get(i).getCount())+count;
        }
        TextView txt_count=(TextView) view.findViewById(R.id.badge_notification_3);
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
