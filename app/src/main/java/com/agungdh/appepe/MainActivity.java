package com.agungdh.appepe;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity implements Message, PasswordHash {

    MainActivity activity;
    String passwordHash;
    int login = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keMenuUtama();
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
                GetDataHash a = new GetDataHash(activity);
                a.execute("http://10.42.0.1/api_appepe/login/hash", password);
                String json_password = passwordHash;

                String str = json_password;
                JSONObject json = new JSONObject(str);
                String param_password = json.getString("hash");

                GetData b = new GetData(activity);
                b.execute("http://10.42.0.1/api_appepe/login", username, param_password);

                keMenuUtama();
//                Toast.makeText(getApplicationContext(), param_password,Toast.LENGTH_LONG).show();
            }
        });
    }
    public void setMessage(String pesan){
        String str = pesan;
        String username = null;
        String password = null;
        String id_mahasiswa = null;
        String nama_mahasiswa = null;
        String nim_mahasiswa = null;
        try {
            JSONObject json = new JSONObject(str);
            username = json.getJSONObject("user").getString("username");
            password = json.getJSONObject("user").getString("password");
            id_mahasiswa = json.getJSONObject("mahasiswa").getString("id");
            nama_mahasiswa = json.getJSONObject("mahasiswa").getString("nama");
            nim_mahasiswa = json.getJSONObject("mahasiswa").getString("nim");
        } catch (JSONException e) {
            System.out.print(e.getMessage());
        }
        String pesanToast = "Username = " + username;
        pesanToast += "\nPassword = " + password;
        pesanToast += "\nID Mahasiswa = " + id_mahasiswa;
        pesanToast += "\nNama = " + nama_mahasiswa;
        pesanToast += "\nNIM= " + nim_mahasiswa;

        System.out.print(pesanToast);

//        Toast.makeText(getApplicationContext(), pesanToast,Toast.LENGTH_LONG).show();
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setLogin(int status) {
        this.login = 1;
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

}
