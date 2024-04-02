package com.example.wastetowealth.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wastetowealth.DashboardActivity;
import com.example.wastetowealth.MainActivity;
import com.example.wastetowealth.R;
import com.example.wastetowealth.adapter.Category;
import com.example.wastetowealth.adapter.UserPostCardRecyclerAdapter;
import com.example.wastetowealth.api.LoginApi;
import com.example.wastetowealth.api.MasterApis;
import com.example.wastetowealth.model.CategoryModel;
import com.example.wastetowealth.model.LoginModel;
import com.example.wastetowealth.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPage extends AppCompatActivity {

    private RecyclerView categoryRV;
    private Category categoryAdapter;
    private List<CategoryModel> categoryList;

    String getCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        initSet();

        FloatingActionButton button = findViewById(R.id.floating_action_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create and show the dialog
                showDialog();
            }
        });


    }

    private void showDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.input_category, null);

        // Find the TextInputEditText in the custom layout
        final Spinner editTextCategory = dialogView.findViewById(R.id.editTextCategory);

        List<String> categoryList = new ArrayList<>();
        categoryList.add("mobile");
        categoryList.add("plastics");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CategoryPage.this,
                android.R.layout.simple_spinner_item,
                categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTextCategory.setAdapter(adapter);

        editTextCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getCategory = categoryList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Category")
                .setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle positive button click
//                        String category = editTextCategory.getText().toString().trim();
                        String category = getCategory;

                        if (!category.isEmpty()) {
                            addCategory(category);
                        } else {
                            Toast.makeText(CategoryPage.this, "Please enter a category", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle negative button click
                        Toast.makeText(CategoryPage.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void addCategory(String category) {
        RetrofitService retrofitService = new RetrofitService();
        MasterApis add = retrofitService.getRetrofit().create(MasterApis.class);
        CategoryModel addCategory = new CategoryModel();
        addCategory.setCategory(category);
        add.addCategory(addCategory).enqueue(new Callback<CategoryModel>() {
                    @Override
                    public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {

                        if (response.isSuccessful()) {
                            fetchCategories();
                            Toast.makeText(CategoryPage.this, "Category Added Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CategoryPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<CategoryModel> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(CategoryPage.this, "Category Adding failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initSet() {
        categoryRV = findViewById(R.id.categoryCards);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        categoryRV.setLayoutManager(layoutManager);
        categoryList = new ArrayList<>();

        categoryAdapter = new Category(categoryList);
        categoryRV.setAdapter(categoryAdapter);
        fetchCategories();
    }

    private void fetchCategories() {
        RetrofitService retrofitService = new RetrofitService();
        MasterApis apiService = retrofitService.getRetrofit().create(MasterApis.class);
        apiService.getCategory().enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.isSuccessful()) {
                    categoryList.clear();
                    categoryList.addAll(response.body());
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(CategoryPage.this, "Failed to fetch categories", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CategoryPage.this, "Failed to fetch categories", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showDatePicker() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog and set its listener
        DatePickerDialog datePickerDialog = new DatePickerDialog(CategoryPage.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                // Handle date selection here
                String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;
                Toast.makeText(CategoryPage.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        }, year, month, dayOfMonth);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
}