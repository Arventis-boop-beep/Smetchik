package com.example.astroybat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SmetaAdapter extends RecyclerView.Adapter<SmetaAdapter.ViewHolder> {

    Context context;
    List<Smeta> smetaList;

    public SmetaAdapter(Context context, List<Smeta> smetaList) {
        this.context = context;
        this.smetaList = smetaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.smeta_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(smetaList != null && smetaList.size() > 0){
            Smeta smeta = smetaList.get(position);
            holder.number_tv.setText(position);
            holder.name_tv.setText(smeta.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return smetaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView number_tv, name_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            number_tv = itemView.findViewById(R.id.number_tv);
            name_tv = itemView.findViewById(R.id.name_tv);
        }
    }
}
