package com.project.hi_trash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name = "session";
    private static final String is_login = "islogin";
    public static final String KEY_NAMALENGKAP = "keynamalengkap";
    public static final String KEY_EMAIL = "keyemail";
    public static final String KEY_ID = "keyid";
    public static final String KEY_NOMOR = "keynomor";
    public static final String KEY_NAMAPENGGUNA = "keyusername";
    public static final String KEY_ALAMAT = "keyalamat";



    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public void createSession_id(String id_user){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_ID, id_user);
        editor.apply();

    }


    public void createSession_email(String email){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_EMAIL, email);
        editor.apply();

    }

    public void createSession_namalengkap(String nama_lengkap){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_NAMALENGKAP, nama_lengkap);
        editor.commit();

    }

    public void createSession_alamat(String alamat){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_ALAMAT, alamat);
        editor.apply();

    }


    public void createSession_namapengguna(String nama_pengguna){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_NAMAPENGGUNA, nama_pengguna);
        editor.apply();

    }


    public void createSession_nomor(String nomor_telpon){
        editor.putBoolean(is_login, true);
        editor.putString(KEY_NOMOR, nomor_telpon);
        editor.apply();

    }


    public void logOut(){
        editor.clear();
        editor.apply();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_NOMOR, pref.getString(KEY_NOMOR, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_NAMALENGKAP, pref.getString(KEY_NAMALENGKAP, null));
        user.put(KEY_NAMAPENGGUNA, pref.getString(KEY_NAMAPENGGUNA, null));
        user.put(KEY_ALAMAT, pref.getString(KEY_ALAMAT, null));
        return user;
    }

    public boolean getSPSudahLogin(){
        return pref.getBoolean(is_login, false);
    }


}
