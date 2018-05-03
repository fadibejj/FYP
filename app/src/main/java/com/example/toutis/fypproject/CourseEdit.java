package com.example.toutis.fypproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Alexander on 23-Feb-18.
 */

public class CourseEdit extends Fragment {
    String name;
    int grade,hours;
    double difficulty;

    EditText courseNameInput;
    EditText gradeInput;
    EditText hoursWillingToStudyInput;
    EditText difficultyInput;

    Button submitButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.course_edit, container, false);

        courseNameInput = (EditText) v.findViewById(R.id.courseName);
        gradeInput = (EditText) v.findViewById(R.id.targetGrade);
        hoursWillingToStudyInput = (EditText) v.findViewById(R.id.hoursWillingToStudy);
        difficultyInput = (EditText) v.findViewById(R.id.difficulty);
        submitButton = (Button) v.findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = courseNameInput.getText().toString();
                grade = Integer.valueOf(gradeInput.getText().toString());
                hours = Integer.valueOf(hoursWillingToStudyInput.getText().toString());
                difficulty = Double.valueOf(difficultyInput.getText().toString());
                Planner plan = new Planner();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.drawer_layout, plan).commit();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*EditText text = (EditText)getView().findViewById(R.id.plain_text_input);
        if(text.getText().toString() == "farhat"){
            EditText tou = (EditText) getView().findViewById(R.id.out);
            tou.setText("farhat ntek");
        }*/
        getActivity().setTitle("Course Edit");
    }
}
