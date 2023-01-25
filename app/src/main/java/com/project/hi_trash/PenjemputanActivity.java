package com.project.hi_trash;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PenjemputanActivity extends AppCompatActivity {

    Button IconBack, BTNselesai;
    EditText ETtanggal, ETalamat, ETjenis;
    String alamat, jenis, tanggal;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjemputan);

        IconBack = findViewById(R.id.IconBackPenjemputan);
        ETtanggal = findViewById(R.id.ETtanggal);
        ETalamat = findViewById(R.id.ETalamat);
        ETjenis = findViewById(R.id.ETjenis);
        BTNselesai = findViewById(R.id.BTNselesai);
        progressDialog = new ProgressDialog(this);

        ETtanggal.setOnClickListener(view -> {
            Calendar tanggalJemput = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
                tanggalJemput.set(Calendar.YEAR, year);
                tanggalJemput.set(Calendar.MONTH, monthOfYear);
                tanggalJemput.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String strFormatDefault = "d MMMM yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormatDefault, Locale.getDefault());
                ETtanggal.setText(simpleDateFormat.format(tanggalJemput.getTime()));
            };

            new DatePickerDialog(PenjemputanActivity.this, date,
                    tanggalJemput.get(Calendar.YEAR),
                    tanggalJemput.get(Calendar.MONTH),
                    tanggalJemput.get(Calendar.DAY_OF_MONTH)).show();
            });
        IconBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });

        BTNselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alamat = ETalamat.getText().toString();
                jenis = ETjenis.getText().toString();
                tanggal = ETtanggal.getText().toString();

                validasiData();
            }
        });

    }

    void validasiData(){
        if (!alamat.isEmpty() && !jenis.isEmpty() && !tanggal.isEmpty()){
            kirimData();
        }else {
            ETalamat.setError("Masukkan alamat penjemputan!");
            ETjenis.setError("Masukkan jenis sampah!");
            ETtanggal.setError("Masukkan tanggal!");
        }
    }

    void kirimData(){
        progressDialog.setMessage("Mengirim...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        AndroidNetworking.post("https://tkjbpnup.com/kelompok_4/api_trashpickup/addPenjemputan.php")
                .addBodyParameter("Alamat",""+alamat)
                .addBodyParameter("Jenis_Sampah",""+jenis)
                .addBodyParameter("Jadwal_Penjemputan",""+tanggal)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("cekTambah",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan   = response.getString("result");
                            Toast.makeText(PenjemputanActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(PenjemputanActivity.this)
                                        .setMessage("Laporan dikirim!")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(PenjemputanActivity.this, HomeActivity.class);
                                                startActivity(i);
                                            }
                                        })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(PenjemputanActivity.this)
                                        .setMessage("Gagal Menambahkan Data !")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(PenjemputanActivity.this, HomeActivity.class);
                                                startActivity(i);
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                    });

        }
}
