package com.example.wastetowealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wastetowealth.R;
import com.example.wastetowealth.model.DeliveryModel;

import java.util.List;

public class DeliveryUser extends RecyclerView.Adapter<DeliveryUser.DeliveryViewHolder>{
    private List<DeliveryModel> deliveryList;

    public DeliveryUser(List<DeliveryModel> deliveryList) {
        this.deliveryList = deliveryList;
    }
    @NonNull
    @Override
    public DeliveryUser.DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.delivery_user_card, parent, false);
        return new DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryUser.DeliveryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DeliveryViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView age;
        TextView location;
        TextView contactNo;
        TextView status;

        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            location = itemView.findViewById(R.id.location);
            contactNo = itemView.findViewById(R.id.contactNo);
            status = itemView.findViewById(R.id.activeProfile);
        }
        public void bind(DeliveryModel deliveryModel) {
            // Set category name
            name.setText(deliveryModel.getName());

            // Set date
            age.setText(deliveryModel.getAge());
            location.setText(deliveryModel.getLocation());
            contactNo.setText(deliveryModel.getContactNo());

            if (deliveryModel.getStatus().equals("1")) {
                status.setText("Active");
                status.setTextColor(itemView.getContext().getResources().getColor(R.color.green));
            } else {
                status.setText("Inactive");
                status.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
            }
        }

    }

}
