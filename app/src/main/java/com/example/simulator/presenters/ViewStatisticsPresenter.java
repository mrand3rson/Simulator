package com.example.simulator.presenters;

import android.app.Activity;

import com.example.simulator.MyApplication;
import com.example.simulator.models.DaoSession;
import com.example.simulator.models.Statistics;
import com.example.simulator.models.StatisticsDao;
import com.example.simulator.models.StatisticsDao.Properties;

import java.util.List;


public class ViewStatisticsPresenter implements IViewStatistics {
    private final int itemsPerPage = 5;
    private Activity activity;
    private StatisticsDao statisticsDao;

    public List<Statistics> getList() {
        return list;
    }
    private List<Statistics> list;


    public ViewStatisticsPresenter(Activity activity) {
        this.activity = activity;

        DaoSession daoSession = ((MyApplication)activity.getApplication()).getDaoSession();
        statisticsDao = daoSession.getStatisticsDao();
    }

    @Override
    public void loadList() {
        list = statisticsDao.queryBuilder()
                .limit(itemsPerPage)
                .orderDesc(Properties.Date)
                .list();
    }

    public void loadMore(int offset) {
        List<Statistics> newList = statisticsDao.queryBuilder()
                .offset(offset)
                .limit(itemsPerPage)
                .orderDesc(Properties.Date)
                .list();
        list.addAll(newList);
    }

    @Override
    public void add(Statistics stat) {
        statisticsDao.insert(stat);
    }
}
