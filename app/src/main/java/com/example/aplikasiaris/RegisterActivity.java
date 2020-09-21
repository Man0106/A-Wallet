package com.example.aplikasiaris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText mName, mEmail, mPass;
    TextView mLogin;
    Button mRegis;
    FirebaseAuth mAuth;
    FirebaseFirestore mDatabase;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        progressBar = findViewById( R.id.prog );

        mName = findViewById( R.id.nama );
        mEmail = findViewById( R.id.email );
        mPass = findViewById( R.id.password );

        mRegis = findViewById( R.id.btnRegis );
        mLogin = findViewById( R.id.adaakun );

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();

        mLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent L = new Intent( RegisterActivity.this, LoginActivity.class );
                startActivity( L );
                finish();
            }
        } );

        mRegis.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility( View.VISIBLE );
                final String Name = mName.getText().toString();
                final String Email = mEmail.getText().toString();
                final String Pass = mPass.getText().toString();

                mAuth.createUserWithEmailAndPassword( Email, Pass ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            final String user_id = mAuth.getCurrentUser().getUid();

                            Map<String, Object> user = new HashMap<>(  );
                            user.put( "Nama", Name );
                            user.put( "Email", Email );
                            user.put( "Password", Pass );

                            mDatabase.collection( "Users" ).document(user_id).set( user ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText( RegisterActivity.this, "Registrasi Berhasil, Silahkan Melakukan Login", Toast.LENGTH_LONG ).show();
                                    mAuth.signOut();
                                    Map<String, Object> wallet = new HashMap<>();
                                    wallet.put("Total", 0);
                                    mDatabase.collection("Users").document(user_id).collection("Wallet").add(wallet)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                                }
                                            });
                                    progressBar.setVisibility( View.INVISIBLE );
                                }
                            } );
                        } else{
                            Toast.makeText( RegisterActivity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_LONG ).show();
                            progressBar.setVisibility( View.INVISIBLE );
                        }
                    }
                } );
            }
        } );
    }
}
