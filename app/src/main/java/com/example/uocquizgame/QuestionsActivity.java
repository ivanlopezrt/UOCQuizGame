package com.example.uocquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.uocquizgame.R;
import com.example.uocquizgame.placeholder.PlaceholderContent;

public class QuestionsActivity extends AppCompatActivity {
    TextView counter;
    TextView txtQuestion;
    GameController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        int quizNumber=getIntent().getExtras().getInt("quiz_number");
        TextView txtQuestion=findViewById(R.id.txtQuestion);
        TextView txtProgress=findViewById(R.id.txtProgress);
        controller=GameController.getInstance();
        controller.initTest();
        loadQuiz(quizNumber);
        controller.addQuestionObserver(new GameController.GameControllerQuestionObserver() {
            @Override
            public void onQuestionChanged() {
                if(controller.getCurrentQuestion()==QuizContent.ITEMS.size()){
                    if(QuizContent.ITEMS.size()==controller.getCorrectAnswersInCurrentTest()) {
                        controller.changeUnitState(GameController.UnitType.PASSED,quizNumber);
                        txtProgress.setText("END OF TEST: Total Right Answers: "+controller.getCorrectAnswersInCurrentTest()+" - TEST PASSED!");
                        controller.updateLevel();
                        controller.updateScore(10);
                    }
                    else {
                        controller.changeUnitState(GameController.UnitType.FAILED,quizNumber);
                        txtProgress.setText("END OF TEST: Total Right Answers: "+controller.getCorrectAnswersInCurrentTest()+" - TEST FAILED!");
                    }
                }
                else {
                    txtQuestion.setText(QuizContent.ITEMS.get(controller.getCurrentQuestion()).getTitle());
                    txtProgress.setText("Question " + (controller.getCurrentQuestion() + 1) + "/" + QuizContent.ITEMS.size() + " - Right Answers: " + controller.getCorrectAnswersInCurrentTest());
                }

            }
        });

        controller.setCurrentUnit(quizNumber);


        txtQuestion.setText(QuizContent.ITEMS.get(controller.getCurrentQuestion()).getTitle());
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
                        int totalQuestions=QuizContent.ITEMS.size();
                        if(totalQuestions==controller.getCorrectAnswersInCurrentTest()) {
                            counter.setText("You got all the questions right!");
                            GameController.getInstance().changeUnitState(GameController.UnitType.PASSED,quizNumber);
                        }
                        else {
                            counter.setText("You got " + controller.getCorrectAnswersInCurrentTest() + " out of " + totalQuestions + " questions right");
                            GameController.getInstance().changeUnitState(GameController.UnitType.FAILED,quizNumber);
                        }
                    }
                }.start();
            }
        };
        countDownTimer.start();
    }

    public void loadQuiz(int number){
        QuizContent.loadQuestionsFromJSON(this,number);
    }

}