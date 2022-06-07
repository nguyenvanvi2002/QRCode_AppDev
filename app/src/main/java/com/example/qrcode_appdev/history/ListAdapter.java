package com.example.qrcode_appdev.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrcode_appdev.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    public List<QrModel> list;
    Context context;

    public ListAdapter(List<QrModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dateTime.setText(list.get(position).getDateTime());
        holder.title.setText(list.get(position).getTitle());
        switch (list.get(position).getType()) {
            case 1:
                holder.imgLogo.setImageResource(R.drawable.ic_link);
                break;
            case 2:
                holder.imgLogo.setImageResource(R.drawable.ic_text);
                break;
            case 3:
                holder.imgLogo.setImageResource(R.drawable.ic_wifi_maincolor);
                break;
            case 4:
                holder.imgLogo.setImageResource(R.drawable.ic_phone);
                break;
            case 5:
                holder.imgLogo.setImageResource(R.drawable.ic_sms);
                break;
            case 6:
                holder.imgLogo.setImageResource(R.drawable.ic_contact);
                break;
            case 7:
                holder.imgLogo.setImageResource(R.drawable.ic_event);
            default:
                holder.imgLogo.setImageResource(R.drawable.ic_email);
        }
        holder.setItemClickListener((view, position1, isLongClick) -> {
            if(isLongClick)
                Toast.makeText(context, "Long Click: "+list.get(position1), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, " "+list.get(position1), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imgLogo;
        TextView title;
        TextView dateTime;
        ConstraintLayout parentLayout;

        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.logo_item);
            title = itemView.findViewById(R.id.title_item);
            dateTime = itemView.findViewById(R.id.content_item);
            parentLayout = itemView.findViewById(R.id.one_item);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false); // Gọi interface , false là vì đây là onClick
        }
        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true); // Gọi interface , true là vì đây là onLongClick
            return true;
        }
    }
}
