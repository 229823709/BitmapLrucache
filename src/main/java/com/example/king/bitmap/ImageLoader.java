package com.example.king.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by king on 2016/9/1.
 */
public class ImageLoader {

    public LruCache<String, Bitmap> mlrucache=null;
    //获取可用的内存
    public final int memeroy = (int) Runtime.getRuntime().maxMemory();
    final int maxMemeroy = memeroy / 4;

    public  ImageLoader(){
        mlrucache=new LruCache<String,Bitmap>(maxMemeroy){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    };
    //把图片资源加入lrucache缓存
    public void addDateToCache(String url, Bitmap bitmap) {

        if (getoutBitmapFromLrucahce(url)==null){
            mlrucache.put(url,bitmap);
        }
    }

    //从lrucache中取出图片资源
    public Bitmap getoutBitmapFromLrucahce(String url) {
        return mlrucache.get(url);

    }

    /*
    * 使用线程加载图片，使用handler更新ui*/
    public void showImageByThread(String url, ImageView iamgeview) {
        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }).start();


    }
    public Bitmap getBitmapFromUrl(String path) {
        Bitmap bitmap;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            BufferedInputStream bu = new BufferedInputStream(in);
            bitmap = BitmapFactory.decodeStream(bu);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showImageFromAsyncTask(String url, ImageView imageView) {
        Bitmap bitmap =getoutBitmapFromLrucahce(url);
        if (bitmap==null){
            new Task(imageView, url).execute(url);
        }else {
            imageView.setImageBitmap(bitmap);
        }

    }

    class Task extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        String murl;
        public Task(ImageView imageView, String url) {
            this.imageView = imageView;
            this.murl = url;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (imageView.getTag().equals(murl)) {
                imageView.setImageBitmap(bitmap);
            }
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String url =params[0];
            Bitmap bitmap=getBitmapFromUrl(url);
            if (bitmap!=null){
                //如果获取到的图片不为空，就加入lrucache缓存
                addDateToCache(url,bitmap);
            }
            return bitmap;
        }
    }


}
