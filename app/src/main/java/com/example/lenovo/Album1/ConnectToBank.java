package com.example.lenovo.Album1.Activity.recyclerview;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import java.util.List;


public class ConnectToBank extends DialogFragment {

    TextView dur,lan,made,haj,rul,dir,des;
    public ConnectToBank newInstance(List<Movie> list, int position)
    {
        Movie movie = list.get(position);
        ConnectToBank frag = new ConnectToBank();
        Bundle args=  new Bundle();
        /*args.putString("dur", movie);
        args.putString("lan", movie.getLan());
        args.putString("made",movie.getMade());
        args.putString("haj",movie.getHaj());
        args.putString("rul",movie.getRul());
        args.putString("dir",movie.getDir());
        args.putString("des",movie.getDes());
        args.putString("s1",movie.getS1());
        args.putString("s2",movie.getS2());
        args.putString("s3",movie.getS3());
        frag.setArguments(args);*/
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_detail_row, container,false);
        // db = new DatabaseHandler(getActivity());
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dur=(TextView) view.findViewById(R.id.txt_dur);
        lan=(TextView) view.findViewById(R.id.txt_lan);
        made=(TextView) view.findViewById(R.id.txt_made);
        haj=(TextView) view.findViewById(R.id.txt_haj);
        rul=(TextView) view.findViewById(R.id.txt_rul);
        dir=(TextView) view.findViewById(R.id.txt_dir);
        des=(TextView) view.findViewById(R.id.txt_des);
        dur.setText(getArguments().getString("dur"));
        lan.setText(getArguments().getString("lan"));
        made.setText(getArguments().getString("made"));
        haj.setText(getArguments().getString("haj"));
        rul.setText(getArguments().getString("rul"));
        dir.setText(getArguments().getString("dir"));
        des.setText(getArguments().getString("des"));
    }

}
