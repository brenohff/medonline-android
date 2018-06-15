package com.brenohff.medonline.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brenohff.medonline.Domain.Receita;
import com.brenohff.medonline.R;

import java.util.List;

public class ReceitaAdapter extends RecyclerView.Adapter<ReceitaAdapter.ReceitaViewHolder> {

    private List<Receita> receitaList;
    private Context context;

    public ReceitaAdapter(List<Receita> receitaList) {
        this.receitaList = receitaList;
    }

    @NonNull
    @Override
    public ReceitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receita_viewholder, parent, false);
        context = view.getContext();

        return new ReceitaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceitaViewHolder holder, int position) {
        Receita receita = receitaList.get(position);
        holder.receita_desc.setText(receita.getDescricao());
    }

    @Override
    public int getItemCount() {
        return receitaList.size();
    }

    public class ReceitaViewHolder extends RecyclerView.ViewHolder {

        private TextView receita_desc;

        public ReceitaViewHolder(View itemView) {
            super(itemView);

            receita_desc = (TextView) itemView.findViewById(R.id.receita_desc);
        }
    }
}
