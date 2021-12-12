package com.example.smartmarker;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextView Login, Signin;
    EditText Login_id,Login_pw;
    String string_id, string_pw;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference db = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login_id=(EditText)findViewById(R.id.Login_id);
        Login_pw=(EditText)findViewById(R.id.Login_pw);
        Login=(TextView) findViewById(R.id.Login);
        Signin=(TextView) findViewById(R.id.Signin);

        Login_pw.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){

                    Login.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    public void sign_Onclick(View v) {
        Intent intent=new Intent(this, Signin.class);
        startActivity(intent);
    }

    public void Login_main(View v) {
        string_id=Login_id.getText().toString();
        string_pw=Login_pw.getText().toString();


        Intent intent=new Intent(this, MainActivity.class);

        db.child("Users").child(string_id).addValueEventListener(new ValueEventListener() { //addValue함수는 데이터베이스에 접근은 하는데 서버에 변화가 있으면 ValueEventListener 실행.
            @Override
            //정상적으로 실행될 때.
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    User user = snapshot.getValue(User.class);

                    if (user.getPw().equals(string_pw)) {
                        PreferenceManager.setUserID(Login.this, string_id);

                        startActivity(intent);
                    }
                    else {
                        sendmsg("비밀번호가 틀렸습니다.");
                    }
                }
                catch (NullPointerException e){
                    sendmsg("잘못된 아이디입니다.");
                }
            }
            @Override
            //오류 생길때.
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void sendmsg(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}