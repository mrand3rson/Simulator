package com.example.simulator.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;

import com.example.simulator.R;

import butterknife.ButterKnife;

public class InitActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(previouslyStarted) {
            goToMain();
            return;
        }

        setContentView(R.layout.activity_init);
        ButterKnife.bind(this);

        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
        edit.apply();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToMain();
            }
        }, 5000);
    }

    protected void goToMain() {
        startActivity(new Intent(InitActivity.this, MainActivity.class));
        finish();
    }
}
