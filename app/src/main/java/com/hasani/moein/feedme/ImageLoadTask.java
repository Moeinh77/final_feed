package com.hasani.moein.feedme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Moein on 8/1/2017.
 */


    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private Bitmap bitmap;
        private String TAG="";

        public ImageLoadTask(String url) {
            this.url = url;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            Log.v(TAG,"bitmap set***********");
            bitmap=result;
        }

    }