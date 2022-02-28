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

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private TextView tvScore;
    private LinearLayout llWord;
    private FloatingActionButton fbCheck;
    private FloatingActionButton fbReferesh;
    private FloatingActionButton fbHelp;

    private static final String API_URL = "https://random-word-form.herokuapp.com/random/noun";
    private final AsyncHttpClient client = new AsyncHttpClient();

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
                System.out.println(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("Error");
                System.out.println(responseString);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}