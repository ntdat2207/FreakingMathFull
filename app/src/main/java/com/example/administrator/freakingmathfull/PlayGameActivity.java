package com.example.administrator.freakingmathfull;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtNumA, txtNumB, txtResult;
    ImageButton btnTrue, btnFalse;
    ProgressBar prb;
    boolean flag = false;
    int score = 0;
    CountDownTimer timer;
    int progress = 59;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        initView();

        PlayGame();
        btnTrue.setOnClickListener(this);
        btnFalse.setOnClickListener(this);

        timer = new CountDownTimer(1000,10) {
            @Override
            public void onTick(long l) {
                prb.setProgress(progress);
                progress--;
                Log.d("DEMO",progress+"");
                if(progress == 0){
                    btnTrue.setEnabled(false);
                    btnFalse.setEnabled(false);
                }
            }

            @Override
            public void onFinish() {
                prb.setProgress(0);
                CheckScore();
            }
        };
    }

    private void initView() {
        txtNumA = (TextView) findViewById(R.id.txtNumA);
        txtNumB = (TextView) findViewById(R.id.txtNumB);
        txtResult = (TextView) findViewById(R.id.txtResult);
        btnTrue = (ImageButton) findViewById(R.id.btnTrue);
        btnFalse = (ImageButton) findViewById(R.id.btnFalse);
        prb = (ProgressBar) findViewById(R.id.prb);
    }

    private void PlayGame(){
        progress = 59;
        Random rd = new Random();
        int a = rd.nextInt(10) + 1;
        int b = rd.nextInt(10) + 1;
        int c = a + b;
        int show = rd.nextInt(3) + (c - 2);
        txtNumA.setText(a+"");
        txtNumB.setText(b+"");
        txtResult.setText(show+"");
        if(c == show){
            flag = true;
        }
        else{
            flag = false;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnTrue:
                timer.cancel();
                CheckGame(true);
                break;
            case R.id.btnFalse:
                timer.cancel();
                CheckGame(false);
                break;
        }
    }

    private void CheckGame(boolean b) {
        if(b == flag){
            score++;
            PlayGame();
            timer.start();
        }
        else{
            timer.cancel();
            CheckScore();
        }
    }

    private void CheckScore() {
        SharedPreferences preference = getSharedPreferences("SCORE",MODE_PRIVATE);
        int count = Integer.parseInt(preference.getString("SCORE","0"));
        if(score > count) {
            StopGame();
        }
        else{
            PlayAgain();
        }
    }

    private void StopGame() {
        final Dialog dialog = new Dialog(PlayGameActivity.this);
        dialog.setContentView(R.layout.end_game_dialog);
        Button btnSave = (Button) dialog.findViewById(R.id.btnSave);
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        TextView txtTotalScore = (TextView) dialog.findViewById(R.id.txtTotalScore);
        txtTotalScore.setText(score+"");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preference = getSharedPreferences("SCORE",MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.putString("NAME",edtName.getText().toString());
                editor.putString("SCORE",score+"");
                editor.commit();
                dialog.dismiss();
                PlayAgain();
            }
        });
        dialog.show();
    }

    private void PlayAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayGameActivity.this);
        builder.setTitle("Bạn có muốn chơi lại không");
        builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                score = 0;
                prb.setProgress(0);
                btnTrue.setEnabled(true);
                btnFalse.setEnabled(true);
                PlayGame();
            }
        });
        builder.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(PlayGameActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}
