package com.technocrats.jobportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";
    TextView textsignin;
    private FirebaseAuth firebaseAuth;
    ImageView regbtn;
    EditText inputname,inputemail1,inputpassword1;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        textsignin = findViewById(R.id.textsignin);
        regbtn = findViewById(R.id.regbtn);
        inputname = findViewById(R.id.inputname);
        inputemail1 = findViewById(R.id.inputemail1);
        inputpassword1 = findViewById(R.id.inputpassword1);
        progressDialog = new ProgressDialog(this);



        textsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent login = new Intent(Register.this, MainActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login);
        }

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.show();
                String email = inputemail1.getText().toString();
                String password = inputpassword1.getText().toString();
                if (email.isEmpty()){
                    progressDialog.dismiss();
                    inputemail1.setError("please enter your email");
                    inputemail1.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    progressDialog.dismiss();
                    inputpassword1.setError("please enter your password");
                    inputpassword1.requestFocus();
                    return;
                }
                if (email.isEmpty() && password.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, "please enter a valid detail", Toast.LENGTH_LONG);
                    return;
                }else if (!(email.isEmpty()) && !password.isEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, "login successful", Toast.LENGTH_LONG).show();
                                Intent login = new Intent(Register.this, Login.class);
                                startActivity(login);

                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, "sign up unsuccessful please try again", Toast.LENGTH_LONG).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: "+e.getMessage());
                        }
                    });
                }





            }
        });
    }
}