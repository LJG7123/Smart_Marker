package com.example.smartmarker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomesaveActivity extends AppCompatActivity implements View.OnClickListener{
    GPSTracker gps;
    SharedPreferences homeAddress;
    SharedPreferences.Editor homeAddressEdit;
    private Button btn_gpssave;
    private double Homelatitude;
    private double Homelongitude;
    private void getpermisson() {

        // 메니패스트에 권한이 있는지 확인
        int permiCheck_loca = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        //앱권한이 없으면 권한 요청
        if (permiCheck_loca == PackageManager.PERMISSION_DENIED) {
            Log.d("권한 없는 상태", "");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            //
            Log.d("권한 있는 상태", "");
        }
    }

    private void init(){
        btn_gpssave=(Button)findViewById(R.id.btn_gpssave);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homegps);
        getpermisson();
        init();

        homeAddress = getSharedPreferences("homeAddress", Activity.MODE_PRIVATE);
        homeAddressEdit = homeAddress.edit();

        btn_gpssave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                gps = new GPSTracker(HomesaveActivity.this);
                if (gps.canGetLocation()) {

                    Homelatitude = gps.getLatitude();
                    Homelongitude = gps.getLongitude();
                    //Log.d("Homelatitude", Homelatitude + "");
                    //Log.d("Homelongitude", Homelongitude + "");
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    List<Address> address = null;
                    try{
                        address = geocoder.getFromLocation(Homelatitude, Homelongitude, 2);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    String str = "";
                    if(address.size()!=0){
                        str = address.get(1).getAddressLine(0);
                        str = str.substring(str.indexOf(" ")+1);
                        Toast.makeText(HomesaveActivity.this, str, Toast.LENGTH_SHORT).show();
                        homeAddressEdit.putString("address", str);
                        homeAddressEdit.apply();
                    }
                    else{
                        str = "주소를 찾을 수 없습니다.";
                    }

                    finish();

                } else {
                    // Can't get location.
                    // GPS or network is not enabled.
                    // Ask user to enable GPS/network in settings.
                    gps.showSettingsAlert();
                }
            }
        });
    }
    @Override
    public void onClick(View view) {

    }
}
