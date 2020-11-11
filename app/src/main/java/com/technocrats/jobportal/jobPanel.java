package com.technocrats.jobportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class jobPanel extends AppCompatActivity {
    CardView findjob, searchjob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_panel);

        findjob = findViewById(R.id.addjob);
        searchjob = findViewById(R.id.findjob);

        searchjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchJob = new Intent(jobPanel.this, Mark.class);
                startActivity(searchJob);
            }
        });

        findjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent findJob = new Intent(jobPanel.this, postJob.class);
                startActivity(findJob);
            }
        });
    }
}