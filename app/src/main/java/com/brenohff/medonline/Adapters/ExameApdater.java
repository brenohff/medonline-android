package com.brenohff.medonline.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brenohff.medonline.Domain.Exame;
import com.brenohff.medonline.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class ExameApdater extends RecyclerView.Adapter<ExameApdater.ExameViewHolder> {

    private List<Exame> exameList;
    private Context context;

    public ExameApdater(List<Exame> exameList) {
        this.exameList = exameList;
    }

    @NonNull
    @Override
    public ExameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exame_viewholder, parent, false);
        context = view.getContext();

        return new ExameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExameViewHolder holder, int position) {
        Exame exame = exameList.get(position);
        holder.tipo.setText(exame.getTipo());
        holder.descricao.setText(exame.getDescricao());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        holder.hora.setText(simpleDateFormat.format(exame.getDtExame()));

    }

    @Override
    public int getItemCount() {
        return exameList.size();
    }

    public class ExameViewHolder extends RecyclerView.ViewHolder {

        private TextView tipo, descricao, hora;

        public ExameViewHolder(View itemView) {
            super(itemView);

            tipo = (TextView) itemView.findViewById(R.id.exame_paciente_tipo);
            descricao = (TextView) itemView.findViewById(R.id.exame_paciente_descricao);
            hora = (TextView) itemView.findViewById(R.id.exame_paciente_horario);
        }
    }


}
