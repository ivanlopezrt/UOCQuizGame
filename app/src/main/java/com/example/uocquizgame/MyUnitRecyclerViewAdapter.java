package com.example.uocquizgame;

import androidx.recyclerview.widget.RecyclerView;

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

    public MyUnitRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentUnitBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIcon.setImageResource(mValues.get(position).icon);
        holder.mUnitName.setText(mValues.get(position).description);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mIcon;
        public final TextView mUnitName;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentUnitBinding binding) {
            super(binding.getRoot());
            mIcon = binding.imgIcon;
            mUnitName = binding.txtUnitName;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUnitName.getText() + "'";
        }
    }
}