package com.example.simulator.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simulator.R;
import com.example.simulator.models.Statistics;
import com.example.simulator.tools.TrainingTypes;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {

    private Context context;
    private int resource;
    private List<Statistics> list;

    public StatisticsAdapter(Context context, int resource, List<Statistics> list) {
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String duration = " (" + list.get(position).getDuration() +")";
        holder.typeAndDuration.setText(String.format(Locale.getDefault(),
                "%d. Type: %s",
                position+1,
                TrainingTypes.parseTrainingTypes(list.get(position).getTrainingType())));
        holder.typeAndDuration.append(duration);
        holder.description.setText(list.get(position).getDescription());

        String dateString = list.get(position).getDate();
        holder.date.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.type_and_duration)
        TextView typeAndDuration;

        @BindView(R.id.description)
        TextView description;

        @BindView(R.id.date)
        TextView date;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
