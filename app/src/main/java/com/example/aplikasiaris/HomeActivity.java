package com.example.aplikasiaris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    TextView mProfile, mBuySell, mTransaction;
    ViewPager mMainPager;

    PagerViewAdapter mPagerAdapter;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        mProfile            = findViewById( R.id.txtprofile );
        mBuySell            = findViewById( R.id.txtbuysell );
        mTransaction        = findViewById( R.id.txttrans );

        mMainPager = findViewById( R.id.mainpager );

        mAuth = FirebaseAuth.getInstance();

        mPagerAdapter = new PagerViewAdapter( getSupportFragmentManager() );
        mMainPager.setAdapter( mPagerAdapter );

        mProfile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem( 0 );
            }
        } );

        mBuySell.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem( 1 );
            }
        } );

        mTransaction.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem( 2 );
            }
        } );

        mMainPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                changeTabs(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        } );

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            sendToLogin();
        }
    }

    private void sendToLogin() {
        Intent L = new Intent( HomeActivity.this, LoginActivity.class );
        startActivity( L );
        finish();
    }

    private void changeTabs(int position) {
        if (position == 0){
            mProfile.setTextSize( 22 );

            mBuySell.setTextSize( 16 );

            mTransaction.setTextSize( 16 );
        }

        if (position == 1){
            mProfile.setTextSize( 16 );

            mBuySell.setTextSize( 22 );

            mTransaction.setTextSize( 16 );
        }

        if (position == 2){
            mProfile.setTextSize( 16 );

            mBuySell.setTextSize( 16 );

            mTransaction.setTextSize( 22 );
        }

    }
}
