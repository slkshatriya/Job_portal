package com.technocrats.jobportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    CardView mark,design,it,teach,med,eng,bank,editor;
    ImageView profile;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mark = findViewById(R.id.mark);
        design = findViewById(R.id.design);
        it = findViewById(R.id.it);
        teach = findViewById(R.id.teach);
        med = findViewById(R.id.med);
        eng = findViewById(R.id.eng);
        bank = findViewById(R.id.bank);
        editor = findViewById(R.id.editor);
        profile = findViewById(R.id.gotoprofile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.getInstance().signOut();
                Intent intent10 = new Intent(MainActivity.this, Login.class);
                intent10.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent10);
                Toast.makeText(MainActivity.this, "Logout successfull", Toast.LENGTH_SHORT).show();
            }
        });

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, jobPanel.class);
                startActivity(intent);
            }
        });

        design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, jobPanel1.class);
                startActivity(intent1);
            }
        });

        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, jobPanel2.class);
                startActivity(intent2);
            }
        });

        teach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, jobPanel3.class);
                startActivity(intent3);
            }
        });

        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, jobPanel4.class);
                startActivity(intent4);
            }
        });

        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(MainActivity.this, jobPanel5.class);
                startActivity(intent5);
            }
        });

        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6 = new Intent(MainActivity.this, jobPanel6.class);
                startActivity(intent6);
            }
        });

        editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7 = new Intent(MainActivity.this, jobPanel7.class);
                startActivity(intent7);
            }
        });


    }
}