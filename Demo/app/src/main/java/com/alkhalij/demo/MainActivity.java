package com.alkhalij.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvMessage;
    private EditText etName;
    private Button btClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.tv_message);
        etName = findViewById(R.id.et_name);
        btClick = findViewById(R.id.bt_click);

        btClick.setOnClickListener(view -> {
            String name = etName.getText().toString();
            tvMessage.setText("Hello, " + name);
        });
    }
}