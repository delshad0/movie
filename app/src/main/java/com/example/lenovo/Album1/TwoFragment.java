package com.example.lenovo.Album1;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.lenovo.Album1.Database.ImageModel;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static android.content.ContentValues.TAG;
import static com.example.lenovo.Album1.Activity.MainActivity.parsFailure;

/**
 * Created by LENOVO on 10/13/2017.
 */

public class TwoFragment extends Fragment {
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static ArrayList<ImageModel> imageModelArrayList;
    private CircleIndicator mIndicator;
    private ViewPager mPager;
    private SliderPagerAdapter mPagerAdapter;


    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageModelArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);
        mIndicator = (CircleIndicator) rootView.findViewById(R.id.indicator6);
        mPager = (ViewPager) rootView.findViewById(R.id.two_frag_viewPager);

        mPagerAdapter = new SliderPagerAdapter(getContext(), imageModelArrayList);
        mPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mPager);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendRequest();
    }

    private void init() {
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
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

    private void sendRequest() {
        imageModelArrayList.add(new ImageModel("https://www.w3schools.com/w3images/fjords.jpg"));
        imageModelArrayList.add(new ImageModel("https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg"));
        imageModelArrayList.add(new ImageModel("https://media-cdn.tripadvisor.com/media/photo-s/04/5c/1a/e6/hardanger.jpg"));

        mPagerAdapter.notifyDataSetChanged();
        mIndicator.setViewPager(mPager);

//        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(AppConfig.slider_URL,
//                null, new Response.Listener<JSONObject>() {
//            public void onResponse(JSONObject response) {
//                //for (int i = 0; i < response.length(); i++) {
//
//                try {
//                    imageModelArrayList.add(new ImageModel(response.getString("s1")));
//                    imageModelArrayList.add(new ImageModel(response.getString("s2")));
//                    imageModelArrayList.add(new ImageModel(response.getString("s3")));
//                    init();
//                    //  }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    //  }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + "ERROR");
//                Toast.makeText(getContext(),
//                        parsFailure(error.getMessage()), Toast.LENGTH_LONG).show();
//            }
//        });
//        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
