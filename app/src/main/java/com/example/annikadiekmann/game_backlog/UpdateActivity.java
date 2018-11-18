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


public class UpdateActivity extends AppCompatActivity {

    EditText mTitle;
    EditText mPlatform;
    EditText mNotes;
    Spinner mStatus;

    static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        mTitle = findViewById(R.id.update_titleText);
        mPlatform = findViewById(R.id.update_platformText);
        mNotes = findViewById(R.id.update_notesText);
        mStatus = findViewById(R.id.update_spinner);

        //Initialize the local variables
        db = AppDatabase.getInstance(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(UpdateActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.statuses));

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatus.setAdapter(dataAdapter);

        final Log logUpdate = getIntent().getParcelableExtra(MainActivity.EXTRA_REMINDER);
        mTitle.setText(logUpdate.getTitle());
        mPlatform.setText(logUpdate.getPlatform());
        mNotes.setText(logUpdate.getNotes());
        mStatus.setSelection(0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.update_save_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = mTitle.getText().toString();
                String platform = mPlatform.getText().toString();
                String notes = mNotes.getText().toString();
                String status = mStatus.getSelectedItem().toString();

                Log newGame = new Log(title,platform,notes,status);

                if (!TextUtils.isEmpty(title)) {

                    logUpdate.setTitle(title);
                    logUpdate.setPlatform(platform);
                    logUpdate.setNotes(notes);
                    logUpdate.setStatus(status);

                    //Prepare the return parameter and return
                    Intent resultIntent = new Intent(UpdateActivity.this,MainActivity.class);
                    resultIntent.putExtra(MainActivity.EXTRA_REMINDER, logUpdate);
                    setResult(Activity.RESULT_OK, resultIntent);

                    finish();

                } else {
                    Toast.makeText(UpdateActivity.this, "Enter some data", Toast.LENGTH_SHORT).show();
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
