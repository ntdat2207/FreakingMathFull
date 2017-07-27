package com.example.administrator.freakingmathfull;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Vibrator;
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
    Vibrator v;
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        initView();

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        player = MediaPlayer.create(PlayGameActivity.this,R.raw.button);

        PlayGame();
        btnTrue.setOnClickListener(this);
        btnFalse.setOnClickListener(this);

        // Đếm thời gian, mỗi lần chạy giảm progress đi 1 đơn vị
        // Khi progress = 0 thì khóa các nút bấm lại => tránh lỗi
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
                v.vibrate(500);
                StopGame();
            }
        };
    }

    // Ánh xạ các view cần thiết
    private void initView() {
        txtNumA = (TextView) findViewById(R.id.txtNumA);
        txtNumB = (TextView) findViewById(R.id.txtNumB);
        txtResult = (TextView) findViewById(R.id.txtResult);
        btnTrue = (ImageButton) findViewById(R.id.btnTrue);
        btnFalse = (ImageButton) findViewById(R.id.btnFalse);
        prb = (ProgressBar) findViewById(R.id.prb);
    }

    // Hàm chơi game
    // Random số và kết quả
    // Tính tổng 2 số và so sánh với kết quả random, đúng thì gán flag = true
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

    // bấm vào nút đúng thì gửi true đi, sai thì gửi false đi
    // Gọi hàm CheckGame để kiểm tra
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnTrue:
                player.start();
                timer.cancel();
                CheckGame(true);
                break;
            case R.id.btnFalse:
                player.start();
                timer.cancel();
                CheckGame(false);
                break;
        }
    }

    // So sánh giá trị gửi đi khi click vào nút với flag
    // Giống nhau thì tăng điểm lên 1, gọi lại hàm chơi game và khởi động timer
    // Sai thì gọi hàm dừng game và dừng timer lại
    private void CheckGame(boolean b) {
        if(b == flag){
            score++;
            PlayGame();
            timer.start();
        }
        else{
            timer.cancel();
            v.vibrate(500);
            StopGame();
        }
    }

    // Hàm dừng chơi game và hỏi người dùng có muốn chơi lại không
    // Hiển thị lên các thông tin về điểm hiện tại, điểm cao nhất
    // Cho người dùng chọn chơi lại hoặc không
    private void StopGame() {
        final Dialog dialog = new Dialog(PlayGameActivity.this);
        dialog.setContentView(R.layout.end_game_dialog);
        TextView txtTotalScore = (TextView) dialog.findViewById(R.id.txtTotalScore);
        TextView txtHighScore = (TextView) dialog.findViewById(R.id.txtHighScore);
        final ImageButton btnPlayAgain = (ImageButton) dialog.findViewById(R.id.btnPlayAgain);
        ImageButton btnStop = (ImageButton) dialog.findViewById(R.id.btnStop);
        txtTotalScore.setText(score+"");
        SharedPreferences preference = getSharedPreferences("SCORE",MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();

        int highScore = preference.getInt("SCORE",0);
        txtHighScore.setText(highScore+"");
        if(highScore < score){
            editor.putInt("SCORE",score);
            editor.commit();
        }
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayGameActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                score = 0;
                PlayGame();
                btnTrue.setEnabled(true);
                btnFalse.setEnabled(true);
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}
