package com.example.wastetowealth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wastetowealth.retrofit.MySharedPreferences;

import cn.pedant.SweetAlert.SweetAlertDialog;

//import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        AppCompatButton reqToSO = view.findViewById(R.id.reqToSO);
        AppCompatButton personalInformation = view.findViewById(R.id.personalInformation);
        AppCompatButton logoutProfile = view.findViewById(R.id.logoutProfile);
        TextView userNamePro = view.findViewById(R.id.userNameProfile);
        TextView emailPro = view.findViewById(R.id.emailProfile);
        MySharedPreferences sharedPreferences = MySharedPreferences.getInstance(getContext());
        String username = sharedPreferences.getString("username", "Default");
        String email = sharedPreferences.getString("email", "Default");
        userNamePro.setText(username);
        emailPro.setText(email);
        personalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfilePage.class);
                startActivity(intent);
            }
        });
        logoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        reqToSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BasicMessage();
            }
        });

        return view;
    }
    public void BasicMessage() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to change shop owner")
                .setConfirmText("Sure!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent intent = new Intent(getActivity(),AddShopRequest.class);
                        startActivity(intent);
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
}