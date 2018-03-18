package com.example.simulator.views.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simulator.R;


public class MainFragment extends Fragment {

    public MainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        getChildFragmentManager().beginTransaction()
                .add(R.id.container, new StartFragment())
                .commit();

        return v;
    }
}
