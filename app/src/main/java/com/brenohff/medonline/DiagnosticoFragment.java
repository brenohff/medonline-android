package com.brenohff.medonline;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brenohff.medonline.Activities.MainActivity;
import com.brenohff.medonline.Adapters.ReceitaAdapter;
import com.brenohff.medonline.Domain.Consulta;
import com.brenohff.medonline.Domain.Diagnostico;
import com.brenohff.medonline.Domain.Receita;
import com.brenohff.medonline.Others.ItemClickSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiagnosticoFragment extends Fragment {

    private Context context;
    private Consulta consulta;
    private List<Receita> receitaList = new ArrayList<>();

    private RecyclerView diag_recycler;
    private EditText diag_resultado, diag_obs, rec_descricao;
    private Button diag_add_receita, diag_finalizar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            consulta = (Consulta) getArguments().getSerializable("consulta");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diagnostico, container, false);
        context = view.getContext();

        diag_recycler = (RecyclerView) view.findViewById(R.id.diag_recycler);
        diag_resultado = (EditText) view.findViewById(R.id.diag_resultado);
        diag_obs = (EditText) view.findViewById(R.id.diag_obs);
        diag_add_receita = (Button) view.findViewById(R.id.diag_add_receita);
        diag_finalizar = (Button) view.findViewById(R.id.diag_finalizar);

        diag_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (diag_obs.getText().toString().isEmpty() || diag_resultado.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Preencha o resultado e/ou observação", Toast.LENGTH_SHORT).show();
                } else {
                    Diagnostico diagnostico = new Diagnostico();
                    diagnostico.setConsulta(consulta);
                    diagnostico.setDescricao(diag_obs.getText().toString());
                    diagnostico.setResultado(diag_resultado.getText().toString());
                    diagnostico.setReceita(receitaList);
                    salvarDiagnostico(diagnostico);
                }

            }
        });

        diag_add_receita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setCancelable(false);
                dialogBuilder.setTitle("Descrição da receita");
                dialogBuilder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (rec_descricao.getText().toString().isEmpty()) {

                        } else {
                            Receita receita = new Receita();
                            receita.setDtReceita(new Date());
                            receita.setDescricao(rec_descricao.getText().toString());
                            receitaList.add(receita);
                            inflateRecyclerView();
                        }
                    }
                })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });


                LayoutInflater inflater = ((MainActivity) context).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_receita, null);
                dialogBuilder.setView(dialogView);

                rec_descricao = (EditText) dialogView.findViewById(R.id.rec_descricao);

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });

        return view;
    }

    private void salvarDiagnostico(Diagnostico diagnostico) {
        Call<Void> call = MainActivity.requests.salvarDiagnostico(diagnostico);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Diagnóstico salvo com sucesso.", Toast.LENGTH_SHORT).show();
                    ((MainActivity) context).popFragment(2);
                } else {
                    Toast.makeText(context, "Erro ao salvar diagnóstico", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Falha ao salvar diagnóstico", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inflateRecyclerView() {
        diag_recycler.setLayoutManager(new LinearLayoutManager(context));
        diag_recycler.setHasFixedSize(true);
        ItemClickSupport.addTo(diag_recycler).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        });
        diag_recycler.setAdapter(new ReceitaAdapter(receitaList));
    }

}
