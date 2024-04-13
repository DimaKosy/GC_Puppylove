package com.example.poppylove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class ImageController extends AppCompatActivity {
    private static FirebaseStorage storage;
    private Context context;
    private static ActivityResultLauncher<Intent> resultLauncher;
    private static String filedir = "";
    private static Bitmap bitmap;

    public ImageController(Context context){
        this.context = context;
        storage = FirebaseStorage.getInstance();

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        try {
                            Uri imageUri = o.getData().getData();
                            uploadImg(filedir,imageUri);
                        } catch (Exception e) {
                            Toast.makeText(context, "Failed to register activity", Toast.LENGTH_SHORT);
                        }
                    }
                }
        );
    }

    public Bitmap downloadUserImage(String ID){
        filedir = "images/"+ID+"/profile";

        return downloadImage(filedir);
    }

    public Bitmap downloadDogImage(String ID, String index){
        filedir = "images/"+ID+"/Dog_"+index;

        return downloadImage(filedir);
    }

    private Bitmap downloadImage(String filename){

        StorageReference storageReference = storage.getReference(filename);

        try {
            File localFile = File.createTempFile("tempfile",".png");
            storageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        }
                    }) .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            bitmap = null;
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bitmap;
    }

    public void uploadProfileImage(String ID, Uri imageUri){
        filedir = "images/"+ID+"/profile";

        uploadImg(filedir,imageUri);
    }

    public void uploadDogImage(String ID,int index , Uri imageUri){
        filedir = "images/"+ID+"/Dog_"+index;

        uploadImg(filedir,imageUri);
    }

    private void uploadImg(String filename, Uri imageUri){
        StorageReference storageReference = storage.getReference(filename);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(context, "File Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
