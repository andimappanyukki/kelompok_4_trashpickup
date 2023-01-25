package com.project.hi_trash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    Button jemput, riwayat, jadwal, edukasi, profil;
    String nama_lengkap;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        jemput = (Button) findViewById(R.id.jemput);
        riwayat = (Button) findViewById(R.id.riwayat);
        jadwal = (Button) findViewById(R.id.jadwal);
        edukasi = (Button) findViewById(R.id.edukasi);
        profil = (Button) findViewById(R.id.profil);


        sessionManager =new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        nama_lengkap = user.get(SessionManager.KEY_NAMALENGKAP);

        jemput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),PenjemputanActivity.class));
            }
        });
        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),riwayatljemput.class));
            }
        });
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),jadwaljemput.class));
            }
        });
        edukasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),EdukasiActivity.class));
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),ProfilActivity.class));
            }
        });


    }
}
