package com.nunu.NUNU;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.google.android.material.tabs.TabLayout;

public class EyeTest extends Fragment {
    TabLayout tabs;
    Fragment sightTest;
    Fragment whoTest;
    Fragment letterTest;
    Fragment selected = null;

    public EyeTest() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_eye_test, container, false);
        sightTest = new sightTest();
        whoTest = new whoTest();
        letterTest = new letterTest();

        tabs = mView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("숫자맞추기"));
        tabs.addTab(tabs.newTab().setText("누구인가?"));
        tabs.addTab(tabs.newTab().setText("글씨맞추기"));

        getFragmentManager().beginTransaction().replace(R.id.contatiner, sightTest).commit();

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    selected = sightTest;
                } else if (position == 1) {
                    selected = whoTest;
                } else if (position == 2) {
                    selected = letterTest;
                }


                getFragmentManager().beginTransaction().replace(R.id.contatiner, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return mView;
    }
}