package com.example.simulator.views.main;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.simulator.R;
import com.example.simulator.views.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TransferingFragment extends Fragment {

    private Bundle bundle;

    @BindView(R.id.description_write)
    EditText description;

    public TransferingFragment() {

    }

    public static TransferingFragment newInstance(Bundle args) {
        TransferingFragment fragment = new TransferingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = getArguments();
        View v = inflater.inflate(R.layout.subfragment_transfering, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.button)
    public void goToStatistics() {
        MainActivity mainActivity = (MainActivity) getActivity();
        bundle.putString("description", description.getText().toString());

        mainActivity.showStatistics(bundle);
    }
}
