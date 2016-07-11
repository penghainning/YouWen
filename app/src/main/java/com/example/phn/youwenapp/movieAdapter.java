package com.example.phn.youwenapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by PHN on 2016/7/9.
 */
public class movieAdapter extends BaseAdapter {

    private LinkedList<Lesson_data> mData;
    private Context mContext;
    private ViewHolder holder = null;
    public movieAdapter(LinkedList<Lesson_data>mData, Context mContext)
    {
        this.mContext=mContext;
        this.mData=mData;
    }
    public int getCount() {
        return mData.size();
    }
    public Lesson_data getItem(int position) {
        return mData.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.moviedata_layout, parent, false);
            holder = new ViewHolder();
            holder.movie_number = (TextView) convertView.findViewById(R.id.movie_number);
            holder.movie_title = (TextView) convertView.findViewById(R.id.movie_title);
            holder.movie_visit = (TextView) convertView.findViewById(R.id.movie_visit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
       /* if(position==1||position==2)
            holder.movie_number.setBackgroundResource(R.drawable.movie_number);*/
       holder.movie_number.setText(mData.get(position).getNumber());
        holder.movie_title.setText(mData.get(position).getTitle());
        holder.movie_visit.setText(mData.get(position).getVisit());


        return convertView;

    }
    static class ViewHolder{
        TextView movie_number;
        TextView movie_title;
        TextView movie_visit;
    }


}
