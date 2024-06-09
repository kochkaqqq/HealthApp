package com.example.health.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.health.R;
import com.example.health.domain.Train;

import java.util.List;

public class MyTrainsListAdapter extends BaseAdapter {

    Context context;
    List<Train> trains;

    LayoutInflater inflater;

    public MyTrainsListAdapter(Context ctx, List<Train> trains) {
        this.context = ctx;
        this.trains = trains;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return trains.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_train_list_view_adapter, null);

        TextView textView = convertView.findViewById(R.id.trainName);
        textView.setText(trains.get(position).Name);

        return convertView;
    }
}
