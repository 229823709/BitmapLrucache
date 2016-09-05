package com.example.king.bitmap;

import android.content.Context;
import android.graphics.Path;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.nio.channels.Channels;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by king on 2016/9/1.
 */
public class MyAdapter extends BaseAdapter {

    private  ImageLoader imageLoader;
    private List<NewsBean> list;
    private ListView listView;

    private LayoutInflater inflater;

    public MyAdapter(Context context, List<NewsBean> list, ListView listView) {
        this.list = list;
        this.listView = listView;
        inflater = LayoutInflater.from(context);
        imageLoader=new ImageLoader();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler = new ViewHodler();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list, null);
            viewHodler.title = (TextView) convertView.findViewById(R.id.title);
            viewHodler.cotnent = (TextView) convertView.findViewById(R.id.content);
            viewHodler.imageView=(ImageView)convertView .findViewById(R.id.image);
            convertView.setTag(viewHodler);
        }
        else {
            viewHodler=(ViewHodler) convertView.getTag();
        }

        String url =list.get(position).ImageUrl;
        viewHodler.imageView.setTag(url);
        viewHodler.imageView.setImageResource(R.mipmap.ic_launcher);
        imageLoader.showImageFromAsyncTask(url, viewHodler.imageView);

        viewHodler.title.setText(list.get(position).title);
        viewHodler.cotnent.setText(list.get(position).content);

        return convertView;
    }

    class ViewHodler {
        public TextView title;
        public TextView cotnent;
        public ImageView imageView;
    }
}

