package com.example.simulator;

import android.app.Application;

import com.example.simulator.models.DaoMaster;
import com.example.simulator.models.DaoSession;

import org.greenrobot.greendao.database.Database;


public class MyApplication extends Application {
    public static final boolean ENCRYPTED = false;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}



