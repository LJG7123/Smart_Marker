package com.example.smartmarker;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartmarker.repositories.RepositoryAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyPageFragment extends Fragment {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference db = database.getReference();

    private RepositoryAccount repositoryAccount = RepositoryAccount.getInstance();
    TextView Mypage_id;
    TextView Mypage_name;
    TextView Mypage_phone;
    TextView Mypage_home;
    TextView Mypage_time;
    TextView Mypage_range;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        Mypage_id=(TextView)view.findViewById(R.id.Mypage_id);
        Mypage_name=(TextView)view.findViewById(R.id.Mypage_name);
        Mypage_phone=(TextView)view.findViewById(R.id.Mypage_phone);
        Mypage_home=(TextView)view.findViewById(R.id.Mypage_home);
        Mypage_time=(TextView)view.findViewById(R.id.Mypage_time);
        Mypage_range=(TextView)view.findViewById(R.id.Mypage_range);

        db.child("Users").child(repositoryAccount.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String strId=user.getId();
                Mypage_id.setText(strId);
                String strName=user.getName();
                Mypage_name.setText(strName);
                String strPhone=user.getPhone();
                Mypage_phone.setText(strPhone);
                repositoryAccount.setPhone(strPhone);
                String strHome=user.getHome();
                Mypage_home.setText(strHome);
                Mypage_time.setText(repositoryAccount.getTime());
                Mypage_home.setText(repositoryAccount.getLocation());
                Mypage_range.setText(repositoryAccount.getRange());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }
}