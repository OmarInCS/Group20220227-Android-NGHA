package com.alkhalij.guessgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
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
        etGuess.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                checkAnswer();
                return true;
            }
            return false;
        });
        btReset.setOnClickListener(view -> resetGame());
    }

    private void resetGame() {
        tvMessage.setText(R.string.init_message);
        etGuess.setText("");
        ivImoji.setImageResource(R.drawable.think);
        number = (int) (Math.random() * 100);
    }

    private void checkAnswer() {
        int guess = Integer.parseInt(etGuess.getText().toString());

        if (guess > number) {
            tvMessage.setText(R.string.smaller);
        }
        else if (guess < number) {
            tvMessage.setText(R.string.greater);
        }
        else {
            tvMessage.setText(R.string.right);
            ivImoji.setImageResource(R.drawable.happy);
        }
    }
}