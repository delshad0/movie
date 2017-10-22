package com.example.lenovo.Album1.Activity.recyclerview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;
import static com.example.lenovo.Album1.Activity.recyclerview.Activity.MainActivity.parsFailure;

/**
 * Created by LENOVO on 10/13/2017.
 */

public class TwoFragment extends Fragment {
private  View view;
    private ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static ArrayList<com.example.lenovo.Album1.Activity.recyclerview.ImageModel> imageModelArrayList;


    public TwoFragment(int i) {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        inflater.inflate(R.layout.fragment, container, false);
        imageModelArrayList=new ArrayList<>();
        sendRequest();
        return view;
    }

    private void init() {

      //  mPager = (ViewPager) view.findViewById(R.id.jjjjjj);

       // mPager.setAdapter(new SliderPagerAdapter(getContext(),imageModelArrayList));
        CirclePageIndicator indicator = (CirclePageIndicator)
               view.findViewById(R.id.indicator6);

        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();
        Log.i("num", String.valueOf(NUM_PAGES));
        // Auto start of viewpager
        final android.os.Handler handler = new android.os.Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
    private void sendRequest(){
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(AppConfig.slider_URL,
                null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                //for (int i = 0; i < response.length(); i++) {

                try {
                    imageModelArrayList.add(new com.example.lenovo.Album1.Activity.recyclerview.ImageModel(response.getString("s1")));
                    imageModelArrayList.add(new com.example.lenovo.Album1.Activity.recyclerview.ImageModel(response.getString("s2")));
                    imageModelArrayList.add(new com.example.lenovo.Album1.Activity.recyclerview.ImageModel(response.getString("s3")));
                    init();
                    //  }


                } catch (JSONException e) {
                    e.printStackTrace();
                    //  }
                }
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
}
