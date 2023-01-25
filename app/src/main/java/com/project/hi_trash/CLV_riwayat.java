package com.project.hi_trash;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CLV_riwayat extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> valamat;
    private ArrayList<String> vjenis;
    private ArrayList<String> vtanggal;



    public CLV_riwayat(Activity context , ArrayList<String> alamat, ArrayList<String> jenis, ArrayList<String> tanggal){
        super(context, R.layout.list_item_jadwal, alamat);
        this.context = context;
        this.valamat = alamat;
        this.vjenis = jenis;
        this.vtanggal = tanggal;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_item_jadwal, null, true);

        //Declarasi komponen
        TextView alamat       = rowView.findViewById(R.id.idTValamat);
        TextView jenis       = rowView.findViewById(R.id.idTVjenis);
        TextView tanggal     = rowView.findViewById(R.id.idTVtanggal);

        //Set Parameter Value sesuai widget textview
        alamat.setText(valamat.get(position));
        jenis.setText(vjenis.get(position));
        tanggal.setText(vtanggal.get(position));


        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }
}
