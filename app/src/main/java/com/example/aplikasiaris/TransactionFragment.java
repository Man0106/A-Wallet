package com.example.aplikasiaris;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {
    EditText mEmail, mAmount;
    Button Send;
    FirebaseFirestore mDatabase;
    FirebaseAuth mAuth;

    public TransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_transaction, container, false );

        mDatabase = FirebaseFirestore.getInstance();
        mAuth   = FirebaseAuth.getInstance();

        mEmail = view.findViewById(R.id.edtemail);
        mAmount = view.findViewById(R.id.edtamount);
        Send = view.findViewById(R.id.Send);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String Amount = mAmount.getText().toString();
                final double intAmount = Double.parseDouble(Amount);
                final String fromuser = mAuth.getUid();
                final String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

                final Query UID = mDatabase.collection("Users").whereEqualTo("Email", email);
//                TODO: Start

                UID.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            final String ID = doc.getId();

                            mDatabase.collection("Users").document(ID).collection("Wallet").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(final QuerySnapshot queryDocumentSnapshots) {
                                            for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                                                final String WalletID = doc.getId();
                                                String WalletTotal = doc.get("Total").toString();
                                                final double intWalletTotal = Double.parseDouble(WalletTotal);

                                                mDatabase.collection("Users").document(fromuser).collection("Wallet").get()
                                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                                                                    final String IDWalletFromUser = documentSnapshot.getId();
                                                                    String WalletFromUser = documentSnapshot.get("Total").toString();
                                                                    final double intWalletFromUser = Double.parseDouble(WalletFromUser);

                                                                    if (intWalletFromUser > intAmount){
                                                                        Map<String, Object> TR = new HashMap<>();
                                                                        TR.put("From", fromuser);
                                                                        TR.put("Amount", Amount);
                                                                        TR.put("Date_Time", currentDateTimeString);
                                                                        mDatabase.collection("Users").document(ID).collection("Wallet_History").add(TR)
                                                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                    @Override
                                                                                    public void onSuccess(DocumentReference documentReference) {
                                                                                        Map<String, Object> wlt = new HashMap<>();

                                                                                        double Total = intWalletTotal + intAmount;
                                                                                        String strtotal = String.valueOf(Total);
                                                                                        wlt.put("Total", strtotal);
                                                                                        mDatabase.collection("Users").document(ID).collection("Wallet").document(WalletID)
                                                                                                .update(wlt).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()){
                                                                                                    double NowTotal = intWalletFromUser - intAmount;
                                                                                                    String strNowTotal = String.valueOf(NowTotal);
                                                                                                    Map<String, Object> updFromUser = new HashMap<>();
                                                                                                    updFromUser.put("Total", strNowTotal);
                                                                                                    mDatabase.collection("Users").document(fromuser)
                                                                                                            .collection("Wallet").document(IDWalletFromUser).update(updFromUser)
                                                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onSuccess(Void aVoid) {
                                                                                                                    Toast.makeText(getActivity(), "Berhasil terkirim ke wallet "+email, Toast.LENGTH_LONG).show();
                                                                                                                }
                                                                                                            });
                                                                                                } else {
                                                                                                    Toast.makeText(getActivity(), "Error "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });
                                                                    } else {
                                                                        Toast.makeText(getActivity(), "Your Wallet balance is not enough!", Toast.LENGTH_LONG).show();
                                                                    }





                                                                }
                                                            }
                                                        });


                                            }
                                        }
                                    });


                        }
                    }
                });
//                TODO: END

            }
        });

        return view;
    }

}
