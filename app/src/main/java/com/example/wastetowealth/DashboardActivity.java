package com.example.wastetowealth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wastetowealth.databinding.ActivityMainBinding;
import com.example.wastetowealth.databinding.DashboardActivityBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DashboardActivity extends AppCompatActivity  {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    DashboardActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DashboardActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new HomeFragment())
                    .commit();
        }
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.home) {
                // Handle Home item click
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.search) {
                // Handle Search item click
                selectedFragment = new SavedFragment();
            } else if (item.getItemId() == R.id.add) {
                // Handle Add item click
                dispatchTakePictureIntent();
            } else if (item.getItemId() == R.id.setting) {
                // Handle Settings item click
                selectedFragment = new SearchFragment();
            } else if (item.getItemId() == R.id.bottomProfile) {
                // Handle Profile item click
                selectedFragment = new ProfileFragment();
            }
            Log.d("DashboardActivity", "Selected item: " + selectedFragment);

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, selectedFragment)
                        .commit();
            }

            return true;
        });
    }

    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
        Intent intent = new Intent(this, AddPosts.class);
        startActivity(intent);

    }

    // Handle result of the camera intent (optional)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // The image has been captured, you can handle the result here
            // For example, you can get the image from the Intent data
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Save the image to a file
            File imageFile = saveImageToFile(imageBitmap);

            // Pass the file URI to ProfileActivity
            Intent intent = new Intent(this, AddPosts.class);
            intent.putExtra("photoUri", imageFile.toURI().toString());
            startActivity(intent);
            // Do something with the imageBitmap
        }
    }
    public static File saveImageToFile(Bitmap imageBitmap) {
        // Create a directory for your images if it doesn't exist
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "WasteToWealth");
        System.out.println("Directory" + directory);
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
}
