package com.odishakrushi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.odishakrushi.R;

public class CustomAdapterAreaOfOperation extends BaseAdapter {
    Context context;
    String[] str_area_of_operation;
    LayoutInflater inflter;

    public CustomAdapterAreaOfOperation(Context applicationContext, String[] str_area_of_operation) {
        this.context = applicationContext;

        this.str_area_of_operation = str_area_of_operation;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return str_area_of_operation.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_area_of_operation, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(str_area_of_operation[i]);
        return view;
    }
}