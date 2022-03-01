package com.alkhalij.guessword;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<HistoryItem> history;
    private LayoutInflater inflater;

    public HistoryAdapter(ArrayList<HistoryItem> history, LayoutInflater inflater) {
        this.history = history;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.history_card, parent));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvCardScore.setText("Score: " + history.get(position).getScore());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = format.format(history.get(position).getDateTime());
        holder.tvCardDate.setText(dateString);
        if (history.get(position).getScore() > 50) {
            holder.ivEmoji.setImageResource(R.drawable.happy);
        }
        else {
            holder.ivEmoji.setImageResource(R.drawable.think);
        }
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCardScore;
        private TextView tvCardDate;
        private ImageView ivEmoji;

        public ViewHolder(View view) {
            super(view);

            tvCardScore = view.findViewById(R.id.tv_score);
            tvCardDate = view.findViewById(R.id.tv_card_date);
            ivEmoji = view.findViewById(R.id.iv_emoji);
        }

    }
}
