package com.example.lenovo.Album1.Activity.recyclerview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.lenovo.Album1.Activity.recyclerview.Adapter.MyAdapter;
import com.example.lenovo.Album1.Activity.recyclerview.AppConfig;
import com.example.lenovo.Album1.Activity.recyclerview.AppController;
import com.example.lenovo.Album1.Activity.recyclerview.Database.Message;
import com.example.lenovo.Album1.Activity.recyclerview.Database.SQLiteHandler;
import com.example.lenovo.Album1.Activity.recyclerview.DateClass;
import com.example.lenovo.Album1.Activity.recyclerview.R;
import com.example.lenovo.Album1.Activity.recyclerview.SliderPagerAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static android.content.ContentValues.TAG;

public class ShowDes extends AppCompatActivity {



    private List<Message> messageList;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<Integer> slider_image_list=new ArrayList<>();
    private ViewPager vp_slider;
    private LinearLayout ll_dots;

    private ExpandableListView expandableListView;
    private MyAdapter adapter;
    private RecyclerView recyclerview;
    List<String> listDataHeader;
    private SQLiteHandler db;
    String id;
    int pos;
    private EditText name, email, message;
    private TextView des, dur, dir, made, haj, lan, rul;
    private TextView[] dots;
    TextView NAME;
    int page_position = 0;
    // private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_des);
        db=new SQLiteHandler(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
             pos = extras.getInt("pos");
            id = extras.getString("id");
            sendRequest(pos, id);
            showMessages(pos,id);
        }
        Button send = (Button) findViewById(R.id.btn_send);
        name = (EditText) findViewById(R.id.edt_name);
        email = (EditText) findViewById(R.id.edt_email);
        message = (EditText) findViewById(R.id.edt_message);
        des = (TextView) findViewById(R.id.txt_des);
        dur = (TextView) findViewById(R.id.txt_dur);
        dir = (TextView) findViewById(R.id.txt_dir);
        made = (TextView) findViewById(R.id.txt_made);
        haj = (TextView) findViewById(R.id.txt_haj);
        lan = (TextView) findViewById(R.id.txt_lan);
        rul = (TextView) findViewById(R.id.txt_rul);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name = name.getText().toString().trim();
                String _email = email.getText().toString().trim();
                String _message = message.getText().toString().trim();
                sendMessage(_name, _email, _message,id,pos);
            }
        });


    //    init();
        //sendRequest();
//        addBottomDots(0);

       /* final Handler handler = new Handler() {
            @Override
            public void publish(LogRecord logRecord) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                ll_dots.post(update);
            }
        }, 100, 5000);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();*/
    }

    private void showMessages(int pos,String id) {
        // Tag used to cancel the request
       // HashMap dictMap = new HashMap<String, ArrayList<Message>>();        // Adding child data
        //HashMap<String, List<String>> listDataChild;


        messageList=db.getMessage(pos,id);
       prepareListData(messageList);
    }

    private void prepareListData(List<Message> list) {
        expandableListView = (ExpandableListView) findViewById(R.id.exv_message);
        List<String> headers = new ArrayList<>();
        headers.add(" نظرات کاربران " );
        HashMap<String, List<Message>> childs = new HashMap<>();
        List<Message> childList = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            childList.add(new Message(list.get(j).getMessage(),list.get(j).getUsername(),list.get(j).getDate()));
        }
        childs.put(headers.get(0), childList);
        adapter = new MyAdapter(this, headers, childs);
        expandableListView.setAdapter(adapter);
    }

    private void sendMessage(final String name, final String email, final String message,final  String id,final int pos) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.Message_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

               try {
                    JSONObject jObj = new JSONObject(response);
                    Boolean error = jObj.getBoolean("error");
                    if(!error){
                        db.addMessage(name,email,message,id,pos,new DateClass().setDate());

                        Toast.makeText(getApplicationContext(), "نظرشماثبت شد.!", Toast.LENGTH_LONG).show();

                        showMessages(pos,id);
                    }
                    else{
                        // Error occurred in registration. Get the error
                        String errorMsg = jObj.getString(String.valueOf("error_msg"));
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        parsFailure(error.getMessage()), Toast.LENGTH_LONG).show();
            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", name);
                params.put("email", email);
                params.put("message", message);
                params.put("movie_id", id);
                params.put("pos",String.valueOf(pos));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static String parsFailure(String error){
        try{

            if (error.contains("Unable to resolve host"))
            { error="اتصال به اینترنت امکان پذیر نیست";
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
    private void sendRequest(final int pos, final String id) {


        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(AppConfig.Movie_URL,
                null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                JSONObject kind = null;
                JSONArray a = null;
                try {
                    Log.i("position", String.valueOf(pos));

                    kind = (JSONObject) response.get(String.valueOf(pos));
                    JSONArray tv = kind.getJSONArray("TV");
                    JSONArray movie = kind.getJSONArray("MOVIE");
                    a = concatArray(tv, movie);

                    for (int i = 0; i < a.length(); i++) {
                        if ((a.getJSONObject(i).get("id")).equals(String.valueOf(id))) {
                            des.setText(String.valueOf(a.getJSONObject(i).get("des")));
                            dir.setText(String.valueOf(a.getJSONObject(i).get("dir")));
                            lan.setText(String.valueOf(a.getJSONObject(i).get("lan")));
                            rul.setText(String.valueOf(a.getJSONObject(i).get("rul")));
                            haj.setText(String.valueOf(a.getJSONObject(i).get("haj")));
                            made.setText(String.valueOf(a.getJSONObject(i).get("made")));
                            dur.setText(String.valueOf(a.getJSONObject(i).get("dur")));
                            break;
                        }

                        // Log.i("leylaa", (String) a.getJSONObject(i).get("id"));
                       /* (String.valueOf(a.getJSONObject(i).get("id")),String.valueOf(a.getJSONObject(i).get("name"))
                                ,String.valueOf(a.getJSONObject(i).get("price"))
                                ,String.valueOf(a.getJSONObject(i).get("image")));

                      */
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }// results.setText(data);

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + "ERROR");
                Toast.makeText(getApplicationContext(),
                        parsFailure(error.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
    private JSONArray concatArray(JSONArray... arrs) throws JSONException {
        JSONArray result = new JSONArray();
        for (JSONArray arr : arrs) {
            for (int i = 0; i < arr.length(); i++) {
                result.put(arr.get(i));
            }
        }
        return result;
    }


  /*  private void init() {

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().hide();
       vp_slider = (ViewPager) findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);

        // slider_image_list.add(R.drawable.coblivion);
        slider_image_list.add(Integer.valueOf(R.drawable.film));
        slider_image_list.add(Integer.valueOf(R.drawable.postimg));
        slider_image_list.add(Integer.valueOf(R.drawable.postimgi));
        //slider_image_list.add(Integer.valueOf(R.drawable.wushu));
        //slider_image_list.add("http://images.all-free-download.com/images/graphiclarge/bird_mountain_bird_animal_226401.jpg");


        sliderPagerAdapter = new SliderPagerAdapter(ShowDes.this, slider_image_list);
        vp_slider.setAdapter(sliderPagerAdapter);

        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }*/

  /*  private void addBottomDots(int currentPage) {
        dots = new TextView[slider_image_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }*/
}