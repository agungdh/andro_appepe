package com.agungdh.appepe;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    MainActivity activity;
    String passwordHash;
    int login = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.activity = this;
        Button bt1 = (Button) findViewById(R.id.login);
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cekLogin(username.getText().toString(), password.getText().toString());
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            private void cekLogin(final String username, final String password) throws UnsupportedEncodingException, JSONException {
                apiAmbilData();

//                login = 1;
//                keMenuUtama();
            }
        });
    }

    public void keMenuUtama() {
        try {
            if (activity.login == 1) {
                Intent intent = new Intent(MainActivity.this, MenuUtama.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void apiAmbilData() {
        Log.d("OKHTTP", "Melakukan panggilan GET");
        String alamat = "https://apps.agungdh.com/api_mahasiswa/";

        OkHttpClient client = new OkHttpClient();
        Log.d("OKHTTP", "client okhttp terbentuk");

        Request permohonan = new Request.Builder()
                .url(alamat)
                .build();
        Log.d("OKHTTP", "membuat permohonan selesai");

        try {
            Response respon = client.newCall(permohonan).execute();
            Log.d("OKHTTP", "mengambil respon");
            Log.d("OKHTTP", respon.body().toString());
        } catch (IOException e) {
            Log.d("OKHTTP", "ada error gan... dibwah");
            e.printStackTrace();
        }
    }

}
