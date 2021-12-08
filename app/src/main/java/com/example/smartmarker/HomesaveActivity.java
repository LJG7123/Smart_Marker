package com.example.smartmarker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

public class HomesaveActivity extends AppCompatActivity implements View.OnClickListener{
    GPSTracker gps;
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
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_gpssave) {
            gps = new GPSTracker(this);
            if (gps.canGetLocation()) {

                Homelatitude = gps.getLatitude();
                Homelongitude = gps.getLongitude();
                //Log.d("Homelatitude", Homelatitude + "");
                //Log.d("Homelongitude", Homelongitude + "");

            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
        }
    }
}
