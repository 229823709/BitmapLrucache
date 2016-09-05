package com.example.king.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by king on 2016/8/31.
 */
public class BitmapTools {
    public static Bitmap decodeBitmap(Resources resources, int resId, int reqWith, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();//设置解码的参数
        options.inJustDecodeBounds = true;//避免解码时申请内存空间
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = bili(options, reqHeight, reqWith);// 设置采样率
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    //设置图片比例
    public static int bili(BitmapFactory.Options options, int reqWith, int height) {

        int height1 = options.outHeight;// options.outWidth和options.Height 获取bitmap的原始高和宽
        int width = options.outWidth;
        int insi = 1;
        if (height1 > height || width > reqWith) {
            final int a = Math.round((float) height1 / (float) height);
            final int b = Math.round((float) width / (float) reqWith);
            insi = a < b ? a : b;
        }
        return insi;
    }
}
