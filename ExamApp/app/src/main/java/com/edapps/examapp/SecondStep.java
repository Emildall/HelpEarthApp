package com.edapps.examapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SecondStep extends AppCompatActivity implements View.OnClickListener{

    public FirebaseAuth firebaseAuth;
    EditText et_shower, et_timeshower, et_ml;
    ImageButton img_button, img_tsbtn, img_mlbtn;
    Button btn_cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_step);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        img_button = (ImageButton)findViewById(R.id.img_sbtn);
        img_tsbtn = (ImageButton)findViewById(R.id.img_tsbtn);
        img_mlbtn = (ImageButton)findViewById(R.id.img_mlbtn);
        et_shower = (EditText) findViewById(R.id.et_shower);
        et_ml = (EditText)findViewById(R.id.et_ml);
        btn_cal = (Button)findViewById(R.id.btn_cal);
        et_shower = (EditText)findViewById(R.id.et_shower);
        et_timeshower = (EditText)findViewById(R.id.et_timeshower);






        img_tsbtn.setOnClickListener(this);
        img_mlbtn.setOnClickListener(this);
        img_button.setOnClickListener(this);
        btn_cal.setOnClickListener(this);


        new AlertDialog.Builder(this)
                .setMessage("As a user you have to fill in the empty areas. If you dont know what to answer, you can tab the '?' icon and it will tell you what to write. When your done press 'calculate' ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface diaglog, int arg1) {
                        diaglog.dismiss();
                    }
                })
                .show();
    }




    @Override
    public void onClick(View v) {
        if (v == img_button) {
            new AlertDialog.Builder(this)
                    .setMessage("You have to type in a number that matches the average time you take pr. shower")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface diaglog, int arg1) {
                            diaglog.dismiss();
                        }
                    })
                    .show();

        }
        if (v == img_mlbtn) {
            new AlertDialog.Builder(this)
                    .setMessage("If you have meatless days, type in a number that matches the amount of days you dont eat meat")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface diaglog, int arg1) {
                            diaglog.dismiss();
                        }
                    })
                    .show();
        }
        if (v == btn_cal) {

            getVal();

        }
        if (v == img_tsbtn) {
            new AlertDialog.Builder(this)
                    .setMessage("You have to type in a number that matches the amount of showers you take pr. week")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface diaglog, int arg1) {
                            diaglog.dismiss();
                        }
                    })
                    .show();


        }
    }

    private void getVal(){

        if(et_shower.getText().toString().isEmpty()
                || et_timeshower.getText().toString().isEmpty() || et_ml.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Invalid values", Toast.LENGTH_SHORT).show();
            return;
        }
            

        String timeshower = et_timeshower.getText().toString();
        String shower = et_shower.getText().toString();
        String mldays = et_ml.getText().toString();




        ArrayList<String> arr = new ArrayList<>();
        arr.add(timeshower);
        arr.add(shower);
        arr.add(mldays);

        Intent i = new Intent(SecondStep.this, ThirdStep.class);
        i.putStringArrayListExtra("key", arr);
        startActivity(i);

        }
}
