package com.hkcyl.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;

public class DetailActivity extends AppCompatActivity {

  //  @Bind(R.id.event_detail)
    TextView event_detail;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        event_detail = (TextView) findViewById(R.id.event_detail);
        initData();
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String detail = intent.getStringExtra("detail");
        event_detail.setText(detail+" == text");
        toolbar.setTitle(title+"== yes");

    }
}
