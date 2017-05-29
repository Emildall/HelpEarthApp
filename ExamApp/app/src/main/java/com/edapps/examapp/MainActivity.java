package com.edapps.examapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Console;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et_email, et_password;
    Button btn_register;
    TextView tv_signin;
    private ProgressDialog progressdialog;

    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseauth = FirebaseAuth.getInstance();

        if (firebaseauth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        btn_register = (Button) findViewById(R.id.btn_register);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);
        tv_signin = (TextView)findViewById(R.id.tv_signin);

        btn_register.setOnClickListener(this);
        tv_signin.setOnClickListener(this);
        progressdialog = new ProgressDialog(this);

    }

    private void registerUser()
    {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }



        progressdialog.setMessage("Registering User..");
        progressdialog.show();

        firebaseauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));


                    }else{
                        Toast.makeText(MainActivity.this, "Could not register, please try again", Toast.LENGTH_SHORT).show();
                    }
                    }
                });



    }

    @Override
    public void onClick(View view) {
        if (view == btn_register)
        {

            registerUser();
        }
        if (view == tv_signin)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
