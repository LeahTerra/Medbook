////////////////////////////////////////////////////////////////////////
//
//  Name: Leah Sheptycki
//  Class: CMPUT 301 Section D01
//  SID: 1547619
//
////////////////////////////////////////////////////////////////////////

package com.example.medbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/*
        Custom Adapter was created based off of Nikhil Bansal's medium post at
            URL: https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd

        The purpose of this custom adapter is to provide multiple spots for information
        in each listed item.
 */

public class MedAdapter extends ArrayAdapter<Medication> {
    private Context theContext;
    private List<Medication> medList = new ArrayList<>();
    public MedAdapter(@NonNull Context context, ArrayList<Medication> list) {
        super(context, 0, list);
            theContext = context;
            medList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(theContext).inflate(R.layout.item,parent,false);
        Medication currentMed = medList.get(position);
        ArrayList<String> medInfo = currentMed.getInfo();

        TextView itemName = (TextView) listItem.findViewById(R.id.itemName);
        itemName.setText(medInfo.get(1));

        TextView itemDate = (TextView) listItem.findViewById(R.id.itemDate);
        itemDate.setText("Date Started: " + medInfo.get(0));

        TextView itemDose = (TextView) listItem.findViewById(R.id.itemDose);
        itemDose.setText("Dosage: " + medInfo.get(2) + " " + medInfo.get(3) + ", " + medInfo.get(4) + "x daily.");

        return listItem;
    }
}
