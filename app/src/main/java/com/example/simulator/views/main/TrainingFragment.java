package com.example.simulator.views.main;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simulator.R;
import com.example.simulator.presenters.ITraining;
import com.example.simulator.presenters.TrainingPresenter;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TrainingFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    private int trainingType;
    private long startTime;
    private String startDateTime;
    ITraining presenter;

    @BindView(R.id.tv)
    public TextView tv;

    @BindView(R.id.debug)
    public TextView debug;


    public TrainingFragment() {

    }

    public static TrainingFragment newInstance(Bundle bundle) {
        TrainingFragment fragment = new TrainingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new TrainingPresenter(getActivity());

        trainingType = getArguments().getInt("training_type", -1);
        startDateTime = getArguments().getString("start_date_time");
        startTime = getArguments().getLong("start_time");

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            Sensor sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(this, sensor2,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

        View v = inflater.inflate(R.layout.subfragment_stop, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.button_stop)
    public void stopTraining() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new StartFragment())
                .commit();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(sensorEvent.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
        }
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(sensorEvent.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }
        updateOrientationAngles();

        int counts = presenter.checkExercise1(orientationAngles);

        if (counts != TrainingPresenter.EXERCISE_FAILED) {
            if (counts != TrainingPresenter.EXERCISE_COMPLETED)
                tv.setText(String.valueOf(counts));
            else {
                Toast.makeText(getActivity(), "Training COMPLETED!", Toast.LENGTH_LONG).show();
                showStatistics();
            }
        } else {
            Toast.makeText(getActivity(), "You are too slow!", Toast.LENGTH_SHORT).show();
            stopTraining();
            sensorManager.unregisterListener(this);
        }
    }

    private void showStatistics() {
        Bundle bundle = new Bundle();
        bundle.putInt("training_type", trainingType);
        bundle.putString("duration", durationFormat(System.currentTimeMillis() - startTime));
        bundle.putString("date", startDateTime);

        getFragmentManager().beginTransaction()
                .replace(R.id.container, TransferingFragment.newInstance(bundle))
                .commit();
    }

    private String durationFormat(Long duration) {
        long minutes = duration / TimeUnit.MINUTES.toMillis(1);
        long seconds = duration % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
        long millis = duration % TimeUnit.SECONDS.toMillis(1);
        return String.format(Locale.getDefault(), "%02d:%02d.%03d", minutes, seconds, millis);
    }

    public void updateOrientationAngles() {
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
