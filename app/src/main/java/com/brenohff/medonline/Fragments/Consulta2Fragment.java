package com.brenohff.medonline.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brenohff.medonline.Activities.MainActivity;
import com.brenohff.medonline.Adapters.MensagemAdapter;
import com.brenohff.medonline.Connections.Connection;
import com.brenohff.medonline.Connections.Requests;
import com.brenohff.medonline.DiagnosticoFragment;
import com.brenohff.medonline.Domain.Consulta;
import com.brenohff.medonline.Domain.Mensagem;
import com.brenohff.medonline.Others.ItemClickSupport;
import com.brenohff.medonline.Others.SaveEmailOnMemory;
import com.brenohff.medonline.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Consulta2Fragment extends Fragment {

    private Consulta consulta;
    private List<Mensagem> mensagemList;
    private Context context;

    private RecyclerView recyclerView;
    private TextView textoAssunto;
    private EditText textoMensagem;
    private Button bt_enviar, bt_menu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            consulta = (Consulta) getArguments().getSerializable("consulta");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        buscaMensagens();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulta2, container, false);
        context = view.getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_consultas_mensagens2);
        textoAssunto = (TextView) view.findViewById(R.id.tv_assunto_consulta2);
        textoMensagem = (EditText) view.findViewById(R.id.et_consulta_mensagem2);
        bt_enviar = (Button) view.findViewById(R.id.bt_enviar_msg2);
        bt_menu = (Button) view.findViewById(R.id.bt_menu);

        bt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterPopup(view);
            }
        });
        bt_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaMensagem();
            }
        });

        textoAssunto.setText("Assunto: " + consulta.getAssunto());

        return view;
    }

    private void enviaMensagem() {
        Mensagem mensagem = new Mensagem();
        mensagem.setConsulta(consulta);
        mensagem.setDtMensagem(new Date());
        mensagem.setFromPaciente(SaveEmailOnMemory.loadEmail(context).getTipo_usuario().equals("paciente"));
        mensagem.setTexto(textoMensagem.getText().toString());

        Requests requests = Connection.createService(Requests.class);
        Call<List<Mensagem>> call = requests.salvarMesagem(mensagem);
        call.enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                if (response.isSuccessful()) {
                    mensagemList = response.body();
                    inflateRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {

            }
        });
    }

    private void buscaMensagens() {
        Requests requests = Connection.createService(Requests.class);
        Call<List<Mensagem>> call = requests.buscaMensagensPorConsulta(consulta.getIdConsulta());
        call.enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                if (response.isSuccessful()) {
                    mensagemList = new ArrayList<>();
                    mensagemList = response.body();

                    inflateRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {

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
        recyclerView.setAdapter(new MensagemAdapter(mensagemList));
    }

    private void showFilterPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);

        if (SaveEmailOnMemory.loadEmail(context).getTipo_usuario().equals("paciente")) {
            popup.getMenuInflater().inflate(R.menu.popup_filte_pacienter, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_paciente_ver_exame:
                            ExameFragment exameFragment = new ExameFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("consulta", consulta);
                            exameFragment.setArguments(bundle);
                            ((MainActivity) context).pushFragmentWithStack(exameFragment, "ExameFragment");
                            return true;

                        case R.id.menu_paciente_encerrar_exame:
                            finalizarConsulta();
                            return true;

                        default:
                            return false;
                    }
                }
            });
        } else {
            popup.getMenuInflater().inflate(R.menu.popup_filter_medico, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_medico_add_exame:
                            ExameFragment exameFragment = new ExameFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("consulta", consulta);
                            bundle.putString("tipo", "add");
                            exameFragment.setArguments(bundle);
                            ((MainActivity) context).pushFragmentWithStack(exameFragment, "ExameFragment");
                            return true;

                        case R.id.menu_medico_ver_exame:
                            ExameFragment exameFragment2 = new ExameFragment();
                            Bundle bundle2 = new Bundle();
                            bundle2.putSerializable("consulta", consulta);
                            bundle2.putString("tipo", "consulta");
                            exameFragment2.setArguments(bundle2);
                            ((MainActivity) context).pushFragmentWithStack(exameFragment2, "ExameFragment");
                            return true;

                        case R.id.menu_medico_add_diagostico:
                            DiagnosticoFragment diagnosticoFragment = new DiagnosticoFragment();
                            Bundle bundle3 = new Bundle();
                            bundle3.putSerializable("consulta", consulta);
                            diagnosticoFragment.setArguments(bundle3);
                            ((MainActivity) context).pushFragmentWithStack(diagnosticoFragment, "DiagnosticoFragment");
                            return true;

                        default:
                            return false;
                    }
                }
            });
        }

        popup.show();
    }

    private void finalizarConsulta() {
        consulta.setFinalizada(true);

        Requests requests = Connection.createService(Requests.class);
        Call<Void> call = requests.atualizarConsulta(consulta);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Consulta finalizada.", Toast.LENGTH_SHORT).show();
                    ((MainActivity) context).popFragment(1);
                } else {
                    Toast.makeText(context, "Erro ao finalizar consulta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Falha ao finalizar consulta.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
