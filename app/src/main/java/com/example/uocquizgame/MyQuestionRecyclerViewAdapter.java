package com.example.uocquizgame;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uocquizgame.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.uocquizgame.databinding.FragmentQuestionBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyQuestionRecyclerViewAdapter extends RecyclerView.Adapter<MyQuestionRecyclerViewAdapter.ViewHolder> {

    private final List<QuizContent.Question> mValues;

    public MyQuestionRecyclerViewAdapter(List<QuizContent.Question> items) {
        mValues = items;
        GameController controller=GameController.getInstance();
        controller.addQuestionObserver(new GameController.GameControllerQuestionObserver() {
            @Override
            public void onQuestionChanged() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentQuestionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        GameController controller=GameController.getInstance();

        if(controller.getCurrentQuestion()!=QuizContent.ITEMS.size()) {
            QuizContent.Answer answer = QuizContent.ITEMS.get(controller.getCurrentQuestion()).getPossibleAnswers().get(position);
            holder.mItem = answer;
            holder.mContentView.setText(answer.description);


            holder.mContentView.setOnClickListener(view -> {
                if (answer.isRightAnswer) {
                    controller.setCorrectAnswersInCurrentTest(controller.getCorrectAnswersInCurrentTest() + 1);
                    controller.updateScore(1);
                }

                if (controller.getCurrentQuestion() != QuizContent.ITEMS.size()) {
                    controller.setCurrentQuestion(controller.getCurrentQuestion() + 1);
                }
            });
        }
        else
            Log.d("UOC","END OF TEST");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public QuizContent.Answer mItem;

        public ViewHolder(FragmentQuestionBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}