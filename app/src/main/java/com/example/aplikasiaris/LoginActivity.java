package com.example.aplikasiaris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail;
    EditText mPass;

    Button mLogin;
    Button mRegister;

    FirebaseAuth mAuth;
    FirebaseFirestore mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        mEmail = findViewById( R.id.edtemail );
        mPass = findViewById( R.id.edtpass );

        mLogin = findViewById( R.id.btnLogin );
        mRegister = findViewById( R.id.btnRegister );

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();

        mRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent R = new Intent( LoginActivity.this, RegisterActivity.class );
                startActivity( R );
                finish();
            }
        } );

        mLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = mEmail.getText().toString();
                String Pass = mPass.getText().toString();

                mAuth.signInWithEmailAndPassword( Email, Pass ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            sendToHome();
                        }
                        else {
                            Toast.makeText( LoginActivity.this, "Errot : "+task.getException().getMessage(), Toast.LENGTH_LONG ).show();
                        }
                    }
                } );
            }
        } );
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            sendToHome();
        }
    }

    private void sendToHome() {
        Intent M = new Intent( LoginActivity.this, HomeActivity.class );
        startActivity( M );
        finish();
    }
}
