package com.example.uocquizgame;

import android.content.Context;
import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuizContent {
    public static List<Question> ITEMS = new ArrayList<Question>();

    public static void loadQuestionsFromJSON(Context c) {

        String json = null;
        try {
            InputStream is =
                    c.getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray questionsList = jsonObject.getJSONArray("questions_list");
            for (int i = 0; i < questionsList.length(); i++) {
                JSONObject jsonQuestion = questionsList.getJSONObject(i);

                String title = jsonQuestion.getString("title");

                Question q=new Question(title);
                JSONArray answerList = jsonObject.getJSONArray("answers");
                for (int j=0;j<answerList.length();j++){
                    JSONObject jsonAnswer=answerList.getJSONObject(j);
                    String answer=jsonAnswer.getString("answer");
                    boolean isRight=jsonAnswer.getBoolean("isRight");
                    q.addPossibleAnswer(new Answer(answer,isRight));
                }
                ITEMS.add(q);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

    public static class Question {
        private String title;
        private List<Answer> possibleAnswers= new ArrayList<Answer>();

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void addPossibleAnswer(Answer a){
            possibleAnswers.add(a);
        }

        public Question(String title) {
            this.title = title;
        }


    }

    public static class Answer{
        String description;
        boolean isRightAnswer;
        public Answer(String description,boolean isRightAnswer){
            this.description=description;
            this.isRightAnswer=isRightAnswer;
        }
    }

}
