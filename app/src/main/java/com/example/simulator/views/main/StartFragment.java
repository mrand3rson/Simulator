package com.example.simulator.views.main;

import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.simulator.R;
import com.example.simulator.tools.TrainingTypes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartFragment extends Fragment {

    public StartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.subfragment_start, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.button_start)
    public void swapLayout() {
        String startDateTime = this.format(Calendar.getInstance().getTime());
        Bundle bundle = new Bundle();
        bundle.putString("start_date_time", startDateTime);
        bundle.putLong("start_time", System.currentTimeMillis());
        bundle.putInt("training_type", TrainingTypes.TYPE1);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, TrainingFragment.newInstance(bundle));
        ft.commit();

        Toast.makeText(getActivity(),
                String.format(Locale.getDefault(),
                "Current intensity: %d ms",
                        TrainingTypes.getTrainingIntensity(TrainingTypes.TYPE1)),
                Toast.LENGTH_SHORT).show();
    }

    private String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }
}
