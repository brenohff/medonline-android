package com.brenohff.medonline.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brenohff.medonline.Domain.Medico;
import com.brenohff.medonline.R;

import java.util.List;

public class MedicosAdapter extends RecyclerView.Adapter<MedicosAdapter.MedicosViewHolder> {

    private Context context;
    private List<Medico> medicoList;

    public MedicosAdapter(List<Medico> medicoList) {
        this.medicoList = medicoList;
    }

    @NonNull
    @Override
    public MedicosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicos_viewholder, parent, false);
        context = view.getContext();

        return new MedicosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicosViewHolder holder, int position) {
        Medico medico = medicoList.get(position);
        holder.medico_nome.setText(medico.getNome());
        holder.medico_especialidade.setText(medico.getEspecialidade().getEspecialidade());
    }

    @Override
    public int getItemCount() {
        return medicoList.size();
    }

    public class MedicosViewHolder extends RecyclerView.ViewHolder {

        private TextView medico_nome, medico_especialidade;

        public MedicosViewHolder(View itemView) {
            super(itemView);

            medico_especialidade = (TextView) itemView.findViewById(R.id.medico_especialidade);
            medico_nome = (TextView) itemView.findViewById(R.id.medico_nome);
        }
    }

}
