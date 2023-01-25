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

public class DaftarActivity extends AppCompatActivity {


    Button BTNdaftar;
    EditText ETNamaLengkap, ETAlamat, ETemail, ETnomor, ETnamapengguna, ETPassword;
    String NamaLengkap, alamat, email ,nomor, namapengguna, Password;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        // deklarasikan variabel
        BTNdaftar = findViewById(R.id.BTNdaftar);
        ETNamaLengkap = findViewById(R.id.idETnamalengkap);
        ETAlamat = findViewById(R.id.idETalamat);
        ETemail = findViewById(R.id.idETemail);
        ETnomor = findViewById(R.id.idETNomor);
        ETnamapengguna = findViewById(R.id.idETnamapengguna);
        ETPassword = findViewById(R.id.idETpassword);


        // Memfungsikan Widget
        BTNdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                NamaLengkap = ETNamaLengkap.getText().toString();
                alamat = ETAlamat.getText().toString();
                email = ETemail.getText().toString();
                nomor = ETnomor.getText().toString();
                namapengguna = ETnamapengguna.getText().toString();
                Password = ETPassword.getText().toString();


                validasiData();

                // Toast.makeText(getApplicationContext(),"Berhasil Melakukan Register",Toast.LENGTH_LONG).show();
                // startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }

    void validasiData(){
        if(NamaLengkap.equals("") ||alamat.equals("") || email.equals("") || nomor.equals("") ||namapengguna.equals("") ||Password.equals("")){
            Toast.makeText(DaftarActivity.this, "Data Tidak Lengkap", Toast.LENGTH_SHORT).show();
        } else {
            kirimData();
        }
    }

    void kirimData(){
        AndroidNetworking.post("https://tkjbpnup.com/kelompok_4/api_trashpickup/register.php")
                .addBodyParameter("nama_lengkap",""+NamaLengkap)
                .addBodyParameter("alamat",""+alamat)
                .addBodyParameter("email",""+email)
                .addBodyParameter("nomor_telpon",""+nomor)
                .addBodyParameter("nama_pengguna",""+namapengguna)
                .addBodyParameter("password",""+Password)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("cekRegister",""+response);

                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan   = response.getString("result");
                            Toast.makeText(DaftarActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(DaftarActivity.this)
                                        .setMessage("Berhasil Register")
                                        .setCancelable(false)
                                        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(DaftarActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                            else {
                                new AlertDialog.Builder(DaftarActivity.this)
                                        .setMessage("Gagal Melakukan Register !")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(DaftarActivity.this, DaftarActivity.class);
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
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }
}