package com.nunu.NUNU;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Calendar extends Fragment {


    public Calendar() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Calendar newInstance(String param1, String param2) {
        Calendar fragment = new Calendar();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
}