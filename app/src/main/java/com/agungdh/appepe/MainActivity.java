package com.agungdh.appepe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    ApiClient ApiClient = new ApiClient();
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String alamat = "https://apps.agungdh.com/api_mahasiswa/";

                        try {
                            String respon = ApiClient.apiAmbilDataGet(alamat);
                            Log.d("OKHTTP", "Respon = " + respon);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

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



}
