package com.example.phn.youwenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by PHN on 2016/7/4.
 */
public class carbreakFragment extends Fragment {

    Button carbreaksure;

    public carbreakFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carbreak_fragment, container, false);
        carbreaksure=(Button)view.findViewById(R.id.carbreaksure);
        carbreaksure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),carbreakActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
