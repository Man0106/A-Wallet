package com.example.aplikasiaris;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    EditText mName, mPass, mNewpass, mConpass;
    TextView mEmail;
    Button ChangePassword, LogOut, CheckWallet;
    FirebaseAuth mAuth;
    FirebaseFirestore mDatabase;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            Toast.makeText(getActivity(), "Anda belum melakukan login", Toast.LENGTH_LONG).show();
            Intent l = new Intent(getActivity(), LoginActivity.class);
            startActivity(l);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_profile, container, false );


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();

        mPass       = view.findViewById(R.id.pass);
        mNewpass    = view.findViewById(R.id.newpass);
        mConpass    = view.findViewById(R.id.conpass);

        ChangePassword  = view.findViewById(R.id.ChangePassword);
        CheckWallet     = view.findViewById(R.id.CheckWallet);
        LogOut          = view.findViewById(R.id.LogOut);

        mName       = view.findViewById(R.id.txtnama);
        mEmail      = view.findViewById(R.id.txtemail);

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CheckWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent w = new Intent(getActivity(), WalletActivity.class);
                startActivity(w);
            }
        });

        final String userID = mAuth.getCurrentUser().getUid();
        mDatabase.collection("Users").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String name = documentSnapshot.get("Nama").toString();
                String email = documentSnapshot.get("Email").toString();
                mName.setText(name);
                mEmail.setText(email);
            }
        });

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String oldPass = mPass.getText().toString();
                final String newpass = mNewpass.getText().toString();
                final String conpass = mConpass.getText().toString();

                if (oldPass.isEmpty() | newpass.isEmpty() | conpass.isEmpty()){
                    Toast.makeText(getActivity(), "Password Field Is Empty!", Toast.LENGTH_LONG).show();
                }
                mDatabase.collection("Users").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String password = documentSnapshot.get("Password").toString();
                        String email = documentSnapshot.get("Email").toString();

                        if (oldPass.equals(password)){
                            if (newpass.equals(conpass)){
                                user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Map<String, Object> updatePassword = new HashMap<>();
                                            updatePassword.put("Password", newpass);
                                            mDatabase.collection("Users").document(userID).update(updatePassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getActivity(), "Password Update Successful", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(getActivity(), "Error "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });




                            } else {
                                Toast.makeText(getActivity(), "Your New Password is Different from the Confirmation Password", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Your Password Doesn't Match", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent out = new Intent(getActivity(), LoginActivity.class);
                startActivity(out);
            }
        });
        return view;
    }

}
