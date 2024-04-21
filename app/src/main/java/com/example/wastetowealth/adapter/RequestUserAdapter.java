package com.example.wastetowealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastetowealth.R;
import com.example.wastetowealth.RequestUserActivity;
import com.example.wastetowealth.model.ShopRegisterFetch;

import java.util.List;

public class RequestUserAdapter extends RecyclerView.Adapter<RequestUserAdapter.CategoryViewHolder> {

    private List<ShopRegisterFetch> categoryList;
    private OnAcceptRejectClickListener onAcceptRejectClickListener;

    public RequestUserAdapter(List<ShopRegisterFetch> categoryList, OnAcceptRejectClickListener listener) {
        this.categoryList = categoryList;
        this.onAcceptRejectClickListener = listener;
    }

    @NonNull
    @Override
    public RequestUserAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.request_user_card, parent, false);
        return new RequestUserAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestUserAdapter.CategoryViewHolder holder, int position) {
        ShopRegisterFetch requestUserModel = categoryList.get(position);
        holder.bind(requestUserModel);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView name,location,contactno;
        ImageButton accept, reject;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            contactno = itemView.findViewById(R.id.contactno);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
        }
        public void bind(ShopRegisterFetch requestUserModel) {
            // Set category name
            name.setText(requestUserModel.getShopName());
            location.setText(requestUserModel.getLocation());
            contactno.setText(requestUserModel.getContactNo());
        }

    }
    public interface OnAcceptRejectClickListener {
        void onAcceptClick(int position);
        void onRejectClick(int position);
    }
}
