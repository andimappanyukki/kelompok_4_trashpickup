package com.project.hi_trash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {

    Button IconBack, ubah, LogOut, info;
    SessionManager sessionManager;
    TextView TVnama, TValamat, TVemail, TVtelpon, hynama;
    String nama,alamat,email,telpon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        IconBack = findViewById(R.id.IconBackProfil);
        LogOut = findViewById(R.id.LogOut);
        ubah = findViewById(R.id.ubah);
        info = findViewById(R.id.info);
        TVnama = findViewById(R.id.TVnama);
        TValamat = findViewById(R.id.TValamat);
        TVemail = findViewById(R.id.TVemail);
        TVtelpon = findViewById(R.id.TVtelpon);
        hynama = findViewById(R.id.hynama);
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        nama = user.get(SessionManager.KEY_NAMALENGKAP);
        alamat = user.get(SessionManager.KEY_ALAMAT);
        email = user.get(SessionManager.KEY_EMAIL);
        telpon = user.get(SessionManager.KEY_NOMOR);

        TVnama.setText(nama);
        TValamat.setText(alamat);
        TVemail.setText(email);
        TVtelpon.setText(telpon);

        hynama.setText("HAI "+nama+"!");


        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this,edit_user.class));
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this,InfoActivity.class));
            }
        });

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logOut();
                Toast.makeText(ProfilActivity.this, "Berhasil Logout", Toast.LENGTH_SHORT).show();
            }
        });
        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this,edit_user.class));
            }
        });

        IconBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });



    }
}
