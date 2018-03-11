package com.agungdh.appepe;

import android.util.Log;

import java.lang.reflect.Array;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by a on 10/03/18.
 */

public class ApiClient {
    public String apiAmbilDataGet(String url, Object data) {
        String respond = null;
        Log.d("OKHTTP", "Melakukan panggilan GET");

        OkHttpClient client = new OkHttpClient();
        Log.d("OKHTTP", "client okhttp terbentuk");

        Request permohonan = new Request.Builder()
                .url(url)
                .build();
        Log.d("OKHTTP", "membuat permohonan selesai");

        try {
            Response respon = client.newCall(permohonan).execute();
            Log.d("OKHTTP", "mengambil respon");
            respond = respon.body().string();
        } catch (Exception e) {
            Log.d("OKHTTP", "ada error gan... dibwah");
            e.printStackTrace();
        }

        return respond;
    }

    public String apiAmbilDataPost(String url, RequestBody body) {
        String respond = null;
        Log.d("OKHTTP", "Melakukan panggilan POST");

        OkHttpClient client = new OkHttpClient();
        Log.d("OKHTTP", "client okhttp terbentuk");

        Request permohonan = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Log.d("OKHTTP", "membuat permohonan selesai");

        try {
            Log.d("OKHTTP", "init try");
            Response respon = client.newCall(permohonan).execute();
            Log.d("OKHTTP", "mengambil respon");
            respond = respon.body().string();
            Log.d("OKHTTP", "set data respon");
        } catch (Exception e) {
            Log.d("OKHTTP", "ada error gan... dibwah");
            Log.d("OKHTTP", "error = " + e.getMessage());
            e.printStackTrace();
        }

        return respond;
    }

}
