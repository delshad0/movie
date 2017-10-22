package com.example.lenovo.Album1.Activity.recyclerview.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.lenovo.Album1.Activity.recyclerview.Database.Message;
import com.example.lenovo.Album1.Activity.recyclerview.R;

import java.util.HashMap;
import java.util.List;


public class MyAdapter extends BaseExpandableListAdapter {

    Context context;
    List<String> headerList;
    HashMap<String, List<Message>> dataList;

    public MyAdapter(Context context, List<String> headerList, HashMap<String, List<Message>> dataList) {
        this.context = context;
        this.headerList = headerList;
        this.dataList = dataList;
    }

    @Override
    public int getGroupCount() {
        return headerList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return dataList.get(headerList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int groupPos, int childPos) {
        return dataList.get(headerList.get(groupPos)).get(childPos);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int groupPos, int childPos) {
        return childPos;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        // TextView header = (TextView) LayoutInflater.from(context).inflate(R.layout.listgroup, viewGroup, false);
        // header.setBackgroundColor(Color.GRAY);
        // header.setText(headerList.get(i));

        LayoutInflater infalInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = infalInflater.inflate(R.layout.list, null);


        TextView header = (TextView) view
                .findViewById(R.id.lblListHeader);
        header.setText("نظرات کاربران");
        return view;
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        // TetView child = (TextView) LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.row_message, null);
        }

        TextView txtListChild = (TextView) view.findViewById(R.id.txt_message);
        TextView username = (TextView) view.findViewById(R.id.txt_username);
        TextView date = (TextView) view.findViewById(R.id.txt_date);
        txtListChild.setText(dataList.get(headerList.get(groupPos)).get(childPos).getMessage());
        username.setText(dataList.get(headerList.get(groupPos)).get(childPos).getUsername());
        date.setText(dataList.get(headerList.get(groupPos)).get(childPos).getDate());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
