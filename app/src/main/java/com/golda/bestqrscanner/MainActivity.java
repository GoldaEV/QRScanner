package com.golda.bestqrscanner;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.golda.bestqrscanner.ScannerActivity.EXTRA_QRSTRING;

public class MainActivity extends AppCompatActivity {
    private Button start;
    private TextView link;
    private String result;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ads
        MobileAds.initialize(this, "ca-app-pub-3602084545548553~3024646952");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        setContentView(R.layout.activity_logo);

        start = findViewById(R.id.start);
        link = findViewById(R.id.link);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanner();
            }
        });


        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        result = data.getStringExtra(EXTRA_QRSTRING);
        link.setText(result);

    }


    private void startScanner() {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivityForResult(intent, 1);
    }


}
