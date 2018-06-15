package com.brenohff.medonline.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brenohff.medonline.Activities.MainActivity;
import com.brenohff.medonline.Adapters.ExameApdater;
import com.brenohff.medonline.Connections.Connection;
import com.brenohff.medonline.Connections.Requests;
import com.brenohff.medonline.Domain.Consulta;
import com.brenohff.medonline.Domain.Exame;
import com.brenohff.medonline.Domain.Medico;
import com.brenohff.medonline.Domain.Paciente;
import com.brenohff.medonline.Memory.MainData;
import com.brenohff.medonline.Others.ItemClickSupport;
import com.brenohff.medonline.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExameFragment extends Fragment {

    private View view;
    private Context context;
    private List<Exame> exameList;

    private Medico medico;
    private Paciente paciente;
    private Consulta consulta;
    private String tipo_exame;

    private RecyclerView recyclerView;
    private EditText tipo, descricao;
    private Button salvar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            consulta = (Consulta) getArguments().getSerializable("consulta");
            tipo_exame = getArguments().getString("tipo");
        }

        if (MainData.getInstance().getPaciente() != null || tipo_exame.equals("consulta")) {
            paciente = MainData.getInstance().getPaciente();
            buscaExamesPorConsulta();
        } else {
            medico = MainData.getInstance().getMedico();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (paciente != null || tipo_exame.equals("consulta")) {
            View view = inflater.inflate(R.layout.fragment_exame_paciente, container, false);
            context = view.getContext();

            castFieldsPaciente(view);

            return view;
        } else {
            View view = inflater.inflate(R.layout.fragment_exame_medico, container, false);
            context = view.getContext();

            castFieldMedico(view);
            return view;
        }

    }

    private void castFieldMedico(View view) {
        tipo = (EditText) view.findViewById(R.id.exame_tipo);
        descricao = (EditText) view.findViewById(R.id.exame_descricao);
        salvar = (Button) view.findViewById(R.id.exame_salvar);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipo.getText().toString().isEmpty() || descricao.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Insira todos os dados antes de salvar.", Toast.LENGTH_SHORT).show();
                } else if (consulta == null) {
                    Toast.makeText(context, "Verifique se a consult está correta.", Toast.LENGTH_SHORT).show();
                } else {
                    Exame exame = new Exame();
                    exame.setDtExame(new Date());
                    exame.setTipo(tipo.getText().toString());
                    exame.setConsulta(consulta);
                    exame.setDescricao(descricao.getText().toString());
                    salvarExame(exame);
                }
            }
        });
    }

    private void castFieldsPaciente(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.exame_paciente_recycler);
    }

    private void salvarExame(Exame exame) {
        Requests requests = Connection.createService(Requests.class);
        Call<Void> call = requests.salvarExame(exame);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Exame salvo com sucesso", Toast.LENGTH_SHORT).show();
                    ((MainActivity) context).popFragment(1);
                } else {
                    Toast.makeText(context, "Erro ao salvar exame", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Falha ao salvar exame", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscaExamesPorConsulta() {
        Call<List<Exame>> call = MainActivity.requests.buscaExamePelaConsulta(consulta.getIdConsulta());
        call.enqueue(new Callback<List<Exame>>() {
            @Override
            public void onResponse(Call<List<Exame>> call, Response<List<Exame>> response) {
                if (response.isSuccessful()) {
                    exameList = new ArrayList<>();
                    exameList = response.body();

                    inflateRecyclerView();
                } else {
                    Toast.makeText(context, "Erro ao obter exames", Toast.LENGTH_SHORT).show();
                    ((MainActivity) context).popFragment(1);
                }
            }

            @Override
            public void onFailure(Call<List<Exame>> call, Throwable t) {
                Toast.makeText(context, "Falha ao obter exames", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void inflateRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
            }
        });
        recyclerView.setAdapter(new ExameApdater(exameList));
    }

}
