package com.example.uocquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.uocquizgame.R;

public class QuestionsActivity extends AppCompatActivity {
    TextView counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        int quizNumber=getIntent().getExtras().getInt("quiz_number");
        GameController.getInstance().changeUnitState(GameController.UnitType.PASSED,quizNumber);
        loadQuiz(quizNumber);
        counter=findViewById(R.id.txtCounter);
        CountDownTimer countDownTimer=new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                counter.setText("Test starts in "+l/1000+" seconds");
            }

            @Override
            public void onFinish() {
                counter.setText("GO!");
                CountDownTimer countDownTimer2=new CountDownTimer(60000,1000){
                    @Override
                    public void onTick(long l) {
                        counter.setText(l/1000+" seconds left");
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        };
        countDownTimer.start();
    }

    public void loadQuiz(int number){
        QuizContent.loadQuestionsFromJSON(this);
    }
}