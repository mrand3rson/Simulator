package com.example.simulator.views.main;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.simulator.R;
import com.example.simulator.tools.TrainingTypes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseTrainingDialog extends DialogFragment {

    @BindView(R.id.lv)
    ListView lv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Choose training");
        View v = inflater.inflate(R.layout.list_layout, container, false);
        ButterKnife.bind(this, v);
        lv.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                TrainingTypes.getAllTrainingNames()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String name = "type";
                Intent intent = new Intent();

                switch (i) {
                    case 0: {
                        intent.putExtra(name, TrainingTypes.TYPE1);
                        break;
                    }
                    case 1: {
                        intent.putExtra(name, TrainingTypes.TYPE2);
                        break;
                    }
                    case 2: {
                        intent.putExtra(name, TrainingTypes.TYPE3);
                        break;
                    }
                    default: {
                        break;
                    }
                }

                getTargetFragment().onActivityResult(1, 0, intent);
                dismiss();
            }
        });
        return v;
    }
}
