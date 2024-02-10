package com.codeseasy.examples.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.core.content.ContextCompat;


public class AboutPage extends AppCompatActivity implements View.OnClickListener {

    Toolbar myToolbar;
    ImageView home_button;
    ImageView map_button;
    ImageView about_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        androidx.appcompat.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setTitle("NearbyHealth");
        myToolbar.setSubtitle("About Page");

        // Set the subtitle text color to white
        int white = ContextCompat.getColor(this, android.R.color.white);
        myToolbar.setSubtitleTextColor(white);

        getSupportActionBar().setTitle("NearbyHealth");

        TextView textViewLink = findViewById(R.id.textViewLinkk);
        textViewLink.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public void onClick(View v) {

    }
}