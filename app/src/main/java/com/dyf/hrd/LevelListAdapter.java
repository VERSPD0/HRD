package com.dyf.hrd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class LevelListAdapter extends RecyclerView.Adapter<LevelListAdapter.LevelViewHolder> {
    private final LayoutInflater mInflater;
    private List<Level> mLevels; // Cached copy of words

    LevelListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public LevelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new LevelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LevelViewHolder holder, int position) {
        if (mLevels != null) {
            Level current = mLevels.get(position);
            holder.itemView.setTag(Integer.toString(current.getId()));
            if (current.isDIY())
                holder.levelTitleView.setText(current.getTitle());
            else
                holder.levelTitleView.setText("level" + Integer.toString(position + 1));
            if (current.resolved())
                holder.levelStatusView.setText(Integer.toString(current.getStep()));
            else
                holder.levelStatusView.setText("未解决");
        }
    }

    void setLevels(List<Level> levels){
        mLevels = levels;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mLevels != null)
            return mLevels.size();
        else return 0;
    }

    class LevelViewHolder extends RecyclerView.ViewHolder {
        private final TextView levelTitleView;
        private final TextView levelStatusView;
        private final View itemView;

        private LevelViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            levelTitleView = itemView.findViewById(R.id.level_title);
            levelStatusView = itemView.findViewById(R.id.level_status);
        }
    }
}
