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
import com.example.wastetowealth.model.UserPostCards;
import com.example.wastetowealth.retrofit.ApiConfig;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserPostCardRecyclerAdapter extends RecyclerView.Adapter<UserPostCardRecyclerAdapter.UserPostViewHolder>{

    private Context context;
    private ArrayList<PostUserData> userPostCardsList;
    private OnItemClickListener clickListener;

    public UserPostCardRecyclerAdapter(Context context, ArrayList<PostUserData> userPostCardsList) {
        this.context = context;
        this.userPostCardsList = userPostCardsList;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onHeartClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }
    @NonNull
    @Override
    public UserPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.post_main_card, parent, false);
        return new UserPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostViewHolder holder, int position) {
        if (userPostCardsList == null || userPostCardsList.isEmpty()) {
            return; // Return if list is empty or null
        }
        final int currentPosition = holder.getAdapterPosition();
        PostUserData userPostCard = userPostCardsList.get(currentPosition);
        Picasso.get()
                .load(ApiConfig.IMAGE_URL + userPostCard.getImages().get(0)) // Image URL from the current item
                .placeholder(R.drawable.purple) // Placeholder image while loading
                .into(holder.productImage);
        holder.productName.setText(userPostCard.getName());
        holder.category.setText(userPostCard.getEcategoryName());
//        holder.likeOrUnlike.setImageResource(userPostCard.getLikeOrUnlike());
//        holder.send.setImageResource(userPostCard.getShare());
//        holder.options.setImageResource(userPostCard.getOptions());

//        holder.likeOrUnlike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (clickListener != null) {
//                    clickListener.onHeartClick(currentPosition);
//                }
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClick(currentPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userPostCardsList == null ? 0 : userPostCardsList.size();
    }

    public class UserPostViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView category;
//        ImageView likeOrUnlike;
//        ImageView send;
//        ImageView options;

        public UserPostViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageStore);
            productName = itemView.findViewById(R.id.productName);
            category = itemView.findViewById(R.id.category);
//            likeOrUnlike = itemView.findViewById(R.id.heart);
//            send = itemView.findViewById(R.id.send);
//            options = itemView.findViewById(R.id.kebab);

            // Add onClickListener or any other operation if required
        }
    }
}
