package com.agungdh.appepe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MenuUtama extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;

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
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            tFoto.setImageBitmap(bitmap);
        }

        try {
            if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
                Uri uVideo = data.getData();
                tVidio.setMediaController(new MediaController(this));
                tVidio.setVideoURI(uVideo);
                Log.d("INFO BRO", "Lokasi Video = " + uVideo.getPath());
//                tVidio.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }

    }
}
