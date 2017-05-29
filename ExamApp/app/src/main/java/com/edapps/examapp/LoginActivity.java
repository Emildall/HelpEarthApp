package com.edapps.examapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_login;
    TextView tv_signup;
    EditText et_email, et_password;

    ProgressDialog progressdialog;

    private FirebaseAuth firebaseauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseauth = FirebaseAuth.getInstance();

        if (firebaseauth.getCurrentUser()!= null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressdialog = new ProgressDialog(this);

       btn_login = (Button) findViewById(R.id.btn_login);
        tv_signup = (TextView) findViewById(R.id.tv_signup);
        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);

        btn_login.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
    }

    private void userLogin()
    {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter valid password", Toast.LENGTH_SHORT).show();
            return;
        }


        progressdialog.setMessage("Login, please wait..");
        progressdialog.show();

        firebaseauth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialog.dismiss();

                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if (view == btn_login)
        {
            userLogin();
        }

        if (view == tv_signup)
        {   finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
