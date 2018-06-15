package com.brenohff.medonline.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brenohff.medonline.Domain.Mensagem;
import com.brenohff.medonline.R;

import java.util.List;

public class MensagemAdapter extends RecyclerView.Adapter<MensagemAdapter.MensagemViewHolder> {

    private List<Mensagem> mensagemList;
    private Context context;

    public MensagemAdapter(List<Mensagem> mensagemList) {
        this.mensagemList = mensagemList;
    }

    @NonNull
    @Override
    public MensagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mensagem_viewholder, parent, false);
        context = view.getContext();

        return new MensagemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensagemViewHolder holder, int position) {
        Mensagem mensagem = mensagemList.get(position);
        if (mensagem.isFromPaciente()) {
            holder.mensagem_medico_nome.setText(mensagem.getConsulta().getPaciente().getNome());
        } else {
            holder.mensagem_medico_nome.setText(mensagem.getConsulta().getMedico().getNome());
        }
        holder.mensagem_medico_texto.setText(mensagem.getTexto());
    }

    @Override
    public int getItemCount() {
        return mensagemList.size();
    }

    public class MensagemViewHolder extends RecyclerView.ViewHolder {

        private TextView mensagem_medico_texto, mensagem_medico_nome;

        public MensagemViewHolder(View itemView) {
            super(itemView);

            mensagem_medico_texto = (TextView) itemView.findViewById(R.id.mensagem_medico_texto);
            mensagem_medico_nome = (TextView) itemView.findViewById(R.id.mensagem_medico_nome);
        }
    }

}
