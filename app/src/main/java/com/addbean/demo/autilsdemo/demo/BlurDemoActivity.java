package com.addbean.demo.autilsdemo.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.addbean.autils.ndk.NativeJni;
import com.addbean.demo.autilsdemo.R;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class BlurDemoActivity extends ActionBarActivity {
    NativeJni nativeJni;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_demo);
        TextView tv1 = (TextView) findViewById(R.id.tv_1);
        TextView tv2 = (TextView) findViewById(R.id.tv_2);
        nativeJni = new NativeJni(this);
        tv1.setText(nativeJni.getString("I AM FROM NDK!"));
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 3, 2, 4, 4};
        int[] array = nativeJni.blur(a, 4, 4, 1);
        String s = "";
        for (int i = 0; i < array.length; i++) {
            s = s + array[i];
        }
        tv1.setText(nativeJni.getString(s));
        img = (ImageView) findViewById(R.id.image);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAsyTask();
            }
        });
    }

    int width;
    int heigh;

    private void startAsyTask() {

        AsyncTask<Void, Void, Bitmap> handleBlur = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_launcher);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                width = bitmap.getWidth();
                heigh = bitmap.getHeight();
                int[] temp = new int[byteArray.length];
                for (int i = 0; i < byteArray.length; i++) {
                    temp[i] = (int) byteArray[i];
                }
                int[] array = nativeJni.blur(temp, 40, 40, 10);

                for (int i = 0; i < byteArray.length; i++) {
                    byteArray[i] = (byte) array [i];
                }
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap bmp) {
                super.onPostExecute(bmp);
                img.setImageBitmap(bmp);
//                bmp.recycle();
            }
        };
        handleBlur.execute();
    }

}
