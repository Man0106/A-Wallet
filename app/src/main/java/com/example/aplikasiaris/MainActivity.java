package com.example.aplikasiaris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText mEmail, mAmount;
    Button mSend;
    FirebaseFirestore mDatabase;
    String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mEmail  = findViewById( R.id.email );
        mAmount = findViewById( R.id.Amount );

        mSend   = findViewById( R.id.BtnSend );
        mDatabase = FirebaseFirestore.getInstance();
        mSend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmailReceiver     = mEmail.getText().toString();
                String AmountReceived   = mAmount.getText().toString();

                Map<String, Object> transaction = new HashMap<>();
                transaction.put( "Name ", EmailReceiver );
                transaction.put( "Amount ", AmountReceived );

                mDatabase.collection( "transaction" ).add( transaction )
                        .addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d( TAG, "Added with ID : "+ documentReference.getId() );
                            }
                        } )
                        .addOnFailureListener( new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d( TAG,"Error adding document", e );
                            }
                        } );
            }
        } );
    }
}
