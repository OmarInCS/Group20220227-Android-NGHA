package com.alkhalij.guessword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Stack;

import cz.msebera.android.httpclient.Header;
// 05 99 888 921
public class MainActivity extends AppCompatActivity {

    private TextView tvScore;
    private LinearLayout llWord;
    private FloatingActionButton fbCheck;
    private FloatingActionButton fbReferesh;
    private FloatingActionButton fbHelp;

    private static final String API_URL = "https://random-word-form.herokuapp.com/random/noun";
    private final AsyncHttpClient client = new AsyncHttpClient();
    private Stack<String> words;
    private String currentWord, blurredWord;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore = findViewById(R.id.tv_score);
        llWord = findViewById(R.id.ll_word);
        fbCheck = findViewById(R.id.fb_check);
        fbReferesh = findViewById(R.id.fb_referesh);
        fbHelp = findViewById(R.id.fb_help);

        loadTenWords();

        fbCheck.setOnClickListener(view -> checkAnswer());
    }

    private void loadTenWords() {
        RequestParams params = new RequestParams();
        params.put("count", 10);

        client.get(API_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                words = new Stack<>();
                for (int i = 0; i < response.length(); i++) {
                    String w = response.optString(i);
                    if (w.length() >= 4 && w.length() <= 10) {
                        words.push(w);
                    }
                }
                blurredWord = createBlurredWord();
                if (blurredWord != null) {
                    displayLetters(blurredWord);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println(errorResponse);
            }
        });
    }

    public String createBlurredWord() {

        if (words.empty()) {
            loadTenWords();
            return null;
        }

        currentWord = words.pop();
        System.out.println(currentWord);
        int idx1 = (int) (Math.random() * currentWord.length());
        int idx2 = (int) (Math.random() * currentWord.length());

        StringBuilder sb = new StringBuilder(currentWord);
        sb.setCharAt(idx1, '?');
        sb.setCharAt(idx2, '?');

        return sb.toString();
    }

    private void displayLetters(String word) {
        llWord.removeAllViews();

        for (char ch : word.toCharArray()) {
            EditText et = new EditText(this);

            et.setTextSize(64);
            et.setInputType(InputType.TYPE_CLASS_TEXT);
            et.setMaxLines(1);
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

            if (ch == '?') {
                et.setHint("?");
            }
            else {
                et.setText(ch + "");
                et.setFocusable(false);
            }

            llWord.addView(et);
        }
    }

    private void checkAnswer() {
        String answer = "";
        for (int i = 0; i < llWord.getChildCount(); i++) {
            EditText et = (EditText) llWord.getChildAt(i);
            answer += et.getText().toString();
        }

        if (answer.equals(currentWord)) {
            score += 10;
            tvScore.setText(score + "");
            blurredWord = createBlurredWord();
            if (blurredWord != null) {
                displayLetters(blurredWord);
            }
        }
        else {
            Snackbar.make(llWord, "You Missed It!!", Snackbar.LENGTH_SHORT).show();
            saveScore();
        }
    }

    private void saveScore() {
        SharedPreferences pref = getSharedPreferences("com.alkhalij.guesswordd", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        editor.putInt(format.format(new Date()), score);
        editor.apply();

        score = 0;
        tvScore.setText("00");
        blurredWord = createBlurredWord();
        if (blurredWord != null) {
            displayLetters(blurredWord);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mi_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, blurredWord);
            startActivity(intent);
        }
        else if (id == R.id.mi_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}