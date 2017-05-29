package com.edapps.examapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class Start extends AppCompatActivity {

    Button btn_next;
    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        firebaseauth = FirebaseAuth.getInstance();

        if (firebaseauth.getCurrentUser()== null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        btn_next = (Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn_next){
                    startActivity(new Intent(getApplicationContext(), SecondStep.class));
                }
            }
        });

    }
}
