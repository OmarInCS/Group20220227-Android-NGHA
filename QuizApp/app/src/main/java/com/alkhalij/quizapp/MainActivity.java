package com.alkhalij.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_easy).setOnClickListener(view -> startQuiz(view.getId()));
        findViewById(R.id.bt_normal).setOnClickListener(view -> startQuiz(view.getId()));
        findViewById(R.id.bt_hard).setOnClickListener(view -> startQuiz(view.getId()));
    }

    private void startQuiz(int id) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("level",  id);
        startActivity(intent);
    }
}