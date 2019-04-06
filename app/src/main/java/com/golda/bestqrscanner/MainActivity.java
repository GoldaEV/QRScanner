package com.golda.bestqrscanner;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.golda.bestqrscanner.ScannerActivity.EXTRA_QRSTRING;

public class MainActivity extends AppCompatActivity {
    private Button start;
    private TextView link;
    private String result;
    private AdView mAdView;
    static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ads
        MobileAds.initialize(this, "ca-app-pub-3602084545548553~3024646952");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
                if (result != null) {
                    extractAndOpenUrls(result);
                }
            }
        });
        link.setActivated(false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                result = data.getStringExtra(EXTRA_QRSTRING);
                link.setText(result);
                link.setActivated(true);
            }

        }

    }


    private void startScanner() {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void extractAndOpenUrls(String text) {
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern urlPattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = urlPattern.matcher(text);

        if (urlMatcher.find()) {
            String url = text.substring(urlMatcher.start(0), urlMatcher.end(0));
            Intent urlIntent = new Intent(Intent.ACTION_VIEW);
            urlIntent.setData(Uri.parse(url));
            startActivity(urlIntent);
        }
    }

}
