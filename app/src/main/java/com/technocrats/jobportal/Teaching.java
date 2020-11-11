package com.technocrats.jobportal;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Teaching extends AppCompatActivity implements OnClickListener{
    RecyclerView recview;
    myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching);

        recview = findViewById(R.id.recview3);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Teach"), model.class)
                        .build();

        adapter = new myadapter(options, this);
        recview.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onClick(View transitionView, model model) {
        Intent intent = new Intent(this, FullDescriptionActivity.class);
        intent.putExtra(ConstantsKt.JOB_TITLE,model.title);
        intent.putExtra(ConstantsKt.JOB_EMAIL,model.email);
        intent.putExtra(ConstantsKt.JOB_PHOTO,model.purl);
        intent.putExtra(ConstantsKt.JOB_DESCRIPTION,model.description);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this,transitionView, String.valueOf(R.string.transition_photo)).toBundle();
        startActivity(intent,bundle);
    }
}