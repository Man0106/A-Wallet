package com.example.aplikasiaris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TransaksiActivity extends AppCompatActivity {
    RecyclerView mTransaksiList;
    FirebaseFirestore mDatabase;
    TransaksiAdapter transaksiAdapter;
    List<Pengguna> Plist;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_transaksi );
    }
}
