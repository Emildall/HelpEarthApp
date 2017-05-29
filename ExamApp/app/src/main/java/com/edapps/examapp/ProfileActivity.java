package com.edapps.examapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private TextView tv_welcome;
    private ImageView iv_chart;
    private Button btn_logout, btn_start, btn_Info;

    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        iv_chart = (ImageView) findViewById(R.id.iv_chart);
        tv_welcome = (TextView) findViewById(R.id.tv_welcome);
        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_Info = (Button)findViewById(R.id.btn_Info);
        btn_start = (Button)findViewById(R.id.btn_start);



        tv_welcome.setText("Welcome : " + user.getEmail());

        btn_start.setOnClickListener(this);
        btn_Info.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        getImage();
    }

    @Override
    public void onClick(View v) {
    if (v == btn_logout){
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
        if (v == btn_start)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), Start.class));
        }
        if (v == btn_Info){
            finish();
            startActivity(new Intent(getApplicationContext(), Info.class));
        }
    }

    private void getImage() {

       try{
           File sdCard = Environment.getExternalStorageDirectory();

           File directory = new File(sdCard.getAbsolutePath());

           File file = new File(directory, "chart.png");

           FileInputStream streamIn = new FileInputStream(file);

           Bitmap bitmap = BitmapFactory.decodeStream(streamIn);
           streamIn.close();

           iv_chart.setImageBitmap(bitmap);

       }catch (Exception e){
           Log.d("ProfileActivity", "Failed to display image:" + e.getMessage());
       }

    }
}
