package com.example.smartmarker;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class Signin extends AppCompatActivity {
    EditText Signin_id, Signin_pw, Signin_pwcheck, Signin_name;
    Button Signin_btn_signin;
    String string_id, string_pw, string_name;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference db = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Signin_id = (EditText) findViewById(R.id.Signin_id);
        Signin_pw = (EditText) findViewById(R.id.Signin_pw);
        Signin_name = (EditText) findViewById(R.id.Signin_name);

        Signin_btn_signin = (Button) findViewById(R.id.Signin_btn_signin);


    }

    public void signin(View view) {

        string_id = Signin_id.getText().toString();
        string_pw = Signin_pw.getText().toString();
        string_name = Signin_name.getText().toString();


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Signin.this, "토큰 생성 실패", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String token = task.getResult();

                        User newuser = new User(string_id, string_pw, string_name, token);


                        db.child("Users").child(string_id).setValue(newuser); //id로 구분. setValue->정보 입력.


                        Intent intent = new Intent(Signin.this, Login.class);
                        startActivity(intent);
                    }
                });
    }
}