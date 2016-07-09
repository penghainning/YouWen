package com.example.phn.youwenapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by PHN on 2016/7/4.
 */
public class jokeFragment extends Fragment {


    public jokeFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.joke_fragment, container, false);
        return view;
    }
}
