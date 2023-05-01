package com.example.tankapp.ui.stats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankapp.R;

import java.util.List;

public class BontasAdapter extends RecyclerView.Adapter<BontasAdapter.BontasHolder> {
    private List<TankolasokSzamaBontasban> lista;

    public BontasAdapter(List<TankolasokSzamaBontasban> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public BontasAdapter.BontasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_bontas, parent, false);
        return new BontasHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BontasAdapter.BontasHolder holder, int position) {
        TankolasokSzamaBontasban elem = lista.get(position);
        holder.hanyszor.setText(String.valueOf(elem.getTankolasokSzama()));
        holder.idoszak.setText(elem.getIdoszak());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class BontasHolder extends RecyclerView.ViewHolder{
        public TextView idoszak;
        public TextView hanyszor;

        public BontasHolder(@NonNull View itemView) {
            super(itemView);
            idoszak = itemView.findViewById(R.id.idoszak);
            hanyszor = itemView.findViewById(R.id.hanyszor);
        }
    }
}
