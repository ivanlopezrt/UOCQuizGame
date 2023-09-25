package com.example.uocquizgame;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uocquizgame.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.uocquizgame.databinding.FragmentUnitBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyUnitRecyclerViewAdapter extends RecyclerView.Adapter<MyUnitRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;
    private Context context;


    public MyUnitRecyclerViewAdapter(List<PlaceholderItem> items, Context context) {
        mValues = items;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentUnitBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        GameController controller=GameController.getInstance();
        holder.mItem = mValues.get(position);
        holder.mIcon.setImageResource(mValues.get(position).icon);
        holder.mUnitName.setText(mValues.get(position).description);
        switch(controller.unitsPassed[position]){
            case PASSED:
                holder.mCardView.setBackground(context.getDrawable(R.drawable.gradient_green_color));
            case FAILED:
                holder.mCardView.setBackground(context.getDrawable(R.drawable.gradient_red_color));
            default:
                //nothing to be done!
        }
        controller.addObserver(new GameController.GameControllerObserver() {
            @Override
            public void onQuizStateChanged() {
                notifyDataSetChanged();
            }
        });
        holder.mCardView.setOnClickListener(view -> {
            Intent i=new Intent(context,QuestionsActivity.class);
            i.putExtra("quiz_number",position);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mIcon;
        public final TextView mUnitName;
        public PlaceholderItem mItem;
        public final CardView mCardView;
        public ViewHolder(FragmentUnitBinding binding) {
            super(binding.getRoot());
            mIcon = binding.imgIcon;
            mUnitName = binding.txtUnitName;
            mCardView=binding.cardView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUnitName.getText() + "'";
        }
    }
}