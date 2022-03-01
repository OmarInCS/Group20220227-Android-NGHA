package com.alkhalij.guessword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private ArrayList<HistoryItem> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistory = findViewById(R.id.rv_history);

        try {
            loadHistoryData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(new HistoryAdapter(history, LayoutInflater.from(this)));

    }

    private void loadHistoryData() throws ParseException {
        history = new ArrayList<>();
        SharedPreferences pref = getSharedPreferences("com.alkhalij.guesswordd", Context.MODE_PRIVATE);
        Map<String, ?> prefData = pref.getAll();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Map.Entry<String, ?> entry : prefData.entrySet()) {
            HistoryItem item = new HistoryItem(
                    format.parse(entry.getKey()),
                    (int) entry.getValue()
            );
            history.add(item);
        }

        System.out.println(history);
    }
}