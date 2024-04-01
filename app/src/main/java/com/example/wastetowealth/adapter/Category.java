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
import com.example.wastetowealth.model.CategoryModel;

import java.util.List;

public class Category extends RecyclerView.Adapter<Category.CategoryViewHolder> {
    private List<CategoryModel> categoryList;

    public Category(List<CategoryModel> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Category.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Category.CategoryViewHolder holder, int position) {
        CategoryModel categoryModel = categoryList.get(position);

        holder.bind(categoryModel);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView category;
        TextView date;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            date = itemView.findViewById(R.id.date);
        }
        public void bind(CategoryModel categoryModel) {
            // Set category name
            category.setText(categoryModel.getCategory());

            // Set date
            date.setText(categoryModel.getStatus());

            // Set text color based on status
            System.out.println(categoryModel.getStatus().equals("1"));
            if (categoryModel.getStatus().equals("1")) {
                date.setText("Active");
                date.setTextColor(itemView.getContext().getResources().getColor(R.color.green));
            } else {
                date.setText("Inactive");
                date.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
            }
        }

    }
}
