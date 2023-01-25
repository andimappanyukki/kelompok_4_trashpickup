package com.project.hi_trash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class EdukasiActivity extends AppCompatActivity {
    ImageButton imgbtnorganik, imgbtnanorganik;
    Button IconBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edukasi);
        imgbtnorganik = (ImageButton) findViewById(R.id.imgbtnorganik);
        imgbtnanorganik = (ImageButton) findViewById(R.id.imgbtnanorganik);

        imgbtnorganik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),SampahOrganik.class));
            }
        });imgbtnanorganik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),SampahAnorganik.class));
            }
        });

        IconBack = findViewById(R.id.IconBackEdukasi);

        IconBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });
    }
}
