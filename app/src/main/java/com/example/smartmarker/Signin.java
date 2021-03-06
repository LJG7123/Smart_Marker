package com.example.smartmarker;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmarker.repositories.RepositoryAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class Signin extends AppCompatActivity {
    EditText Signin_id, Signin_pw, Signin_name, Signin_phone, Signin_home, Signin_careid;
    TextView Login_back;
    String string_id, string_pw, string_name, string_phone, string_home, string_careid;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference db = database.getReference();

    private RepositoryAccount repositoryAccount = RepositoryAccount.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Signin_id = (EditText) findViewById(R.id.Signin_id);
        Signin_pw = (EditText) findViewById(R.id.Signin_pw);
        Signin_name = (EditText) findViewById(R.id.Signin_name);
        Signin_phone = (EditText) findViewById(R.id.Signin_phone);
        Signin_home=(EditText) findViewById(R.id.Signin_home);
        Signin_careid=(EditText) findViewById(R.id.Signin_careid);
        Login_back = (TextView) findViewById(R.id.Login_back);

    }

    public void signin(View view) {

        string_id = Signin_id.getText().toString();
        string_pw = Signin_pw.getText().toString();
        string_name = Signin_name.getText().toString();
        string_phone = Signin_phone.getText().toString();
        string_home = Signin_home.getText().toString();
        string_careid = Signin_careid.getText().toString();


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Signin.this, "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        String token = task.getResult();

                        User newuser = new User(string_id, string_pw, string_name, string_phone, string_home, string_careid, token);

                        repositoryAccount.setLocation(string_home);
                        repositoryAccount.setCare_id(string_careid);


                        db.child("Users").child(string_id).setValue(newuser); //id??? ??????. setValue->?????? ??????.


                        Intent intent = new Intent(Signin.this, Login.class);
                        startActivity(intent);
                    }
                });
    }
}
