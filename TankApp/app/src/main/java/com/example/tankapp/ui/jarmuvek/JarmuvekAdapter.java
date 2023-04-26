package com.example.tankapp.ui.jarmuvek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankapp.MainActivity;
import com.example.tankapp.R;
import com.example.tankapp.data.AutoModel;

import java.util.List;

public class JarmuvekAdapter extends RecyclerView.Adapter<JarmuvekAdapter.ViewHolder> {

    private List<AutoModel> autoModelsList;
    private Context context;

    public JarmuvekAdapter(List<AutoModel> autoModelsList, Context context) {
        this.autoModelsList = autoModelsList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jamu_lista_elem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AutoModel autoModel =  autoModelsList.get(position);

        holder.textViewNumberPlate.setText(autoModel.getRendszam());
        holder.textViewCarName.setText(autoModel.getMegj());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, autoModel.getRendszam() + " jelű járműre váltott",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return autoModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewNumberPlate;
        public TextView textViewCarName;

        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumberPlate = (TextView) itemView.findViewById(R.id.numberPlateTitle);
            textViewCarName = (TextView) itemView.findViewById(R.id.carNameTitle);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }

}
