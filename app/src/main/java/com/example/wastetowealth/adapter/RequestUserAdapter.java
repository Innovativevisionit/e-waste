package com.example.wastetowealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wastetowealth.R;
import com.example.wastetowealth.model.CategoryModel;
import com.example.wastetowealth.model.RequestUserModel;

import java.util.List;

public class RequestUserAdapter extends RecyclerView.Adapter<RequestUserAdapter.CategoryViewHolder> {

    private List<RequestUserModel> categoryList;

    public RequestUserAdapter(List<RequestUserModel> categoryList) {
        this.categoryList = categoryList;
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
        RequestUserModel requestUserModel = categoryList.get(position);

        holder.bind(requestUserModel);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView location;
        TextView contactno;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            contactno = itemView.findViewById(R.id.contactno);
        }
        public void bind(RequestUserModel requestUserModel) {
            // Set category name
            name.setText(requestUserModel.getName());

            // Set date
            location.setText(requestUserModel.getLocation());
            contactno.setText(requestUserModel.getContactNumber());
        }

    }
}
