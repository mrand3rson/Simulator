package com.example.simulator.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.simulator.R;
import com.example.simulator.views.main.MainFragment;
import com.example.simulator.views.statistics.ViewStatisticsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        getFragmentManager().beginTransaction()
                .add(R.id.container, new MainFragment())
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_trainings) {
            showTrainings();
        } else if (id == R.id.nav_statistics) {
            showStatistics(null);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showTrainings() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new MainFragment())
                .commit();
    }

    public void showStatistics(Bundle bundle) {
        if (bundle == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new ViewStatisticsFragment())
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, ViewStatisticsFragment.newInstance(bundle))
                    .commit();

            Menu menu = navigationView.getMenu();
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.nav_statistics) {
                    menu.getItem(i).setChecked(true);
                    break;
                }
            }
        }
    }
}