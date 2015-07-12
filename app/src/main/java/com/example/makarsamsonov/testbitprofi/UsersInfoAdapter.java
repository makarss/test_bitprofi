package com.example.makarsamsonov.testbitprofi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by makarsamsonov on 09.07.15.
 */
public class UsersInfoAdapter extends ArrayAdapter {

    private Context context;
    private View.OnClickListener mOnClickListener;

    public UsersInfoAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        mOnClickListener = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        UserInfo item = (UserInfo)getItem(position);
        View view = convertView;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();

            holder.id = (TextView) view.findViewById(R.id.userId);
            holder.name = (TextView) view.findViewById(R.id.userName);
            holder.photo = (ImageView) view.findViewById(R.id.userPhoto);

            view.setOnClickListener(mOnClickListener);

            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.id.setText(item.getId());
        holder.name.setText(item.getName());
        new DownloadImageTask((ImageView)view.findViewById(R.id.userPhoto)).execute(item.getPhotoUrl());

        return view;
    }

    static class ViewHolder{
        TextView name;
        TextView id;
        ImageView photo;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
