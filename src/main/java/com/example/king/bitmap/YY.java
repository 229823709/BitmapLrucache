package com.example.king.bitmap;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by king on 2016/9/1.
 */
public class YY extends Activity {

    private ListView listView;
    private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        listView = (ListView) findViewById(R.id.listView);
        new NewsAsyncTask().execute(URL);
    }

    /***
     * 将url对应的JSON格式数据转化为我们所封装的NewsBean对象
     * @param url
     * @return
     */
    private List<NewsBean> getJsonData(String url) {
        List<NewsBean> newsBeenList = new ArrayList<>();
        try {
            //和url.openConnection().getInputStream()相同，可根据URL直接联网获取网络数据，
            // 简单粗暴！返回值类型为InputStream
            String jsonString = readStream(new URL(url).openStream());
            Log.d("gy",jsonString);
            JSONObject jsonObject;
            NewsBean newsBean;
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                newsBean = new NewsBean();
                newsBean.ImageUrl = jsonObject.getString("picSmall");
                newsBean.title = jsonObject.getString("name");
                newsBean.content= jsonObject.getString("description");
                newsBeenList.add(newsBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsBeenList;
    }


    //用于读取数据
    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    //实现网络的异步访问
    class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

        @Override
        protected List<NewsBean> doInBackground(String... params) {

            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeenList) {
            super.onPostExecute(newsBeenList);
            MyAdapter adapter=new MyAdapter(YY.this,newsBeenList,listView);
            listView.setAdapter(adapter);
        }
    }
}
