package com.example.simulator.presenters;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;


public class TrainingPresenter implements ITraining {

    private final int VIBRATION_DURATION = 200;
    private final Context context;
    private boolean landscape;
    private float prevRoll;
    private int count;

    public TrainingPresenter(Context context) {
        this.context = context;
    }

    @Override
    public int checkExercise1(float[] orientationAngles) {
        float pitch = orientationAngles[1];
        float roll = orientationAngles[2];

        if (pitch > 0) {
            if (!landscape) {
                landscape = true;

                if (prevRoll == 0)
                    prevRoll = Math.signum(roll);
                else {
                    if (Math.signum(roll) != 0
                            && Math.signum(roll) != prevRoll) {
                        prevRoll = Math.signum(roll);
                        count++;
                        vibrationResponse();
                        if (checkIfCompleted1())
                            return PART_COMPLETED;
                        return count;
                    }
                }
            }
        } else {
            landscape = false;
        }
        return PART_NOT_COMPLETED;
    }

    private void vibrationResponse() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(VIBRATION_DURATION);
            }
        }
    }

    private boolean checkIfCompleted1() {
        return count == 5;
    }
}
