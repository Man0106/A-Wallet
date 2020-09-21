package com.example.aplikasiaris;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuySellFragment extends Fragment {
    Button Advertise;
    RecyclerView mUserslist;
    BuySellAdapter buySellAdapter;
    List<Users> usersList;
    String TAG ="BuySellFragment";
    FirebaseAuth mAuth;
    FirebaseFirestore mDatabase;

    public BuySellFragment() {
        // Required empty public constructor

    }


    @Override
    public void onStart() {
        super.onStart();

        mDatabase = FirebaseFirestore.getInstance();
        if (usersList.size() > 0){
            usersList.clear();
        }

        mDatabase.collection( "Advertise" ).addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "error : " + e.getMessage());
                    return;
                }

                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                    if (doc.getType() == DocumentChange.Type.ADDED  ) {
                        String UsersId = doc.getDocument().getId();
                        Users users = doc.getDocument().toObject(Users.class).withId(UsersId);
                        usersList.add(users);

                        buySellAdapter.notifyDataSetChanged();
                    }
                }
            }
        } );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_buy_sell, container, false );
        mAuth = FirebaseAuth.getInstance();
        usersList = new ArrayList<>(  );
        buySellAdapter = new BuySellAdapter( getActivity(), usersList );
        mUserslist = view.findViewById(R.id.buysell_list);
        mUserslist.setHasFixedSize(true);
        mUserslist.setLayoutManager( new LinearLayoutManager( container.getContext() ) );
        mUserslist.setAdapter( buySellAdapter );

        Advertise = view.findViewById(R.id.createadvertise);
        Advertise.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity() , AdvertiseActivity.class );
                startActivity( i );
            }
        } );
        return view;
    }



}
