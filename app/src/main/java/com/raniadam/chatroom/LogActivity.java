package com.raniadam.chatroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ComponentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LogActivity extends AppCompatActivity {
MaterialEditText memail,mpswrd;
    Button loginbtn;
ProgressBar progressBar;
    FirebaseAuth auth;
    TextView register,resetpswrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        pswrd = findViewById(R.id.pswrd);
        loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = pswrd.getText().toString();

                if(TextUtils.isEmpty(txt_email) ||(TextUtils.isEmpty(txt_password) )) {
                    Toast.makeText(LogActivity.this,"Il faut rempliir tous les champs",Toast.LENGTH_SHORT).show();
                }else{
                    auth.signInWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(LogActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LogActivity.this,"Verifier votre mot de passe ou Email",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
*/
        memail = findViewById(R.id.email);
        mpswrd = findViewById(R.id.pswrd);
        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        loginbtn = findViewById(R.id.loginbtn);
        register = findViewById(R.id.createText);
        resetpswrd = findViewById(R.id.pswrdText);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = memail.getText().toString().trim();
                String password = mpswrd.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    memail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mpswrd.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mpswrd.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // authenticate the user

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LogActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(LogActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });


        resetpswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText mail = new EditText(view.getContext());
                AlertDialog.Builder passwrdreset = new AlertDialog.Builder(view.getContext());
                passwrdreset.setTitle("Recuperer mot de passe");
                passwrdreset.setMessage("Entrer votre Email pour recuperer votre mot de passe");
                passwrdreset.setView(mail);
                passwrdreset.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email = mail.getText().toString();
                        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LogActivity.this, "Email envoyé ", Toast.LENGTH_SHORT);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LogActivity.this, "Email non envoyé " + e.getMessage(), Toast.LENGTH_SHORT);

                            }
                        });
                    }
                });
                passwrdreset.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
            }
        });
    }

}
