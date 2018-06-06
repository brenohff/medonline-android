package com.brenohff.medonline.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brenohff.medonline.R;

public class MedicosAdapter extends RecyclerView.Adapter<MedicosAdapter.MedicosViewHolder> {

    private Context context;

    @NonNull
    @Override
    public MedicosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicos_viewholder, parent, false);
        context = view.getContext();

        return new MedicosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicosViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MedicosViewHolder extends RecyclerView.ViewHolder {

        public MedicosViewHolder(View itemView) {
            super(itemView);
        }
    }

}
