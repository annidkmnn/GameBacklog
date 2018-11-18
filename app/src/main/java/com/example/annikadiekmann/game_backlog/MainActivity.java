package com.example.annikadiekmann.game_backlog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LogAdapter.LogClickListener {

    private List<Log> mLogs;
    private LogAdapter mAdapter;

    private RecyclerView mRecyclerView;

    public static final String EXTRA_REMINDER = "Game";
    public static final int REQUESTCODE_ADDING = 1234;
    public static final int REQUESTCODE_EDITING = 5678;
    private int mModifyPosition;

    public final static int TASK_GET_ALL_LOGS = 0;
    public final static int TASK_DELETE_LOG = 1;
    public final static int TASK_UPDATE_LOG = 2;
    public final static int TASK_INSERT_LOG = 3;

    static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize the local variables

        db = AppDatabase.getInstance(this);

        mLogs = new ArrayList<>();

        new LogAsyncTask(TASK_GET_ALL_LOGS).execute();

        mRecyclerView = findViewById(R.id.recyclerView);
        updateUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        new LogAsyncTask(TASK_DELETE_LOG).execute(mLogs.get(position));

                    }


                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToAddPage(View view) {

        Intent intent = new Intent(MainActivity.this, AddLog.class);
        startActivityForResult(intent,REQUESTCODE_ADDING);
    }

    void onGameDbUpdated(List list) {

        mLogs = list;
        updateUI();

    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new LogAdapter(mLogs, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mLogs);
        }

    }

    @Override
    public void logOnClick(int i) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        mModifyPosition = i;

        intent.putExtra(EXTRA_REMINDER, mLogs.get(i));
        startActivityForResult(intent, REQUESTCODE_EDITING);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUESTCODE_ADDING){
            if (resultCode == RESULT_OK){
                Log addedGame = data.getParcelableExtra(MainActivity.EXTRA_REMINDER);
                // New timestamp: timestamp of update
                // mLoggers.set(mModifyPosition, updatedReminder);
                new LogAsyncTask(TASK_INSERT_LOG).execute(addedGame);

                updateUI();
            }
        } else if (requestCode == REQUESTCODE_EDITING){
            if(resultCode == RESULT_OK){
                Log updatedGame = data.getParcelableExtra(MainActivity.EXTRA_REMINDER);
                // New timestamp: timestamp of update
                // mLoggers.set(mModifyPosition, updatedReminder);
                new LogAsyncTask(TASK_UPDATE_LOG).execute(updatedGame);

                updateUI();
            }
        }
    }


    public class LogAsyncTask extends AsyncTask<Log, Void, List> {

        private int taskCode;

        public LogAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }


        @Override
        protected List doInBackground(Log... logs) {
            switch (taskCode) {
                case TASK_DELETE_LOG:
                    db.logDao().deleteLogs(logs[0]);
                    break;

                case TASK_UPDATE_LOG:
                    db.logDao().updateLogs(logs[0]);
                    break;

                case TASK_INSERT_LOG:
                    db.logDao().insertLogs(logs[0]);
                    break;
            }

            //To return a new list with the updated data, we get all the data from the database again.
            return db.logDao().getAllLogs();
        }


        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onGameDbUpdated(list);
        }

    }
}
