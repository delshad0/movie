package com.example.lenovo.Album1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lenovo.Album1.R;
import com.example.lenovo.Album1.Database.ImageModel;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class SliderPagerAdapter extends PagerAdapter{

    private ArrayList<ImageModel> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;


    public  SliderPagerAdapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
       // Log.i("leyla","leyla");
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slider, view, false);
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);

        Glide.with(context)
                .load(imageModelArrayList.get(position).getImage_drawable())
                .into(imageView);

        view.addView(imageLayout);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
