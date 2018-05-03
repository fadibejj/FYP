package com.example.toutis.fypproject;

import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Alexander on 23-Feb-18.
 */

public class Planner extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.planner, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String s = getActivity().getIntent().getStringExtra("nameOfCourse");
        TextView tv = (TextView) getView().findViewById(R.id.nameOfCourse);
        tv.setText(s);
        getActivity().setTitle("Planner");
    }
}
