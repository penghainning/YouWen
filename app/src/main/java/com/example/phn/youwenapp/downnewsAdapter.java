package com.example.phn.youwenapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by PHN on 2016/7/9.
 */
public class downnewsAdapter extends BaseAdapter {

    private LinkedList<Lesson_data> mData;
    private Context mContext;
    private ViewHolder holder = null;
    public downnewsAdapter(LinkedList<Lesson_data>mData, Context mContext)
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.downnewslist_laout, parent, false);
            holder = new ViewHolder();
            holder.downnumber = (TextView) convertView.findViewById(R.id.down_number);
            holder.downtitle = (TextView) convertView.findViewById(R.id.down_title);
            holder.downvisit = (TextView) convertView.findViewById(R.id.down_visit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


           holder.downnumber.setText(mData.get(position).getNumber());
           holder.downtitle.setText(mData.get(position).getTitle());
          holder.downvisit.setText(mData.get(position).getVisit());



        return convertView;

    }
    static class ViewHolder{
        TextView downnumber;
        TextView downtitle;
        TextView downvisit;
    }


}
