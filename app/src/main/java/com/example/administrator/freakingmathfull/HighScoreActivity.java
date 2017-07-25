package com.example.administrator.freakingmathfull;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {
    TextView txtName, txtScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        initView();
        SharedPreferences preferences = getSharedPreferences("SCORE",MODE_PRIVATE);

        String name = preferences.getString("NAME","Họ và tên");
        String score = preferences.getString("SCORE","Điểm");

        txtName.setText(name);
        txtScore.setText(score);
    }

    private void initView() {
        txtName = (TextView) findViewById(R.id.txtName);
        txtScore = (TextView) findViewById(R.id.txtScore);
    }
}
