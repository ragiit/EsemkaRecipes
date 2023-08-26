package com.example.esemkarecipes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.esemkarecipes.models.ResponseModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class _Helper {

    private static String baseURL = "http://10.0.2.2:5000/api/";
    //    private static String baseURL = "http://10.0.2.2:5000/api/";
    private static String baseImageURL = baseURL.replace("api/", "") + "images/";

    public static void httpGetImage(Context context, String url, ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap1 = BitmapFactory.decodeStream(new URL(baseImageURL + url).openStream());
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap1);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

    public static ResponseModel httpHelper(String... values) {
        try {
            return new _HttpHelper().execute(values).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class _HttpHelper extends AsyncTask<String, Void, ResponseModel> {
        @Override
        protected ResponseModel doInBackground(String... strings) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(baseURL + strings[0]).openConnection();
                conn.setRequestProperty("Content-Type", "application/json");

                if (strings.length > 1) {
                    conn.setDoOutput(true);
                    OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
                    os.write(strings[1]);
                    os.flush();
                    os.close();
                }

                int code = conn.getResponseCode();
                if (code == 200 || code == 201) {
                    return new ResponseModel(code, new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine());
                } else {
                    return new ResponseModel(code, conn.getResponseMessage());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
