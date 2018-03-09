package com.agungdh.appepe;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by a on 09/03/18.
 */

public class GetDataHash extends AsyncTask<String,Void,String> {

    PasswordHash ph;
    String text = "";

    public GetDataHash(PasswordHash ph){
       this.ph = ph;
    }

    public String doInBackground(String... a){
        try{
            String data = URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(a[1], "UTF-8");

            BufferedReader reader=null;

            // Defined URL  where to send data
            URL url = new URL(a[0]);
//            URL url = new URL("https://apps.agungdh.com/api_mahasiswa/");

            // Send POST data request

            URLConnection conn = url.openConnection();
                            conn.setDoOutput(true);
            conn.setConnectTimeout(50000);
                            OutputStreamWriter  wr = new OutputStreamWriter(conn.getOutputStream());
                            wr.write( data );
                            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            System.out.println("mencoba mengambil data");

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }

            System.out.println("Berhasil Mengambil data");


            text = sb.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
        return text;
    }

    protected void onPostExecute(String result) {
        ph.setPasswordHash(result);
    }
}
