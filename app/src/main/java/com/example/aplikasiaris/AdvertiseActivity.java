package com.example.aplikasiaris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdvertiseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView Cate;
    EditText mName, mAmount, mPrice, mDesc;
    Spinner mCat;
    Button save;
    FirebaseFirestore mDatabase;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_advertise );
        Cate = findViewById(R.id.textView);

        mName       = findViewById( R.id.edtname );
        mAmount     = findViewById( R.id.edtamount );
        mPrice      = findViewById( R.id.edtprice );
        mDesc       = findViewById( R.id.edtdescription );

        mCat        = findViewById( R.id.spcat );

        save        = findViewById( R.id.advertisesave );

        mDatabase   = FirebaseFirestore.getInstance();
        mAuth       = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCat.setAdapter(adapter);
        mCat.setOnItemSelectedListener( this );

        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name     = mName.getText().toString();
                String amount   = mAmount.getText().toString();
                String price    = mPrice.getText().toString();
                String Desc     = mDesc.getText().toString();
                String Category = Cate.getText().toString();

                Map<String, Object> Advertise = new HashMap<>();
                Advertise.put("Name", name);
                Advertise.put("Amount", amount);
                Advertise.put("Price", price);
                Advertise.put("Description", Desc);
                Advertise.put("Category", Category);

                mDatabase.collection("Advertise").add(Advertise).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AdvertiseActivity.this, "Berhasil", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(AdvertiseActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            }
        } );
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String Cat = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), Cat, Toast.LENGTH_LONG).show();
        Cate.setText(Cat);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
