package com.example.aplikasiaris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class WalletActivity extends AppCompatActivity {
    TextView Balance;
    FirebaseAuth mAuth;
    FirebaseFirestore mDatabase;

    @Override
    protected void onStart() {
        super.onStart();
        String UserID = mAuth.getCurrentUser().getUid();

        mDatabase.collection("Users").document(UserID).collection("Wallet").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                        String Total = doc.get("Total").toString();
                        Balance.setText(Total);
                    }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_wallet );

        Balance = findViewById(R.id.balance);

        mDatabase = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


    }
}
