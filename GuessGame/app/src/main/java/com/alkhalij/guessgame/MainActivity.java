package com.alkhalij.guessgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvMessage;
    private ImageView ivImoji;
    private EditText etGuess;
    private Button btCheck;
    private Button btReset;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.tv_message);
        ivImoji = findViewById(R.id.iv_imoji);
        etGuess = findViewById(R.id.et_guess);
        btCheck = findViewById(R.id.bt_check);
        btReset = findViewById(R.id.bt_reset);
        number = (int) (Math.random() * 100);

        btCheck.setOnClickListener(view -> checkAnswer());
    }

    private void checkAnswer() {
        int guess = Integer.parseInt(etGuess.getText().toString());

        if (guess > number) {
            tvMessage.setText("Guess a smaller number");
        }
        else if (guess < number) {
            tvMessage.setText("Guess a greater number");
        }
        else {
            tvMessage.setText("You Guessed Right!!");
            ivImoji.setImageResource(R.drawable.happy);
        }
    }
}