package com.alkhalij.guessword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.Stack;

import cz.msebera.android.httpclient.Header;

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
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("Error");
                System.out.println(responseString);
            }

            @Override
            public void onStart() {
                System.out.println("Request started");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}