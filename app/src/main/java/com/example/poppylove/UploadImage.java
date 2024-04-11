package com.example.poppylove;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.poppylove.databinding.ActivityUploadImageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UploadImage extends AppCompatActivity {

    //Easier way to call the views than using findById()
    ActivityUploadImageBinding binding;
    //Needed for putting data into the firebase storage
    StorageReference storageReference;
    //Used for launching the image selector
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up binding
        binding = ActivityUploadImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        registerResult();

        //launches img selector when the select image button is pressed
        binding.selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImg();
            }
        });

        //pulls an image down from the firebase storage stores
        //it as a temporary file using the files location in storage
        binding.retriveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference = FirebaseStorage.getInstance().getReference("images/profile.png");
                try {
                    File localFile = File.createTempFile("tempfile", ".png");
                    storageReference.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    binding.selectedImg.setImageBitmap(bitmap);
                                }
                            }) .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadImage.this, "Failed to retrieve", Toast.LENGTH_SHORT);
                                }
                            });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    //used to launch the pick image selector
    private void selectImg() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    //pass an image uri it will be given an automatic heading using the current
    //date and time and attempt to upload the selected image
    //gives a toast when successful
    private void uploadImage(Uri imageUri) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CHINA);
        Date now = new Date();
        String fileName = formatter.format(now);

        storageReference = FirebaseStorage.getInstance().getReference("image/"+fileName);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UploadImage.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadImage.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //Once an image is selected this function calls the uploadImage to send
    //it to the firebase storage
    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                            try {
                                Uri imageUri = result.getData().getData();
                                uploadImage(imageUri);
                            } catch (Exception e) {
                                Toast.makeText(UploadImage.this, "Woah buddy that ain't working", Toast.LENGTH_SHORT);
                            }
                    }
                }
        );
    }
}