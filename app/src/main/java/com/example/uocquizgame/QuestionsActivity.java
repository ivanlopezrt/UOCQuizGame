package com.example.uocquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uocquizgame.placeholder.PlaceholderContent;

public class QuestionsActivity extends AppCompatActivity {
    private final int TIME_OUT=30000;

    TextView counter;
    TextView txtQuestion;
    TextView txtUnitTitle;
    ImageView imgUnit;
    int quizNumber;
    GameController controller;
    GameController.GameControllerQuestionObserver observer;
    TextView txtProgress;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        quizNumber=getIntent().getExtras().getInt("quiz_number");
        txtQuestion=findViewById(R.id.txtQuestion);
        txtUnitTitle=findViewById(R.id.txtUnitTitle);
        imgUnit =findViewById(R.id.imgUnit);
        txtProgress=findViewById(R.id.txtProgress);
        controller=GameController.getInstance();
        controller.initTest();
        loadQuiz(quizNumber);
        observer=new GameController.GameControllerQuestionObserver() {
            @Override
            public void onQuestionChanged() {
                checkUnitPassed();
            }
        };
        controller.addQuestionObserver(observer);
        controller.setCurrentUnit(quizNumber);


        txtQuestion.setText(QuizContent.ITEMS.get(controller.getCurrentQuestion()).getTitle());
        txtUnitTitle.setText(PlaceholderContent.UNITS.get(quizNumber).description);
        txtProgress.setText("Question " + (controller.getCurrentQuestion() + 1) + "/" + QuizContent.ITEMS.size() + " - Right Answers: " + controller.getCorrectAnswersInCurrentTest());
        imgUnit.setImageResource(PlaceholderContent.UNITS.get(quizNumber).icon);
        counter=findViewById(R.id.txtCounter);

        countDownTimer=new CountDownTimer(TIME_OUT,1000){
            @Override
            public void onTick(long l) {
                counter.setText(l/1000+" seconds left");
            }

            @Override
            public void onFinish() {
                controller.setCurrentQuestion(QuizContent.ITEMS.size()); //disable further answers
                play(R.raw.gong);
                checkUnitPassed();
            }
        }.start();

    }

    private void checkUnitPassed(){
        if(controller.getCurrentQuestion()==QuizContent.ITEMS.size() ){
            if(QuizContent.ITEMS.size()==controller.getCorrectAnswersInCurrentTest()) {
                controller.changeUnitState(GameController.UnitType.PASSED,quizNumber);
                txtProgress.setText("END OF TEST: Total Right Answers: "+controller.getCorrectAnswersInCurrentTest()+" - TEST PASSED!");
                controller.updateLevel();
                controller.updateScore(10);
                play(R.raw.cheer);
                countDownTimer.cancel();
                counter.setText("FINISHED! WELL DONE!");

            }
            else {
                controller.changeUnitState(GameController.UnitType.FAILED,quizNumber);
                txtProgress.setText("END OF TEST: Total Right Answers: "+controller.getCorrectAnswersInCurrentTest()+" - TEST FAILED!");
                play(R.raw.fail);
                countDownTimer.cancel();
                counter.setText("FINISHED! TEST FAILED!");
            }
        }
        else {
            txtQuestion.setText(QuizContent.ITEMS.get(controller.getCurrentQuestion()).getTitle());
            txtProgress.setText("Question " + (controller.getCurrentQuestion() + 1) + "/" + QuizContent.ITEMS.size() + " - Right Answers: " + controller.getCorrectAnswersInCurrentTest());
        }
    }

    private void play(int resource){
        MediaPlayer mp=MediaPlayer.create(this,resource);
        mp.start();
    }



    public void loadQuiz(int number){
        QuizContent.loadQuestionsFromJSON(this,number);
    }

    @Override
    protected void onDestroy() {
        controller.removeQuestionObserver(observer);
        super.onDestroy();
    }
}