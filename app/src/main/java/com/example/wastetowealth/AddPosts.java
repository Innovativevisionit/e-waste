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
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wastetowealth.adapter.ImageRecycler;
import com.example.wastetowealth.api.CategoryApi;
import com.example.wastetowealth.api.LoginApi;
import com.example.wastetowealth.api.UserApi;
import com.example.wastetowealth.databinding.ActivityAddPostsBinding;
import com.example.wastetowealth.model.CategoryModel;
import com.example.wastetowealth.model.PostData;
import com.example.wastetowealth.retrofit.MySharedPreferences;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    TextInputEditText postName, category, brand, model, condition, minAmount, maxAmount;
    Button submit, toggle;
    boolean isAllFieldsChecked = false;
    Spinner categoryValue;
    private String allShop = "";
    public ArrayList<CategoryModel> categoryArrayList;
    List<Uri> selectedImages = new ArrayList<>(); // List to store selected images URIs
    String selectedCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        hideAndShow();
        dropDownCategory();
        recyclerView = findViewById(R.id.allImages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ImageRecycler(imagePaths);
        recyclerView.setAdapter(adapter);
        popOpen();
        toggleButton = findViewById(R.id.shop_all);
        postName = findViewById(R.id.post_name);
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
//        if(toggleButton.isChecked() && shops.length() == 0) {
//            shops.setError("This field is required");
//            return false;
//        }
//        if(category.length() == 0) {
//            category.setError("This field is required");
//            return false;
//        }
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
        SwitchMaterial switchMaterial = findViewById(R.id.shop_all);
//        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    allShop = "Yes";
//                } else {
//                    allShop = "No";
//
//                }
//            }
//        });

        String postNameValue = postName.getText().toString();
        String brandValue = brand.getText().toString();
        String modelValue = model.getText().toString();
        String conditionValue = condition.getText().toString();
        String minAmountValue = minAmount.getText().toString();
        String maxAmountValue = maxAmount.getText().toString();

        RequestBody postNameBody = RequestBody.create(MediaType.parse("text/plain"), postNameValue);
//        RequestBody allShopBody = RequestBody.create(MediaType.parse("text/plain"), allShop);
        RequestBody brandBody = RequestBody.create(MediaType.parse("text/plain"), brandValue);
        RequestBody modelBody = RequestBody.create(MediaType.parse("text/plain"), modelValue);
        RequestBody conditionBody = RequestBody.create(MediaType.parse("text/plain"), conditionValue);
        RequestBody minAmountBody = RequestBody.create(MediaType.parse("text/plain"), minAmountValue);
        RequestBody maxAmountBody = RequestBody.create(MediaType.parse("text/plain"), maxAmountValue);
        RequestBody categoriesBody = RequestBody.create(MediaType.parse("text/plain"), selectedCategory);

        // Prepare list of image parts
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

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        MySharedPreferences sharedPreferences = MySharedPreferences.getInstance(AddPosts.this);
        String email = sharedPreferences.getString("email", "Default");
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
        Call<Object> call = userApi.doPostData(
                postNameBody,
                emailBody,
                brandBody,
                modelBody,
                conditionBody,
                minAmountBody,
                maxAmountBody,
                categoriesBody,
                imageParts
        );
        System.out.println(call);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    Intent intent = new Intent(AddPosts.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(AddPosts.this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle failure
                    Toast.makeText(AddPosts.this, "Failed to submit form", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                // Handle failure
                Log.e("Retrofit", "Request failed: " + t.getMessage(), t);
                Toast.makeText(AddPosts.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void handleSubmit() {
//        // Create a PostData object with form data
//        PostData postData = new PostData();
//        List<String> shopId = new ArrayList<>();
//        shopId.add(shops.getText().toString());
//        postData.setAllShop("Yes");
//        postData.setShopId(shopId);
//
//        postData.setCategories(selectedCategory);
//        postData.setBrand(brand.getText().toString());
//        postData.setModel(model.getText().toString());
//        postData.setCondition(condition.getText().toString());
//        postData.setMinAmount(minAmount.getText().toString());
//        postData.setMaxAmount(maxAmount.getText().toString());
//        postData.setImages(imagePaths);
//
//        System.out.println("post data" + postData);
//
//        RetrofitService retrofitService = new RetrofitService();
//        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
//
//        Call<PostData> callApi = userApi.doPostData(postData);
//
//        System.out.println("Call Api" + callApi);
//        callApi.enqueue(new Callback<PostData>() {
//            @Override
//            public void onResponse(Call<PostData> call, Response<PostData> response) {
//                if (response.isSuccessful()) {
//                    System.out.println(response.body().toString());
//                    Toast.makeText(AddPosts.this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(AddPosts.this, "Failed to submit form", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PostData> call, Throwable t) {
//                Toast.makeText(AddPosts.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
//            }
//        });;
//    }

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
                        if (item.getItemId() == R.id.menu_gallery) {
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
                if (data != null && data.getData() != null) {
                    Uri imageUri = data.getData();
                    selectedImages.add(imageUri);
                } else {
                    Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_IMAGE_GALLERY) {
                if (data != null) {
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
        }
    }

    private void updateAdapter() {
        adapter.setImagePaths(imagePaths);
        adapter.notifyDataSetChanged();
    }

    public static Bitmap uriToBitmap(Context context, Uri uri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    private void hideAndShow() {
        SwitchMaterial switchMaterial = findViewById(R.id.shop_all);
        TextInputLayout shopLayout = findViewById(R.id.shop);
        TextInputEditText shopText = findViewById(R.id.post_name);
//        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (!isChecked) {
//                    shopLayout.setVisibility(View.VISIBLE);
//                    shopText.setVisibility(View.VISIBLE);
//                } else {
//                    shopLayout.setVisibility(View.GONE);
//                    shopText.setVisibility(View.GONE);
//                }
//            }
//        });
    }

    private void dropDownCategory(){

        categoryValue = findViewById(R.id.category_text);

        RetrofitService retrofitService = new RetrofitService();
        CategoryApi categoryApi = retrofitService.getRetrofit().create(CategoryApi.class);

        categoryArrayList = new ArrayList<>();
        categoryApi.listCategory()
                .enqueue(new Callback<List<CategoryModel>>() {
                    @Override
                    public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                        if (response.isSuccessful()) {
                            categoryArrayList.addAll(response.body());
                            ArrayAdapter<CategoryModel> adapter = new ArrayAdapter(AddPosts.this,
                                    android.R.layout.simple_spinner_item,
                                    categoryArrayList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            categoryValue.setAdapter(adapter);
                        }else{
                            Toast.makeText(AddPosts.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                        Toast.makeText(AddPosts.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        categoryValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoryArrayList.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}