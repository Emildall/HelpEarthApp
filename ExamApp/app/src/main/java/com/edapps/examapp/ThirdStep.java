package com.edapps.examapp;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.Manifest;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static android.R.attr.data;


public class ThirdStep extends AppCompatActivity {
    ArrayList<String> array = null;
    ArrayList<BarEntry> barEntries;
    Button btnScreenshot, btnBack;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_step);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        btnScreenshot = (Button) findViewById(R.id.btnScreenshot);
        btnBack = (Button) findViewById(R.id.btnBack);

        Intent i = getIntent();
        array = i.getExtras().getStringArrayList("key");


        generateBarChart();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        btnScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout);

                layout.post(new Runnable() {
                    @Override
                    public void run() {
                        // takes screenshot
                        Bitmap pic = takeScreenShot(layout);
                        try {
                            if (pic != null) {
                                //saves screenshot
                                saveScreenShot(pic);
                                Toast.makeText(getApplicationContext(), "Screenshot saved", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void generateBarChart() {
        int shower = Integer.parseInt(array.get(0));
        int tshower = Integer.parseInt(array.get(1));
        int ml = Integer.parseInt(array.get(2));

        int calShower = shower * tshower;
        int calMeat = ml * 7;


        final BarChart barChart = (BarChart) findViewById(R.id.barchart);
        barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(calShower, 0));
        barEntries.add(new BarEntry(calMeat, 1));


        BarDataSet barDataSet = new BarDataSet(barEntries, "Emission");


        ArrayList<String> emissions = new ArrayList<>();
        emissions.add("Water Emission");
        emissions.add("Meat Emission");

        BarData data = new BarData(emissions, barDataSet);
        barChart.setData(data);
    }

    private Bitmap takeScreenShot(View v) {
        Bitmap screenShot = null;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            return null;
        }
        try {
            //get width and height
            int width = v.getMeasuredWidth();
            int height = v.getMeasuredHeight();
            screenShot = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            // Draw to canvas
            Canvas c = new Canvas(screenShot);
            v.draw(c);
        } catch (Exception e) {
            Log.d("ScreenShotActivity", "Failed to capture screenshot because:" + e.getMessage());
        }
        return screenShot;
    }

    private void saveScreenShot(Bitmap bm) {
        ByteArrayOutputStream bao = null;
        File file = null;
        try {
            bao = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 40, bao);

            file = new File(Environment.getExternalStorageDirectory()+ file.separator+ "chart.png");
            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bao.toByteArray());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


