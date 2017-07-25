package com.example.administrator.freakingmathfull;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnPlay;
    TextView txt;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btnPlay.setOnClickListener(this);
        Typeface tp = Typeface.createFromAsset(getAssets(),"font/Finition.ttf");
        txt.setTypeface(tp);
    }

    private void initView() {
        btnPlay =(Button) findViewById(R.id.btnPlay);
        txt = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnPlay:
                intent = new Intent(MainActivity.this,PlayGameActivity.class);
                startActivity(intent);
                break;
        }
    }
}
