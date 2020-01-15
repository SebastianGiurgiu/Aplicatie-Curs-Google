package com.example.myappgoogle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AlimentList extends ArrayAdapter<Aliment> {

    private Activity context;
    private List<Aliment> alimentList;

    public AlimentList(Activity context, List<Aliment> alimentList){
        super(context,R.layout.list_layout,alimentList);
        this.context = context;
        this.alimentList = alimentList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewProteine = listViewItem.findViewById(R.id.textViewProteine);
        TextView textViewCarbohidrati = listViewItem.findViewById(R.id.textViewCarbohidrati);
        TextView textViewGrasimi = listViewItem.findViewById(R.id.textViewGrasimi);

        Aliment aliment = alimentList.get(position);

        textViewName.setText(aliment.getName());
        textViewProteine.setText(aliment.getProteine());
        textViewCarbohidrati.setText(aliment.getCarbohidrati());
        textViewGrasimi.setText(aliment.getGrasimi());

        return listViewItem;

    }
}
