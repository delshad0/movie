package com.example.lenovo.Album1.Activity.recyclerview.Adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.Album1.Activity.recyclerview.Database.Message;
import com.example.lenovo.Album1.Activity.recyclerview.R;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    public String name;
    public boolean isFav;
    Context mContext;
    private List<Message> messageList;
    public MessageAdapter(Context context, List<Message> list){
        this.mContext=context;
        this.messageList=list;
       // Log.i("leylaleyla", String.valueOf(messageList));


    }
    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView username, date, message;

        public MyViewHolder(View view) {
            super(view);
           // username = (TextView) view.findViewById(R.id.txt_username);
            date = (TextView) view.findViewById(R.id.txt_date);
            //message= (TextView) view.findViewById(R.id.txt_message);
        }
    }

    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_message, parent, false);
        return new MessageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {
       Message message = messageList.get(position);
       // Log.i("username",message.getUsername());
        //Log.i("date",message.getDate());
       // Log.i("message",message.getMessage());
      // holder.username.setText(message.getUsername());
       holder.date.setText(message.getDate());
      // holder.message.setText(message.getMessage());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
