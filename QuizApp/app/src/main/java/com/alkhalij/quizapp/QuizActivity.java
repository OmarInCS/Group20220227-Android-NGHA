package com.alkhalij.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
// 05 99 888 921
public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private TextView tvTime;
    private Button[] btChoices;
    private int level, x, y, numOfQuestions, correct;
    private ArrayList<Integer> choices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        level = getIntent().getIntExtra("level", -1);
        tvQuestion = findViewById(R.id.tv_question);
        tvTime = findViewById(R.id.tv_time);
        btChoices = new Button[] {
                findViewById(R.id.bt_choice_0),
                findViewById(R.id.bt_choice_1),
                findViewById(R.id.bt_choice_2),
                findViewById(R.id.bt_choice_3)
        };

        generateQuestion();
        generateChoices();

        for (int i = 0; i < btChoices.length; i++) {
            int finalI = i;
            btChoices[i].setOnClickListener(view -> checkChoice(finalI));
        }
    }

    private void checkChoice(int idx) {
        int userChoice = choices.get(idx);

        if (userChoice == x*y) {
            Toast
                .makeText(this,
                        "Correct Answer",
                        Toast.LENGTH_SHORT)
                .show();
            correct++;
        }
        else {
            Toast
                .makeText(this,
                        "Wrong Answer",
                        Toast.LENGTH_SHORT)
                .show();
        }

        checkIfCompleted();
    }

    private void checkIfCompleted() {

        if (numOfQuestions != 5) {
            generateQuestion();
            generateChoices();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Your score");
            builder.setMessage("Your score is: " + correct + " out of 5");
            builder.setNeutralButton("OK", (dialog, i) -> {
                dialog.cancel();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });

            builder.create().show();
        }

    }


    private void generateChoices() {
        choices = new ArrayList<>();
        choices.add(x * y);
        for (int i = 1; i < 4; i++) {
            int ch = (int) (Math.random() * 101);
            choices.add(ch);
        }
        Collections.shuffle(choices);

        for (int i = 0; i < 4; i++) {
            btChoices[i].setText(choices.get(i).toString());
        }
    }

    private void generateQuestion() {
        numOfQuestions++;
        x = (int) (Math.random() * 11);
        y = (int) (Math.random() * 11);
        tvQuestion.setText(getString(R.string.question, x, y));
    }
}