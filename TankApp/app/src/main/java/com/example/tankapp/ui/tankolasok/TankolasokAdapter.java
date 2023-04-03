package com.example.tankapp.ui.tankolasok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tankapp.R;

import java.util.ArrayList;

public class TankolasokAdapter extends ArrayAdapter<TestModel> {
    private ArrayList<TestModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView datumTxt;
        TextView tavTxt;
        TextView egysegarTxt;
        TextView mennyTxt;
        TextView uanyag_tipusTxt;
    }

    public TankolasokAdapter(ArrayList<TestModel> data, Context context){
        super(context, R.layout.row_item_tankolasok, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TestModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_tankolasok, parent, false);
            viewHolder.datumTxt = (TextView) convertView.findViewById(R.id.datumTxt);
            viewHolder.tavTxt = (TextView) convertView.findViewById(R.id.tavTxt);
            viewHolder.mennyTxt = (TextView) convertView.findViewById(R.id.mennyTxt);
            viewHolder.uanyag_tipusTxt = (TextView) convertView.findViewById(R.id.uanyag_tipusTxt);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.datumTxt.setText(dataModel.getDatum());
        viewHolder.tavTxt.setText(dataModel.getTav());
        viewHolder.mennyTxt.setText(dataModel.getMenny());
        viewHolder.uanyag_tipusTxt.setText(dataModel.getUzema_tipus());
        // Return the completed view to render on screen
        return convertView;
    }

}
