package com.example.wastetowealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastetowealth.R;
import com.example.wastetowealth.model.PostData;
import com.example.wastetowealth.model.PostUserData;
import com.example.wastetowealth.model.ShopRegisterFetch;

import java.util.List;

public class RequestUserRecycler  extends RecyclerView.Adapter<RequestUserRecycler.ViewHolder> {
    private int selectedPosition = RecyclerView.NO_POSITION;
    private List<PostUserData> postData;
    private OnItemClickListener listener;

    public RequestUserRecycler(List<PostUserData> postData, OnItemClickListener listener) {
        this.postData = postData;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RequestUserRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_post_main_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestUserRecycler.ViewHolder holder, int position) {
        PostUserData item = postData.get(position);
        holder.categoryText.setText(item.getEcategoryName());
        holder.brandText.setText(item.getBrand());
        holder.condition.setText(item.getPostCondition());
        holder.maxAmount.setText(String.valueOf(Long.valueOf(item.getMaxAmount())));
        holder.itemView.setSelected(selectedPosition == holder.getAdapterPosition());
        holder.acceptButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAcceptClick(position);
            }
        });

        holder.rejectButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRejectClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return postData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryText;
        public TextView brandText;
        public TextView condition;
        public TextView maxAmount;

        public ImageButton acceptButton, rejectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.category_edit);
            brandText = itemView.findViewById(R.id.brand_edit);
            condition = itemView.findViewById(R.id.condition_edit);
            maxAmount = itemView.findViewById(R.id.max_price_text);
            acceptButton = itemView.findViewById(R.id.acceptPost);
            rejectButton = itemView.findViewById(R.id.rejectPost);
        }

    }
    public interface OnItemClickListener {
        void onAcceptClick(int position);

        void onRejectClick(int position);
    }
}
