package com.project.hi_trash;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button BTNmasuk, BTNbuat;
    EditText ETnama, ETpassword;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    String email, password;
//    int id_user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BTNbuat = findViewById(R.id.buat);
        BTNmasuk = findViewById(R.id.masuk);
        ETnama = findViewById(R.id.nama);
        ETpassword = findViewById(R.id.password);

        progressDialog = new ProgressDialog(this);

        sessionManager = new SessionManager(getApplicationContext());



        BTNbuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DaftarActivity.class));
            }
        });

        BTNmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  //              Toast.makeText(getApplicationContext(),"Berhasil Login",Toast.LENGTH_LONG).show();
 //               startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                progressDialog.setMessage("Login..");
                progressDialog.setCancelable(false);
                progressDialog.show();

                email = ETnama.getText().toString();
                password = ETpassword.getText().toString();


                validasiData();
            }
        });

    }

    void validasiData(){
        if(email.equals("") || password.equals("")){
            Toast.makeText(LoginActivity.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            cekLogin();
        }
    }

    void cekLogin(){
        AndroidNetworking.post("https://tkjbpnup.com/kelompok_4/api_trashpickup/cekLogin2.php")
                .addBodyParameter("email",""+email)
                .addBodyParameter("password",""+password)
                .setPriority(Priority.MEDIUM)
                .setTag("Cek Login")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("Cek Login",""+response);

                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan   = response.getString("result");
                            String id_user = response.getString("id_user");
                            String nama_lengkap = response.getString("nama_lengkap");
                            String email = response.getString("email");
                            String nama_pengguna = response.getString("nama_pengguna");
                            String alamat = response.getString("alamat");
                            String nomor_telpon = response.getString("nomor_telpon");
                            Toast.makeText(LoginActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                sessionManager.createSession_email(email);
                                sessionManager.createSession_namapengguna(nama_pengguna);
                                sessionManager.createSession_alamat(alamat);
                                sessionManager.createSession_namalengkap(nama_lengkap);
                                sessionManager.createSession_id(id_user);
                                sessionManager.createSession_nomor(nomor_telpon);

                                new AlertDialog.Builder(LoginActivity.this)
                                        .setMessage("Berhasil Login")
                                        .setCancelable(false)
                                        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                            else {
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setMessage("Gagal Melakukan Login !")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //dialog.cancel();
                                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorLogin",""+anError.getErrorBody());
                    }
                });

    }
}