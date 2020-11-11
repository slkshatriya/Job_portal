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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    TextView textsignup;
    FirebaseAuth firebaseAuth;
    ImageView loginbtn;
    EditText inputemail, inputpassword;
    ProgressDialog progressDialog;
    FirebaseAuth.AuthStateListener authStateListener;
    private static final String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        textsignup = findViewById(R.id.textsignup);
        loginbtn = findViewById(R.id.loginbtn);
        inputemail = findViewById(R.id.inputemail);
        inputpassword = findViewById(R.id.inputpassword);
        progressDialog = new ProgressDialog(this);


        textsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent login = new Intent(Login.this, MainActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login);
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please wait");
                progressDialog.show();
                String email = inputemail.getText().toString().trim();
                String password = inputpassword.getText().toString().trim();
                Log.d(TAG, "onClick: ");
                if (email.isEmpty()){
                    progressDialog.dismiss();
                    inputemail.setError("please enter your email");
                    inputemail.requestFocus();
                }
                else if (password.isEmpty()){
                    progressDialog.dismiss();
                    inputpassword.setError("please enter your password");
                    inputpassword.requestFocus();
                }
                else if (email.isEmpty() && password.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "please enter a valid detail", Toast.LENGTH_LONG);
                    return;
                }
                else if (!(email.isEmpty() && password.isEmpty())){
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, "login error try again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progressDialog.dismiss();
                                Intent inhome = new Intent(Login.this, MainActivity.class);
                                startActivity(inhome);
                            }

                        }
                    });

                }

                else {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "error getting trouble", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}