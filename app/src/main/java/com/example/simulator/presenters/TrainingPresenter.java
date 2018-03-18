package com.example.simulator.presenters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.simulator.tools.TrainingTypes;

import java.io.IOException;


public class TrainingPresenter implements ITraining {

    private final int VIBRATION_DURATION = 200;
    private final Context context;
    private boolean landscape;
    private float prevRoll;
    private long startIterationTime;
    private int count, warningsCount;

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

                if (prevRoll == 0) {
                    prevRoll = Math.signum(roll);
                    startIterationTime = System.currentTimeMillis();
                    vibrationResponse(VIBRATION_DURATION/4);
                }
                else {
                    if (Math.signum(roll) != 0
                            && Math.signum(roll) != prevRoll) {
                        prevRoll = Math.signum(roll);
                        startIterationTime = System.currentTimeMillis();
                        count++;
                        vibrationResponse(VIBRATION_DURATION);
                        if (checkIfCompleted1())
                            return EXERCISE_COMPLETED;
                    }
                }
            }
        } else {
            landscape = false;
        }
        if (lowIntensity(TrainingTypes.TYPE1))
            return EXERCISE_FAILED;

        return count;
    }

    private boolean lowIntensity(int type) {
        if (System.currentTimeMillis() - startIterationTime > TrainingTypes.getTrainingIntensity(type)
                && startIterationTime != 0) {

            warningsCount++;
            if (warningsCount == 3) {
                return true;
            }
            warnUser();
            startIterationTime = System.currentTimeMillis();
        }
        return false;
    }

    private void warnUser() {
        Toast.makeText(context, "Faster!", Toast.LENGTH_SHORT).show();

        Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, defaultRingtoneUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    mp.release();
                }
            });
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void vibrationResponse(int duration) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(duration);
            }
        }
    }

    private boolean checkIfCompleted1() {
        return count == 5;
    }
}
