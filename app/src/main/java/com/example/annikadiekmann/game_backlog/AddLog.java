package com.example.annikadiekmann.game_backlog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class AddLog extends AppCompatActivity {

    EditText mTitle;
    EditText mPlatform;
    EditText mNotes;
    Spinner mStatus;

    static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        mTitle = findViewById(R.id.add_titleText);
        mPlatform = findViewById(R.id.add_platformText);
        mNotes = findViewById(R.id.add_notesText);
        mStatus = findViewById(R.id.add_spinner);

        //Initialize the local variables
        db = AppDatabase.getInstance(this);

        ArrayAdapter<CharSequence> myStatusAdapter = ArrayAdapter.createFromResource(this,
                R.array.statuses, android.R.layout.simple_spinner_item);

        myStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatus.setAdapter(myStatusAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_save_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mTitle.getText().toString();
                String platform = mPlatform.getText().toString();
                String notes = mNotes.getText().toString();
                String status = mStatus.getSelectedItem().toString();

                Log newGame = new Log(title,platform,notes,status);

                if (!TextUtils.isEmpty(title)) {

                    //new LoggerAsyncTask(TASK_INSERT_REMINDER).execute(newLogger);
                    Intent resultIntent = new Intent(AddLog.this,MainActivity.class);
                    resultIntent.putExtra(MainActivity.EXTRA_REMINDER, newGame);
                    setResult(Activity.RESULT_OK, resultIntent);

                    finish();



                } else {
                    Toast.makeText(AddLog.this, "Enter some data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
