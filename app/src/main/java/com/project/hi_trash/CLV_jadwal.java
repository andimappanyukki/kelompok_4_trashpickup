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

public class CLV_jadwal extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> vnama;
    private ArrayList<String> vtanggal;
    private ArrayList<String> valamat;



    public CLV_jadwal (Activity context , ArrayList<String> nama, ArrayList<String> tanggal, ArrayList<String> alamat){
        super(context, R.layout.list_item_jadwal, nama);
        this.context = context;
        this.vnama = nama;
        this.vtanggal = tanggal;
        this.valamat = alamat;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_item_jadwal, null, true);

        //Declarasi komponen
        TextView nama       = rowView.findViewById(R.id.idTVnama);
        TextView tanggal       = rowView.findViewById(R.id.idTVtanggal);
        TextView alamat     = rowView.findViewById(R.id.idTValamat);

        //Set Parameter Value sesuai widget textview
        nama.setText(vnama.get(position));
        tanggal.setText(vtanggal.get(position));
        alamat.setText(valamat.get(position));


        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }
}
