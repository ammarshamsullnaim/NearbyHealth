package com.codeseasy.examples.loginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

public class Instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        androidx.appcompat.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setTitle("NearbyHealth");
        myToolbar.setSubtitle("Instruction Page");

        // Set the subtitle text color to white
        int white = ContextCompat.getColor(this, android.R.color.white);
        myToolbar.setSubtitleTextColor(white);

        getSupportActionBar().setTitle("NearbyHealth");
    }
}