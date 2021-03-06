package com.example.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.Database.Entities.EntityBase;
import com.example.Database.Entities.LoginEntity;

import java.util.Vector;

/**
 * Created by omur on 08.05.2016.
 */
public class LoginAdapter extends BaseAdapter {

    private Context context;
    private Vector<EntityBase> model;

    public LoginAdapter(Context context, Vector<EntityBase> model)
    {
        this.context = context;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((LoginEntity) getItem(position)).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LoginEntity le = (LoginEntity) getItem(position);

        TextView tID = new TextView(context);
        tID.setHeight(90);
        tID.setText(String.valueOf(le.getID()));

        return tID;
    }
}
