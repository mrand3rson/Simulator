package com.example.simulator.views.statistics;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simulator.R;
import com.example.simulator.custom.StatisticsAdapter;
import com.example.simulator.custom.VerticalSpaceItemDecoration;
import com.example.simulator.models.Statistics;
import com.example.simulator.presenters.ViewStatisticsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewStatisticsFragment extends Fragment {

    private final int VERTICAL_SPACE_RECYCLER_VIEW = 8;
    private LinearLayoutManager gridLayoutManager;

    public ViewStatisticsPresenter getPresenter() {
        return presenter;
    }
    ViewStatisticsPresenter presenter;

    @BindView(R.id.rv)
    RecyclerView rView;

    @BindView(R.id.nothing)
    TextView nothing;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_statistics, container, false);

        ButterKnife.bind(this, v);
        presenter = new ViewStatisticsPresenter(this.getActivity());

        Bundle extras = getArguments();
        if (extras != null) {
            presenter.add(buildStat(extras));
        }
        presenter.loadList();

        gridLayoutManager = new LinearLayoutManager(rView.getContext());
        rView.setLayoutManager(gridLayoutManager);
        rView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_SPACE_RECYCLER_VIEW));
        rView.setAdapter(new StatisticsAdapter(getActivity(), R.layout.adapter_statistics_item, presenter.getList()));
        implementScrollListener();

        if (presenter.getList().size() == 0)
            nothing.setVisibility(View.VISIBLE);

        return v;
    }

    protected Statistics buildStat(Bundle extras) {
        Integer trainingType = extras.getInt("training_type");
        String duration = extras.getString("duration");
        String description = extras.getString("description");
        String date = extras.getString("date");
        return new Statistics(null, trainingType, duration, date, description);
    }

    public void implementScrollListener() {
        rView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {

                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    loadMoreData(gridLayoutManager.getItemCount());
                }
            }
        });
    }

    private void loadMoreData(int itemsCount) {
        presenter.loadMore(itemsCount);
        rView.getAdapter().notifyDataSetChanged();
    }

    public static Fragment newInstance(Bundle args) {
        ViewStatisticsFragment fragment = new ViewStatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
