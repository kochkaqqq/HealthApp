package com.example.health;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatisticGraph extends Fragment {

    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic_graph, container, false);

        barChart = view.findViewById(R.id.barChart);
        setupBarChart();

        loadCaloriesDataFromServer();

        return view;
    }

    private void setupBarChart()
    {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        barChart.getXAxis().setDrawLabels(true);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getXAxis().setValueFormatter(new DayAxisValueFormatter());
    }

    private void loadCaloriesDataFromServer() {
        // получение данных с сервера
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 95)); // Сегодня
        entries.add(new BarEntry(1, 60)); // 1 день назад
        entries.add(new BarEntry(2, 45)); // 2 дня назад
        entries.add(new BarEntry(3, 112)); // 3 дня назад
        entries.add(new BarEntry(4, 30)); // 4 дня назад
        entries.add(new BarEntry(5, 45)); // 5 дня назад
        entries.add(new BarEntry(6, 88)); // 6 дня назад


        BarDataSet dataSet = new BarDataSet(entries, "Сожженные калории");
        dataSet.setColor(ContextCompat.getColor(requireContext(), R.color.main));
        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.invalidate(); // Перерисовать график
    }

    private static class DayAxisValueFormatter extends ValueFormatter {
        private final String[] daysLabels = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};

        @Override
        public String getFormattedValue(float value) {
            int index = (int) value;
            if (index >= 0 && index < daysLabels.length) {
                return daysLabels[index];
            } else {
                return "";
            }
        }
    }
}