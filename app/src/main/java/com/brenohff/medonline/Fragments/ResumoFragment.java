package com.brenohff.medonline.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.brenohff.medonline.Activities.MainActivity;
import com.brenohff.medonline.Domain.Diagnostico;
import com.brenohff.medonline.Domain.Exame;
import com.brenohff.medonline.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumoFragment extends Fragment {

    private Context context;
    private Diagnostico diagnostico;

    private TextView resumo_assunto, resumo_medico, resumo_especialidade;
    private TextView resumo_exame;
    private TextView resumo_diag_desc, resumo_diag_res;
    private TextView resumo_receita;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            diagnostico = (Diagnostico) getArguments().getSerializable("diag");
            buscaExamesPorConsulta();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumo, container, false);
        context = view.getContext();

        resumo_assunto = (TextView) view.findViewById(R.id.resumo_assunto);
        resumo_medico = (TextView) view.findViewById(R.id.resumo_medico);
        resumo_especialidade = (TextView) view.findViewById(R.id.resumo_especialidade);
        resumo_exame = (TextView) view.findViewById(R.id.resumo_exame);
        resumo_diag_desc = (TextView) view.findViewById(R.id.resumo_diag_desc);
        resumo_diag_res = (TextView) view.findViewById(R.id.resumo_diag_res);
        resumo_receita = (TextView) view.findViewById(R.id.resumo_receita);


        return view;
    }

    private void buscaExamesPorConsulta() {
        Call<List<Exame>> call = MainActivity.requests.buscaExamePelaConsulta(diagnostico.getConsulta().getIdConsulta());
        call.enqueue(new Callback<List<Exame>>() {
            @Override
            public void onResponse(Call<List<Exame>> call, Response<List<Exame>> response) {
                if (response.isSuccessful()) {
                    List<Exame> exameList = new ArrayList<>();
                    exameList = response.body();
                    diagnostico.getConsulta().setExame(exameList);
                    alimentaResumo();
                } else {
                    Toast.makeText(context, "Erro ao obter exames", Toast.LENGTH_SHORT).show();
                    ((MainActivity) context).popFragment(1);
                }
            }

            @Override
            public void onFailure(Call<List<Exame>> call, Throwable t) {
                Toast.makeText(context, "Falha ao obter exames", Toast.LENGTH_SHORT).show();
                ((MainActivity) context).popFragment(1);
            }
        });

    }

    private void alimentaResumo() {
        resumo_assunto.setText(diagnostico.getConsulta().getAssunto());
        resumo_medico.setText(diagnostico.getConsulta().getMedico().getNome());
        resumo_especialidade.setText(diagnostico.getConsulta().getMedico().getEspecialidade().getEspecialidade());
        resumo_diag_desc.setText(diagnostico.getDescricao());
        resumo_diag_res.setText(diagnostico.getResultado());

        String exame = "";
        for (int i = 0; i < diagnostico.getConsulta().getExame().size(); i++) {
            if (i == 0) {
                exame = diagnostico.getConsulta().getExame().get(i).getTipo();
            } else {
                exame = exame.concat("\n" + diagnostico.getConsulta().getExame().get(i).getTipo());
            }
        }
        resumo_exame.setText(exame);

        String receita = "";
        for (int i = 0; i < diagnostico.getReceita().size(); i++) {
            if (i == 0) {
                receita = diagnostico.getReceita().get(i).getDescricao();
            } else {
                receita = receita.concat("\n" + diagnostico.getReceita().get(i).getDescricao());
            }
        }
        resumo_receita.setText(receita);
    }

}
