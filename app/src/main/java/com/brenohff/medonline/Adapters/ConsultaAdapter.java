package com.brenohff.medonline.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brenohff.medonline.Domain.Consulta;
import com.brenohff.medonline.Others.SaveEmailOnMemory;
import com.brenohff.medonline.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class ConsultaAdapter extends RecyclerView.Adapter<ConsultaAdapter.ConsultaViewHolder> {

    private List<Consulta> consultaList;
    private Context context;

    public ConsultaAdapter(List<Consulta> consultaList) {
        this.consultaList = consultaList;
    }

    @NonNull
    @Override
    public ConsultaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consulta_viewholder, parent, false);
        context = view.getContext();

        return new ConsultaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultaViewHolder holder, int position) {
        Consulta c = consultaList.get(position);
        holder.tv_assunto.setText(c.getAssunto());

        if (SaveEmailOnMemory.loadEmail(context).getTipo_usuario().equals("paciente")) {
            holder.tv_nome_medico.setText(c.getMedico().getNome() + " (" + c.getMedico().getEspecialidade().getEspecialidade() + ")");
        } else {
            holder.tv_nome_medico.setText(c.getPaciente().getNome() );
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        holder.tv_data.setText(simpleDateFormat.format(c.getDtConsulta()));
    }

    @Override
    public int getItemCount() {
        return consultaList.size();
    }

    public class ConsultaViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_nome_medico, tv_assunto, tv_data;

        public ConsultaViewHolder(View itemView) {
            super(itemView);

            tv_nome_medico = (TextView) itemView.findViewById(R.id.et_consulta_nome_medico);
            tv_assunto = (TextView) itemView.findViewById(R.id.et_consulta_assunto);
            tv_data = (TextView) itemView.findViewById(R.id.et_consulta_data);
        }
    }
}
