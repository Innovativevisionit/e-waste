package com.example.wastetowealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastetowealth.R;
import com.example.wastetowealth.model.PostUserData;
import com.example.wastetowealth.model.ShopRegisterFetch;
import com.example.wastetowealth.retrofit.ApiConfig;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserPostRecycler extends RecyclerView.Adapter<UserPostRecycler.ViewHolder>{

    private final Context context;
    private final List<PostUserData> dataList;
    private UserPostRecycler.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(UserPostRecycler.OnItemClickListener listener) {
        this.listener = listener;
    }

    public UserPostRecycler(Context context, List<PostUserData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public UserPostRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dashboard_cards, parent, false);
        return new UserPostRecycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostRecycler.ViewHolder holder, int position) {
        PostUserData item = dataList.get(position);
        if (item.getImages() != null) {
            Picasso.get().load(ApiConfig.IMAGE_URL + item.getImages().get(0)).into(holder.imageView);
        } else {
            Picasso.get().load(R.drawable.purple).into(holder.imageView);
        }
        holder.storeNameTextView.setText(item.getName());
        holder.locationTextView.setText(Math.toIntExact(item.getMinAmount()));
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
