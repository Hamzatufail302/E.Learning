package com.example.elearningfordeafanddumbkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Sixth extends AppCompatActivity {

    private DatabaseReference databaseReference;
    StorageReference reference;
    private List myVideos;
    private VideoView videoView;
    private ImageView imageView;
    //Uri iamgeuri;
    //Uri videouri;
    String [] img_names;
    int index=0;
    ProgressDialog progressDialog;
    String [] vid_names;
    ValueEventListener listener;
    ArrayList<String> myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);
        imageView= findViewById(R.id.my_img);
        myVideos=new ArrayList();
        databaseReference= FirebaseDatabase.getInstance().getReference("hamza12");
        videoView=(VideoView)findViewById(R.id.vid);
        progressDialog=new ProgressDialog(Sixth.this);
        img_names=getIntent().getStringArrayExtra("images");
        vid_names=getIntent().getStringArrayExtra("videos");
        myList=new ArrayList<>();
        RetrieveData();


    }
    public void imageload(int index){
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference =storage.getReference("images").child("fruits").child(img_names[index]+".png");
        try {
            final File localFile=File.createTempFile(img_names[index],"png");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                   imageView.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   Toast.makeText(Sixth.this,"Failed to load",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void multiple(int index){

        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Video/Image...");
        progressDialog.show();

        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
   //     reference=storageReference.child("/videos/Apple.mp4");
        Toast.makeText(this, myList.get(0), Toast.LENGTH_SHORT).show();
      //  reference=storageReference.child("Hamza12@gmail.com").child("Fruits/"+myList.get(index)+".mp4");
        reference=storageReference.child("/"+"hamza12@gmail.com"+"/Fruit/"+myList.get(index)+".mp4");
      //  storageReference.child("/"+Username+"/Fruit/"+name+".mp4");


        try {
            final File localFile=File.createTempFile(vid_names[index],"mp4");
            reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    videoView.setVideoURI(Uri.fromFile(localFile));
                    videoView.start();
                    progressDialog.dismiss();

                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setLooping(true);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Sixth.this, "Failed "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Next(View view) {
        index++;
        if(index>myList.size()-1){
            index=0;
        }
        multiple(index);
        imageload(index);
        Toast.makeText(this, myList.toString(), Toast.LENGTH_SHORT).show();
    }

    public void RetrieveData(){
        listener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item: dataSnapshot.getChildren()){
                        myList.add(item.getValue().toString());
                }
                imageload(index);
                multiple(index);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





}
