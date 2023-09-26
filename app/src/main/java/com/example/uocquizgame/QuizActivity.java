package com.example.uocquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        TextView edTitle=findViewById(R.id.txtTitle);
        GameController controller=GameController.getInstance();
        controller.addScoreObserver(new GameController.GameControllerScoreObserver() {
            @Override
            public void onScoreChanged() {
                edTitle.setText(GameController.getInstance().getPlayer()+ "- Score:"+GameController.getInstance().getScore()+ " - Level:"+GameController.getInstance().getLevel());
            }
        });
        controller.setLevel(0);
        controller.setScore(0);
        controller.initTest();
    }

}