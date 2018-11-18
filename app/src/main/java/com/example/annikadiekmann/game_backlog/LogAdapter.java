package com.example.annikadiekmann.game_backlog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

    private List<Log> mLogs;
    final private LogClickListener mLogClickListener;
    private Context mContext;

    public interface LogClickListener{
        void logOnClick(int i);
    }

    public LogAdapter(List<Log> mLogs, LogClickListener mLogClickListener) {
        this.mLogs = mLogs;
        this.mLogClickListener = mLogClickListener;
    }

    @NonNull
    @Override
    public LogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rowlayout, parent, false);

        // Return a new holder instance
        LogAdapter.ViewHolder viewHolder = new LogAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull LogAdapter.ViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy / MM / dd ");
        String currentDate = format.format(calendar.getTime());

        Log game = mLogs.get(position);
        holder.mTitle.setText(game.getTitle());
        holder.mPlatform.setText(game.getPlatform());
        holder.mStatus.setText(game.mStatus);
        holder.mDate.setText(currentDate);
    }

    @Override
    public int getItemCount() {
        return mLogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTitle;
        public TextView mPlatform;
        public TextView mStatus;
        public TextView mDate;

        public ViewHolder(View itemView) {

            super(itemView);
            mTitle = itemView.findViewById(R.id.titleText);
            mPlatform = itemView.findViewById(R.id.platfromText);
            mStatus = itemView.findViewById(R.id.statusText);
            mDate = itemView.findViewById(R.id.dateText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mLogClickListener.logOnClick(clickedPosition);
        }

    }

    public void swapList (List<Log> newList) {


        mLogs = newList;

        if (newList != null) {

            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();

        }

    }


}
