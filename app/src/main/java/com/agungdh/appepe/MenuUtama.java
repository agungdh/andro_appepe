package com.agungdh.appepe;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MenuUtama extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    ApiClient ApiClient = new ApiClient();
    Bitmap bitmap;
    Uri lokasiVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        Button foto = (Button) findViewById(R.id.foto);
        Button vidio = (Button) findViewById(R.id.vidio);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cFoto();
            }

            private void cFoto() {
                Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(myIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        vidio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cVidio();
            }

            private void cVidio() {
                Intent myIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(myIntent, REQUEST_VIDEO_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView tFoto = (ImageView) findViewById(R.id.tFoto);
        VideoView tVidio= (VideoView) findViewById(R.id.tVidio);

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //auk dah
            bitmap = (Bitmap) data.getExtras().get("data");
            tFoto.setImageBitmap(bitmap);
            upFoto();
        }

        try {
            if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
                Uri uVideo = data.getData();
                tVidio.setMediaController(new MediaController(this));
                tVidio.setVideoURI(uVideo);
                lokasiVideo = uVideo;
                Log.d("INFO BRO", "Lokasi Video = " + uVideo);
                Log.d("INFO BRO", "Lokasi Video = " + uVideo.getPath());
                upVideo();
//                tVidio.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }

    }

    public void upFoto() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.d("poto", "awal run");
        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(getApplicationContext(), bitmap);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        File finalFile = new File(getRealPathFromURI(tempUri));

        Log.d("poto", "tempuri: " + tempUri.toString());
        Log.d("poto", "lokasifile: " + finalFile.toString());

        String url= "http://10.42.0.1/api_appepe/test/test";

        doUploadFile(url, finalFile);
    }

    public void upVideo() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.d("poto", "awal run");

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        File finalFile = new File(getRealPathFromURI(lokasiVideo));

        Log.d("poto", "lokasifile: " + finalFile.toString());

        String url= "http://10.42.0.1/api_appepe/test/test";

        doUploadFile(url, finalFile);
    }

    public void doUploadFile(String url, File finalFile) {
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        int index = finalFile.getName().lastIndexOf('.')+1;
        String ext = finalFile.getName().substring(index).toLowerCase();
        String type = mime.getMimeTypeFromExtension(ext);

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", finalFile.getName(),
                        RequestBody.create(MediaType.parse(type), finalFile))
                .addFormDataPart("pesan", "ade agung")
                .build();
        Log.d("OKHTTP", "membuat form data." +
                "\nNama File = " + finalFile.getName() +
                "\nLokasi File = " + finalFile.getAbsoluteFile());


        String respon = ApiClient.apiAmbilDataPost(url, body);
        Log.d("OKHTTP", "Respon = " + respon);

        try {
            JSONObject json = new JSONObject(respon);
            String pesan = json.getString("pesan");
            Log.d("OKHTTP", "Pesan = " + pesan);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}
