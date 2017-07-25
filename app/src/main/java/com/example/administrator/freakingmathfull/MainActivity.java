package com.example.administrator.freakingmathfull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnPlay, btnHighScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btnPlay.setOnClickListener(this);
        btnHighScore.setOnClickListener(this);
    }

    private void initView() {
        btnPlay =(Button) findViewById(R.id.btnPlay);
        btnHighScore = (Button) findViewById(R.id.btnHighScore);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnPlay:
                Toast.makeText(this, "PLAY", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnHighScore:
                Toast.makeText(this, "HIGH", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
