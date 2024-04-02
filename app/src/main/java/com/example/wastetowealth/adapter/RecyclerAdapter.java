package com.example.wastetowealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastetowealth.HomeFragment;
import com.example.wastetowealth.R;
import com.example.wastetowealth.model.DashboardCards;
import com.example.wastetowealth.model.ShopRegisterFetch;
import com.example.wastetowealth.retrofit.ApiConfig;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final Context context;
    private final List<ShopRegisterFetch> dataList;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecyclerAdapter(Context context, List<ShopRegisterFetch> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dashboard_cards, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShopRegisterFetch item = dataList.get(position);
        // Bind data to views in the ViewHolder
        if (item.getImages() != null && item.getImages().length > 0) {
            Picasso.get().load(ApiConfig.IMAGE_URL + item.getImages()[0]).into(holder.imageView);
        } else {
            Picasso.get().load(R.drawable.purple).into(holder.imageView);
        }        holder.storeNameTextView.setText(item.getShopName());
        holder.locationTextView.setText(item.getLocation());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView storeNameTextView;
        public TextView locationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageStore);
            storeNameTextView = itemView.findViewById(R.id.storeText);
            locationTextView = itemView.findViewById(R.id.storeLocation);
        }

    }

}
