package com.example.elearningfordeafanddumbkids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Monitor extends AppCompatActivity {
    private static final int PICK_VIDEO_REQUEST=3;
    private VideoView vv;
    Uri ImagePath;
    private static int Pick_image = 123;
    private ImageView extraimage;
    Button Add_Image;
    public MediaController mediaController;
    private StorageReference storageReference;
    Uri videouri;
    String VideoName;
    DatabaseReference reference;
    ArrayList<String> videos_Name=new ArrayList<>();
    String[] vidNames={"0","0","0","0","0","0","0","0","0","0"};

//private  String videoname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        extraimage=findViewById(R.id.chooseimage);
        vv=findViewById(R.id.vidupload);
        storageReference = FirebaseStorage.getInstance().getReference();
        reference= FirebaseDatabase.getInstance().getReference();
        Toast.makeText(this, String.valueOf(reference), Toast.LENGTH_SHORT).show();

        Add_Image=findViewById(R.id.btn);
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mediaController=new MediaController(Monitor.this);
                        vv.setMediaController(mediaController);
                        mediaController.setAnchorView(vv);
                    }
                });
            }
        });

        vv.start();

        Add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Pick_image);
            }
        });
    }
    public void Upload(View view){

    AlertBox();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


//        switch (requestCode){
//            case 1:
//                    videouri = data.getData();
//                    vv.setVideoURI(videouri);
//                    Toast.makeText(this, "Video is set", Toast.LENGTH_SHORT).show();
//
//
//                break;
//            case 2:
//                ImagePath = data.getData();
//                break;
//        }
//

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data.getData() != null) {
            videouri = data.getData();
            vv.setVideoURI(videouri);
            Toast.makeText(this, "Video is set", Toast.LENGTH_SHORT).show();

        }
        if (requestCode == Pick_image && resultCode == RESULT_OK && data.getData() != null) {
            ImagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImagePath);
                extraimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


    public void Videoup(View view) {
        Intent i = new Intent();
        i.setType("video/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"select a video"),PICK_VIDEO_REQUEST);
    }



    public void uploadimage(View view){


    }

    private void sendUserData() {
        StorageReference imagereferance = storageReference.child("images").child("fruits").child("apple");
        UploadTask uploadTask = imagereferance.putFile(ImagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Monitor.this, "upload failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Monitor.this, "upload Success", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void AlertBox(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Monitor.this);
        alertDialog.setTitle("Name");
        alertDialog.setMessage("Enter Desired Video Name");

        final EditText input = new EditText(Monitor.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                      String  Name = input.getText().toString();
                      myupload(Name);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        alertDialog.show();



    }
    public void myupload(final String name){
        final String Username="hamza12@gmail.com";
        Sessions session=new Sessions(Monitor.this);
        //    Username=session.getUsername();
        sendUserData();
     //   String name= AlertBox();
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
        videos_Name.add(name);
        if(videouri!=null){
            //   StorageReference riversRef = storageReference.child("/test/"+VideoName+".mp4");
            StorageReference riversRef = storageReference.child("/"+Username+"/Fruit/"+name+".mp4");
            UploadTask uploadTask=riversRef.putFile(videouri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Monitor.this, "Upload Failed :"+e, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Monitor.this, "Upload Sucessfull", Toast.LENGTH_SHORT).show();
                    InsertVideoName(Username,name);
                }
            });
        }
        else{
            Toast.makeText(this,"Video is empty ",Toast.LENGTH_LONG).show();
        }
    }
    public void InsertVideoName(String Username,String VideoName){
        String s=StringtoArray(Username);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        reference.child(s).push().setValue(VideoName);
     //   Toast.makeText(this, "Value Added", Toast.LENGTH_SHORT).show();
    }
    public String StringtoArray(String str){
        char[] ch = new char[str.length()];
        List list=new ArrayList();
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }
        for (int i = 0; i < ch.length; i++) {
           if(ch[i]!='@'){
               list.add(ch[i]);
           }
           else{
               break;
           }
        }
        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < list.size() - 1) {
            sb.append(list.get(i));

            i++;
        }
        sb.append(list.get(i));

        return sb.toString();
    }
}
