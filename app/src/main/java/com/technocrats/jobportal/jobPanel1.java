package com.technocrats.jobportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class jobPanel1 extends AppCompatActivity {
    CardView addjob, searchjob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_panel1);

        addjob = findViewById(R.id.addjob);
        searchjob = findViewById(R.id.findjob);

        searchjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchJob = new Intent(jobPanel1.this, Design.class);
                startActivity(searchJob);
            }
        });

        addjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent findJob = new Intent(jobPanel1.this, postJob1.class);
                startActivity(findJob);
            }
        });
    }
}