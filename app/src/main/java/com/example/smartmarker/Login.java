package com.example.smartmarker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmarker.repositories.RepositoryAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    TextView Login, Signin;
    EditText Login_id,Login_pw;
    String string_id, string_pw;
    CheckBox autologin;
    SharedPreferences auto;
    SharedPreferences.Editor autoLoginEdit;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference db = database.getReference();

    private RepositoryAccount repositoryAccount = RepositoryAccount.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        autoLoginEdit = auto.edit();
        Login_id=(EditText)findViewById(R.id.Login_id);
        Login_pw=(EditText)findViewById(R.id.Login_pw);
        Login=(TextView) findViewById(R.id.Login);
        Signin=(TextView) findViewById(R.id.Signin);
        autologin=(CheckBox)findViewById(R.id.auto_login);

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

        String userId = auto.getString("userId", null);
        String password = auto.getString("password", null);

        if(userId != null & password != null){
            autologin.setChecked(true);
            Login_id.setText(userId);
            Login_pw.setText(password);
            Login.performClick();
        }
    }

    public void sign_Onclick(View v) {
        Intent intent=new Intent(this, Signin.class);
        startActivity(intent);
    }

    public void Login_main(View v) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("로그인중..");
        progressDialog.show();

        string_id=Login_id.getText().toString();
        string_pw=Login_pw.getText().toString();

        if(autologin.isChecked()){
            autoLoginEdit.putString("userId", string_id);
            autoLoginEdit.putString("password", string_pw);
            autoLoginEdit.apply();
        }

        Intent intent=new Intent(this, MainActivity.class);

        db.child("Users").child(string_id).addValueEventListener(new ValueEventListener() { //addValue함수는 데이터베이스에 접근은 하는데 서버에 변화가 있으면 ValueEventListener 실행.
            @Override
            //정상적으로 실행될 때.
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    User user = snapshot.getValue(User.class);
                    progressDialog.dismiss();

                    if (user.getPw().equals(string_pw)) {
                        PreferenceManager.setUserID(Login.this, string_id);
                        repositoryAccount.setId(string_id);
                        db.child("Users").child(repositoryAccount.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String care_id;
                                User user1 = snapshot.getValue(User.class);
                                care_id=user1.getCare_id();

                                db.child("Users").child(care_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        User user2 = snapshot.getValue(User.class);
                                        String token = user2.getToken();
                                        Runnable runnable=new Runnable() {
                                            @Override
                                            public void run() {

                                                try {

                                                    //댓글 시 알림창 저장

                                                    APIService apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
                                                    apiService.sendNotification(new NotificationData(new SendData("경고", "설정 반경을 벗어났습니다."), token))
                                                            .enqueue(new Callback<MyResponse>() {
                                                                @Override
                                                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                                                    if (response.code() == 200) {
                                                                        if (response.body().success == 1) {
                                                                            Log.e("Notification", "success");
                                                                        }
                                                                    }

                                                                }

                                                                @Override
                                                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                                                }
                                                            });


                                                } catch (NullPointerException e) {

                                                }

                                            }
                                        };
                                        Thread tr = new Thread(runnable);
                                        tr.start();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
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