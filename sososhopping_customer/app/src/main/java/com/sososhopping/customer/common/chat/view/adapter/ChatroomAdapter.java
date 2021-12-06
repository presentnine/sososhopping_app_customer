package com.sososhopping.customer.common.chat.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.R;
import com.sososhopping.customer.common.chat.ChatroomInfor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatroomAdapter extends RecyclerView.Adapter {

    Context context;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    ArrayList<ChatroomInfor> chatroomInforList;

    public ChatroomAdapter(Context context, ArrayList<ChatroomInfor> chatroomInforList) {
        this.context = context;
        this.chatroomInforList = chatroomInforList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatroom_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).chatroomStoreName.setText(chatroomInforList.get(position).storeName);
        ((ViewHolder)holder).chatroomItemContent.setText(chatroomInforList.get(position).lastMessage);

        String timeStamp = chatroomInforList.get(position).lastMessageTimestamp.toString();
        Long time = Long.parseLong(timeStamp);
        Date date = new Date(time + 1000 * 60 * 60 * 9);

        ((ViewHolder)holder).chatroomItemTime.setText(simpleDateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        return chatroomInforList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView chatroomStoreName;
        public TextView chatroomItemTime;
        public TextView chatroomItemContent;

        public ViewHolder(View v) {
            super(v);
            this.chatroomStoreName = v.findViewById(R.id.chatroomItemStoreName);
            this.chatroomItemTime = v.findViewById(R.id.chatroomItemTime);
            this.chatroomItemContent = v.findViewById(R.id.chatroomItemContent);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        ChatroomInfor chatroomInfor = chatroomInforList.get(pos);
                        Bundle bundle = new Bundle();
                        bundle.putString("chatroomId", chatroomInfor.chatroomId);
                        bundle.putString("storeName", chatroomInfor.storeName);
                        Navigation.findNavController(v).navigate(R.id.action_chatFragment_to_conversationFragment, bundle);
                    }
                }
            });
        }
    }
}
