package com.agungdh.appepe;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    ApiClient ApiClient = new ApiClient();
    MainActivity activity;
    String flashData;
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
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //hash
                            String url= "http://10.42.0.1/api_appepe/login/hash";

                            RequestBody body = new FormBody.Builder()
                                    .add("password", password)
                                    .build();
                            Log.d("OKHTTP", "membuat form data");

                            String respon = ApiClient.apiAmbilDataPost(url, body);
                            Log.d("OKHTTP", "Respon = " + respon);

                            JSONObject json = new JSONObject(respon);
                            String hashPassword = json.getString("hash");
                            Log.d("OKHTTP", "Password ter hash = " + hashPassword);

                            //login
                            url = "http://10.42.0.1/api_appepe/login";

                            body = new FormBody.Builder()
                                    .add("username", username)
                                    .add("password", hashPassword)
                                    .build();
                            Log.d("OKHTTP", "membuat form data");

                            respon = ApiClient.apiAmbilDataPost(url, body);
                            Log.d("OKHTTP", "Respon = " + respon);

                            json = new JSONObject(respon);
                            int jsonlogin = json.getInt("login");
                            if (jsonlogin == 1) {
                                String jsonid = json.getJSONObject("user").getString("id");
                                String jsonusername = json.getJSONObject("user").getString("username");
                                String jsonlevel = json.getJSONObject("user").getString("level");
                                String jsonnama = json.getJSONObject("mahasiswa").getString("nama");
                                String jsonnim = json.getJSONObject("mahasiswa").getString("nim");
                                String jsonidmahasiswa = json.getJSONObject("mahasiswa").getString("id");

                                String dataLogin = "ID User = " + jsonid + "\n"
                                        + "Username = " + jsonusername + "\n"
                                        + "Level = " + jsonlevel + "\n"
                                        + "ID Mahasiswa = " + jsonidmahasiswa + "\n"
                                        + "Nama = " + jsonnama + "\n"
                                        + "NIM = " + jsonnim + "\n";


                                Log.d("OKHTTP", "Data Login = " + dataLogin);
                                login = 1;
                                keMenuUtama();
                            } else {
                                Log.d("OKHTTP", "Login Salah");
                            }

                        } catch (Exception e) {
                            Log.d("OKHTTP", "Ada Error");
                            Toast.makeText(getBaseContext(),"Iam inside thread",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    public void Alert(String pesan) {
        Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT).show();
    }

    public void AlertFlash() {
        if (this.flashData != null) {
            Toast.makeText(getApplicationContext(), this.flashData, Toast.LENGTH_SHORT).show();
        }
        this.flashData = null;
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
