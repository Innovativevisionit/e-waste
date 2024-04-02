package com.example.wastetowealth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wastetowealth.api.MasterApis;
import com.example.wastetowealth.model.ShopRegister;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddShopRequest extends AppCompatActivity {
    private static final int REQUEST_IMAGE_GALLERY = 2;
    Button submit, toggle;
    TextInputEditText shopName,contactNo,location,category,recycleMethods,website,socialLink;
    SwitchMaterial hazard;
    ShopRegister shopRegister;
    List<Uri> selectedImages = new ArrayList<>(); // List to store selected images URIs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_request);

        submit = findViewById(R.id.submit_button);
        popOpen();

        submit.setOnClickListener(v -> {
            addForm();
        });

    }

    public void addForm() {

        MultipartBody.Part[] imageParts = new MultipartBody.Part[selectedImages.size()];
        for (int i = 0; i < selectedImages.size(); i++) {
            Uri uri = selectedImages.get(i);
            String filePath = saveImageToFile(this, uri);
            if (filePath != null) {
                File file = new File(filePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                imageParts[i] = MultipartBody.Part.createFormData("images", file.getName(), requestFile);
            } else {
                Log.e("YourClass", "File path is null for URI: " + uri.toString());
                // Handle this case as per your app's requirements
            }
        }

        shopName = findViewById(R.id.shop_name_text);
        contactNo = findViewById(R.id.contact_no_text);
        location = findViewById(R.id.location_text);
        category = findViewById(R.id.category_text);
        recycleMethods = findViewById(R.id.recycling_methods_text);
        hazard = findViewById(R.id.hazard);
        website = findViewById(R.id.website_text);
        socialLink = findViewById(R.id.social_link_text);
        String shopNameValue = shopName.getText().toString();
        String contactNoValue = contactNo.getText().toString();
        String locationValue = location.getText().toString();
        String categoryValue = category.getText().toString(); // Assuming category is a Spinner
        String recycleMethodsValue = recycleMethods.getText().toString();
        String hazardValue = hazard.getText().toString();
        String websiteValue = website.getText().toString();
        String socialLinkValue = socialLink.getText().toString();

        RequestBody shopNameBody = RequestBody.create(MediaType.parse("text/plain"), shopNameValue);
        RequestBody contactNoBody = RequestBody.create(MediaType.parse("text/plain"), contactNoValue);
        RequestBody locationBody = RequestBody.create(MediaType.parse("text/plain"), locationValue);
        RequestBody categoryBody = RequestBody.create(MediaType.parse("text/plain"), categoryValue);
        RequestBody recycleMethodsBody = RequestBody.create(MediaType.parse("text/plain"), recycleMethodsValue);
        RequestBody hazardBody = RequestBody.create(MediaType.parse("text/plain"), hazardValue);
        RequestBody websiteBody = RequestBody.create(MediaType.parse("text/plain"), websiteValue);
        RequestBody socialLinkBody = RequestBody.create(MediaType.parse("text/plain"), socialLinkValue);


        String data = "PostData{" +
                "shopNameBody='" + shopNameValue + '\'' +
                ", contactNoBody=" + contactNoValue +
                ", locationBody='" + locationValue + '\'' +
                ", images=" + Arrays.toString(imageParts) + // Assuming imageParts is an array
                ", categoryBody='" + categoryValue + '\'' +
                ", recycleMethodsBody='" + recycleMethodsValue + '\'' +
                ", hazardBody='" + hazardValue + '\'' +
                ", websiteBody='" + websiteValue + '\'' +
                ", socialLinkBody='" + socialLinkValue + '\'' +
                '}';


        RetrofitService retrofitService = new RetrofitService();
        MasterApis masterApis = retrofitService.getRetrofit().create(MasterApis.class);

        Call<Object> call = masterApis.submitFormWithImages(
                shopNameBody,
                contactNoBody,
                imageParts,
                locationBody,
                categoryBody,
                recycleMethodsBody,
                hazardBody,
                websiteBody,
                socialLinkBody
        );

        // Retrofit enqueue
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    System.out.println("Success");
                    Intent intent = new Intent(AddShopRequest.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
//                    Fragment profileFragment = new ProfileFragment();
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(android.R.id.content, profileFragment); // Replace the entire content view
//                    transaction.addToBackStack(null); // Optional: Add to back stack if needed
//                    transaction.commit();
                    Toast.makeText(AddShopRequest.this, "Form submitted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(AddShopRequest.this, "Failed to submit form", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // Handle failure
                t.printStackTrace();
                Toast.makeText(AddShopRequest.this, "Error submitting form: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getRealPathFromURI(Context context, Uri uri) {
        if (uri == null) {
            return null; // Return null if the URI is null
        }
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePath = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            Log.e("YourClass", "Error retrieving file path from URI", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return filePath;
    }
    public static String saveImageToFile(Context context, Uri uri) {
        // Create a directory for your images if it doesn't exist
        File directory = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "WasteToWealth");
        if (!directory.exists()) {
            directory.mkdirs(); // Make directories if they don't exist
        }

        // Create a unique filename for the image
        String filename = "image_" + System.currentTimeMillis() + ".jpg";

        // Create a new file object
        File imageFile = new File(directory, filename);

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                Log.e("YourClass", "Failed to open input stream for URI: " + uri.toString());
                return null;
            }
            OutputStream outputStream = new FileOutputStream(imageFile);

            // Copy data from input stream to output stream
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            // Close streams
            inputStream.close();
            outputStream.flush();
            outputStream.close();

            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if an error occurs
        }
    }
    private void popOpen() {
        toggle = findViewById(R.id.image_select_sor);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromGallery();
            }
        });
    }
    private void chooseImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK && data != null) {
            // Handle single image selection

            System.out.println(data.getData());
            Uri imageUri = data.getData();
            selectedImages.add(imageUri);

            // Handle multiple image selection
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(uri);
                }
            }
        }
    }
//    public void BasicMessage() {
//        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Are you sure?")
//                .setContentText("You want to submit the form, its logged out!")
//                .setConfirmText("Sure!")
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        Intent intent = new Intent(AddShopRequest.this,MainActivity.class);
//                        startActivity(intent);
//                    }
//                })
//                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog.dismissWithAnimation();
//                    }
//                })
//                .show();
//    }

}