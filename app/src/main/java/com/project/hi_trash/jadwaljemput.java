package com.project.hi_trash;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class jadwaljemput extends AppCompatActivity {

    SwipeRefreshLayout srl_main;
    ArrayList<String> array_nama,array_tanggal,array_alamat;
    ProgressDialog progressDialog;
    ListView listProses;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwaljemput);

        Toolbar toolbar = findViewById(R.id.idTBjadwal);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        listProses = findViewById(R.id.idLVjadwal);
        srl_main = findViewById(R.id.swipe_container);
        progressDialog = new ProgressDialog(this);

        srl_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollRefresh();
                srl_main.setRefreshing(false);
            }
        });
        // Scheme colors for animation
        srl_main.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)

            );

            scrollRefresh();
        }

        public void scrollRefresh(){
            progressDialog.setMessage("Mengambil Data.....");
            progressDialog.setCancelable(false);
            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getDataAc();
                }
            },2000);
        }

        void initializeArray(){
            array_nama = new ArrayList<String>();
            array_tanggal = new ArrayList<String>();
            array_alamat = new ArrayList<String>();


            array_nama.clear();
            array_tanggal.clear();
            array_alamat.clear();
        }

        public void getDataAc(){
            initializeArray();
            //URL API
            AndroidNetworking.get("https://tkjbpnup.com/kelompok_4/api_trashpickup/getData.php")
                    .setTag("Get Data")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();

                            try{
                                Boolean status = response.getBoolean("status");
                                if(status){
                                    JSONArray ja = response.getJSONArray("result");
                                    Log.d("respon",""+ja);
                                    for(int i = 0 ; i < ja.length() ; i++){
                                        JSONObject jo = ja.getJSONObject(i);

                                        array_nama.add(jo.getString("Nama"));
                                        array_tanggal.add(jo.getString("Tanggal_Penjemputan"));
                                        array_alamat.add(jo.getString("Alamat_Penjemputan"));
                                        //add this code
                                    }

                                    //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                    final CLV_jadwal adapter = new CLV_jadwal(jadwaljemput.this,array_nama,array_tanggal,array_alamat);

                                    //Set adapter to list
                                    listProses.setAdapter(adapter);


                                    //edit and delete
//                                    listProses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                            Log.d("TestKlik",""+array_nama.get(position));
//                                            Toast.makeText(jadwaljemput.this, array_nama.get(position), Toast.LENGTH_SHORT).show();
//
//                                            //Setelah proses koneksi keserver selesai, maka aplikasi akan berpindah class
//                                            //DataActivity.class dan membawa/mengirim data-data hasil query dari server.
//                                            Intent i = new Intent(jadwaljemput.this, DetailAc.class);
//                                            i.putExtra("namaAc",array_namaAc.get(position));
//                                            i.putExtra("descAc",array_deskripsiAc.get(position));
//                                            i.putExtra("hargaAc",array_hargaAc.get(position));
//                                            i.putExtra("fotoAc",array_fotoAc.get(position));
//                                            startActivity(i);
//                                        }
//                                    });


                                }else{
                                    Toast.makeText(jadwaljemput.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();

                                }

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                        }


                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        }

}