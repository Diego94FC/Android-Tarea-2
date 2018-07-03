package com.tareas.diego.tarea2v5;

import android.content.Context;
import android.drm.DrmStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Parsania Hardik on 03-Jan-17.
 */
public class customAdapter  extends BaseAdapter {

    private Context context;
    private ArrayList<ImageModel> imageModelArrayList;


    public customAdapter(Context context, ArrayList<ImageModel> imageModelArrayList) {

        this.context = context;
        this.imageModelArrayList = imageModelArrayList;

    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public String getItem(int position) {
        return imageModelArrayList.get(position).getName()                  +
                ";" + imageModelArrayList.get(position).getYear()           +
                ";" + imageModelArrayList.get(position).getType()           +
                ";" + imageModelArrayList.get(position).getImage_drawable() +
                ";" + imageModelArrayList.get(position).getId();
    }

    /*@Override
    public Object getItem(int position) {
        return imageModelArrayList.get(position);
    }*/

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);
            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.iv = (ImageView) convertView.findViewById(R.id.imgView);



            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(imageModelArrayList.get(position).getName());
        //holder.iv.setImageResource(imageModelArrayList.get(position).getImage_drawable());
        Picasso.get().load(imageModelArrayList.get(position).getImage_drawable()).into(holder.iv);


        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
        private ImageView iv;

    }

}