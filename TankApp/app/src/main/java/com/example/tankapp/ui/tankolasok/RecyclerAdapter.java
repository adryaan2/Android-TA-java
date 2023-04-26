package com.example.tankapp.ui.tankolasok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankapp.R;
import com.example.tankapp.data.TankolasOsszetett;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TankolasHolder> {


    private List<TankolasOsszetett> osszList;
    DateTimeFormatter formatter;

    public RecyclerAdapter(List<TankolasOsszetett> osszList) {
        this.osszList = osszList;
        formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    }

    @NonNull
    @Override
    public RecyclerAdapter.TankolasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_tankolasok, parent, false);
        return new TankolasHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.TankolasHolder holder, int position) {
        TankolasOsszetett tankolas = osszList.get(position);
        holder.egysegar.setText(tankolas.xEgysegar());
        holder.datum.setText(tankolas.getDatum().format(formatter));
        holder.tav.setText(tankolas.xMegtettUt());
        holder.uanyag_tipus.setText(tankolas.getUzemanyag());
        holder.menny.setText(tankolas.xTankoltMennyiseg());
    }

    @Override
    public int getItemCount() {
        return osszList.size();
    }

    public class TankolasHolder extends RecyclerView.ViewHolder{
        public TextView datum;
        public TextView tav;
        public TextView egysegar;
        public TextView menny;
        public TextView uanyag_tipus;

        public TankolasHolder(@NonNull View itemView) {
            super(itemView);
            datum = itemView.findViewById(R.id.datumTxt);
            tav = itemView.findViewById(R.id.tavTxt);
            egysegar = itemView.findViewById(R.id.egysegarTxt);
            menny = itemView.findViewById(R.id.mennyTxt);
            uanyag_tipus = itemView.findViewById(R.id.uanyag_tipusTxt);
        }
    }
}
