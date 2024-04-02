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
import com.example.wastetowealth.model.UserPostCards;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserPostCardRecyclerAdapter extends RecyclerView.Adapter<UserPostCardRecyclerAdapter.UserPostViewHolder>{

    private Context context;
    private ArrayList<UserPostCards> userPostCardsList;
    private OnItemClickListener clickListener;

    public UserPostCardRecyclerAdapter(Context context, ArrayList<UserPostCards> userPostCardsList) {
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
        final int currentPosition = holder.getAdapterPosition();
        UserPostCards userPostCard = userPostCardsList.get(currentPosition);
        Picasso.get()
                .load(userPostCard.getPostImage()) // Image URL from the current item
                .placeholder(R.drawable.purple) // Placeholder image while loading
                .into(holder.productImage);
        holder.productName.setText(userPostCard.getPostName());
        holder.category.setText(userPostCard.getCategory());
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
        return userPostCardsList.size();
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
