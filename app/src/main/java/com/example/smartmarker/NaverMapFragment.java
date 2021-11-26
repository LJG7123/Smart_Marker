package com.example.smartmarker;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

public class NaverMapFragment extends Fragment implements OnMapReadyCallback{

    private NaverMap naverMap;
    private MapFragment mapFragment;
    private Marker houseMarker;
    GPSTracker gps;
    private double latitude;
    private double longitude;
    Context context;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private FloatingActionButton fab;
    private boolean editable = false;
    private boolean fabMain_status = false;
    //
    //
    private void getpermisson() {

        // 메니패스트에 권한이 있는지 확인
        int permiCheck_loca = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        //앱권한이 없으면 권한 요청
        if (permiCheck_loca == PackageManager.PERMISSION_DENIED) {
            Log.d("권한 없는 상태", "");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            //
            Log.d("권한 있는 상태", "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        context = getActivity();
        getpermisson();
        gps=new GPSTracker(context);
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d("latitude", latitude + "");
            Log.d("longitude", longitude + "");

        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                toggleFab();
            }
        });

//
//
        return view;
    }

    public void toggleFab() {
        if(fabMain_status){
            ObjectAnimator.ofFloat(fab, View.ROTATION, 45f, 0f).start();
        }
        else {
            ObjectAnimator.ofFloat(fab, View.ROTATION, 0f, 45f).start();
        }
        fabMain_status = !fabMain_status;
        editable = !editable;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        this.naverMap.setLocationSource(locationSource);
        houseMarker = new Marker();
        Log.d("latitude", Double.toString(latitude));

        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        CameraPosition cameraPosition=new CameraPosition(new com.naver.maps.geometry.LatLng(latitude,longitude),15);
        naverMap.setCameraPosition(cameraPosition);

        this.naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                if(editable) {
                    houseMarker.setPosition(latLng);
                    houseMarker.setMap(naverMap);
                }
            }
        });


    }
}