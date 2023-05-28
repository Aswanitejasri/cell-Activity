package com.example.t;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView textViewDetails = findViewById(R.id.textViewDetails);

        // Retrieve the phone state details from the intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String phoneStateDetails = extras.getString("phoneStateDetails");
            textViewDetails.setText(phoneStateDetails);
        }
    }
}
