package com.example.smartmarker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button onButtonClickedAlarming = (Button) view.findViewById(R.id.alarming);
        onButtonClickedAlarming.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        Button b = (Button) v;

        switch(b.getId())
        {
            case R.id.alarming:
                getActivity().startActivity(new Intent(getActivity(), AlarmActivity.class));
                break;
        }

    }
}