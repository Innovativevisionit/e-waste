package com.example.wastetowealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.wastetowealth.adapter.ImageRecycler;
import com.example.wastetowealth.api.LoginApi;
import com.example.wastetowealth.api.UserApi;
import com.example.wastetowealth.databinding.ActivityAddPostsBinding;
import com.example.wastetowealth.model.PostData;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPosts extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    ActivityAddPostsBinding binding;
    List<String> imagePaths = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageRecycler adapter;
SwitchMaterial toggleButton;
    TextInputEditText shops, category, brand, model, condition, minAmount, maxAmount;
    Button submit, toggle;
    boolean isAllFieldsChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        hideAndShow();
        recyclerView = findViewById(R.id.allImages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ImageRecycler(imagePaths);
        recyclerView.setAdapter(adapter);
        popOpen();
        toggleButton = findViewById(R.id.shop_all);
        shops = findViewById(R.id.shop_text);
        category = findViewById(R.id.category_text);
        brand = findViewById(R.id.brand_text);
        model = findViewById(R.id.model_text);
        condition = findViewById(R.id.condition_text);
        minAmount = findViewById(R.id.min_amount_text);
        maxAmount = findViewById(R.id.max_amount_text);
        submit = findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();
                if(!isAllFieldsChecked) {
                    Toast.makeText(AddPosts.this, "Enter all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                handleSubmit();
            }
        });
    }

    private boolean CheckAllFields() {
        if(toggleButton.isChecked() && shops.length() == 0) {
            shops.setError("This field is required");
            return false;
        }
        if(category.length() == 0) {
            category.setError("This field is required");
            return false;
        }
        if(brand.length() == 0) {
            brand.setError("This field is required");
            return false;
        }
        if(model.length() == 0) {
            model.setError("This field is required");
            return false;
        }
        if(condition.length() == 0) {
            condition.setError("This field is required");
            return false;
        }
        if(minAmount.length() == 0) {
            minAmount.setError("This field is required");
            return false;
        }
        if(maxAmount.length() == 0) {
            maxAmount.setError("This field is required");
            return false;
        }
        return true;
    }

    private void handleSubmit() {
        // Create a PostData object with form data
        PostData postData = new PostData();
        List<String> shopId = new ArrayList<>();
        shopId.add(shops.getText().toString());
        postData.setAllShop("Yes");
        postData.setShopId(shopId);
        postData.setCategories(category.getText().toString());
        postData.setBrand(brand.getText().toString());
        postData.setModel(model.getText().toString());
        postData.setCondition(condition.getText().toString());
        postData.setMinAmount(minAmount.getText().toString());
        postData.setMaxAmount(maxAmount.getText().toString());
        postData.setImages(imagePaths);

        System.out.println("post data" + postData);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        Call<PostData> callApi = userApi.doPostData(postData);

        System.out.println("Call Api" + callApi);
        callApi.enqueue(new Callback<PostData>() {
            @Override
            public void onResponse(Call<PostData> call, Response<PostData> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body().toString());
                    Toast.makeText(AddPosts.this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddPosts.this, "Failed to submit form", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostData> call, Throwable t) {
                Toast.makeText(AddPosts.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });;
    }

    private void popOpen() {
        toggle = findViewById(R.id.image_select);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(AddPosts.this, toggle);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.image_option_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_camera) {
                            dispatchTakePictureIntent();
                            return true;
                        } else if (item.getItemId() == R.id.menu_gallery) {
                            chooseImageFromGallery();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                if (extras != null && extras.containsKey("data")) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    File imageFile = saveImageToFile(imageBitmap);
                    imagePaths.add(imageFile.getAbsolutePath());
                    updateAdapter();
                } else {
                    Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_IMAGE_GALLERY) {
                if (data != null) {
                    ClipData clipData = data.getClipData();
                    if (clipData != null) {
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri imageUri = clipData.getItemAt(i).getUri();
                            Bitmap bitmap = uriToBitmap(this, imageUri);
                            if (bitmap != null) {
                                File imageFile = saveImageToFile(bitmap);
                                imagePaths.add(imageFile.getAbsolutePath());
                            }
                        }
                    } else {
                        Uri imageUri = data.getData();
                        Bitmap bitmap = uriToBitmap(this, imageUri);
                        if (bitmap != null) {
                            File imageFile = saveImageToFile(bitmap);
                            imagePaths.add(imageFile.getAbsolutePath());
                        }
                    }
                    updateAdapter();
                }
            }
        }
    }

    private void updateAdapter() {
        adapter.setImagePaths(imagePaths);
        adapter.notifyDataSetChanged();
    }
    public static Bitmap uriToBitmap(Context context, Uri uri) {
        try {
            // Use ContentResolver to open an InputStream from the URI
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                // Decode the InputStream into a Bitmap using BitmapFactory
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close(); // Close the InputStream to release resources
                return bitmap; // Return the Bitmap
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if an error occurs
    }
    public static File saveImageToFile(Bitmap imageBitmap) {
        // Create a directory for your images if it doesn't exist
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "WasteToWealth");
        if (!directory.exists()) {
            directory.mkdirs(); // Make directories if they don't exist
        }

        // Create a unique filename for the image
        String filename = "image_" + System.currentTimeMillis() + ".jpg";

        // Create a new file object
        File imageFile = new File(directory, filename);

        try {
            // Create a FileOutputStream to write the bitmap to the file
            FileOutputStream outputStream = new FileOutputStream(imageFile);

            // Compress the bitmap and write it to the output stream
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            // Flush and close the output stream
            outputStream.flush();
            outputStream.close();

            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if an error occurs
        }
    }
    private void hideAndShow() {

        SwitchMaterial switchMaterial = findViewById(R.id.shop_all);
        TextInputLayout shopLayout = findViewById(R.id.shop);
        TextInputEditText shopText = findViewById(R.id.shop_text);
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Show or hide the shop_text field based on the switch state
                if (isChecked) {
                    // Show the shop_text field
                    shopLayout.setVisibility(View.VISIBLE);
                    shopText.setVisibility(View.VISIBLE);
                } else {
                    // Hide the shop_text field
                    shopLayout.setVisibility(View.GONE);
                    shopText.setVisibility(View.GONE);
                }
            }
        });
    }
}