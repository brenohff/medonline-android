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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brenohff.medonline.Adapters.MensagemAdapter;
import com.brenohff.medonline.Connections.Connection;
import com.brenohff.medonline.Connections.Requests;
import com.brenohff.medonline.Domain.Consulta;
import com.brenohff.medonline.Domain.Medico;
import com.brenohff.medonline.Domain.Mensagem;
import com.brenohff.medonline.Domain.Paciente;
import com.brenohff.medonline.Others.ItemClickSupport;
import com.brenohff.medonline.Others.SaveEmailOnMemory;
import com.brenohff.medonline.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.client.StompClient;


public class ConsultaFragment extends Fragment {

    private View view;
    private Context context;
    private List<Mensagem> mensagemList = new ArrayList<>();

    private Consulta consulta = new Consulta();
    private Medico medico;
    private Paciente paciente;

    private Button iniciar_consulta, bt_enviar_msg;
    private EditText et_assunto_consulta, et_consulta_mensagem;
    private LinearLayout layout_assunto_consulta;
    private RelativeLayout layout_assunto_mensagem;
    private RecyclerView rv_consultas_mensagens;
    private TextView tv_assunto_consulta;

    private static final String TAG = "SOCKET";
    private static StompClient mStompClient;
    private Gson mGson = new GsonBuilder().create();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            medico = (Medico) getArguments().getSerializable("medico");
            paciente = (Paciente) getArguments().getSerializable("paciente");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_consulta, container, false);
        context = view.getContext();

        iniciar_consulta = (Button) view.findViewById(R.id.iniciar_consulta);
        bt_enviar_msg = (Button) view.findViewById(R.id.bt_enviar_msg);
        et_assunto_consulta = (EditText) view.findViewById(R.id.et_assunto_consulta);
        et_consulta_mensagem = (EditText) view.findViewById(R.id.et_consulta_mensagem);
        layout_assunto_consulta = (LinearLayout) view.findViewById(R.id.layout_assunto_consulta);
        layout_assunto_mensagem = (RelativeLayout) view.findViewById(R.id.layout_assunto_mensagem);
        rv_consultas_mensagens = (RecyclerView) view.findViewById(R.id.rv_consultas_mensagens);
        tv_assunto_consulta = (TextView) view.findViewById(R.id.tv_assunto_consulta);

        iniciar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarConsulta();
            }
        });

        bt_enviar_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaMensagem();
            }
        });

        return view;
    }

    private void inflateRecyclerView() {
        rv_consultas_mensagens.setLayoutManager(new LinearLayoutManager(context));
        rv_consultas_mensagens.setHasFixedSize(true);
        ItemClickSupport.addTo(rv_consultas_mensagens).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
            }
        });
        rv_consultas_mensagens.setAdapter(new MensagemAdapter(mensagemList));
    }

    private void salvarConsulta() {
        consulta.setAssunto(et_assunto_consulta.getText().toString());
        consulta.setDt_consulta(new Date());
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);

        Requests requests = Connection.createService(Requests.class);
        Call<Consulta> call = requests.salvarConsulta(consulta);
        call.enqueue(new Callback<Consulta>() {
            @Override
            public void onResponse(Call<Consulta> call, Response<Consulta> response) {
                if (response.isSuccessful()) {
                    consulta = response.body();
                    layout_assunto_consulta.setVisibility(View.GONE);
                    tv_assunto_consulta.setVisibility(View.VISIBLE);
                    layout_assunto_mensagem.setVisibility(View.VISIBLE);
                    tv_assunto_consulta.setText("Assunto: " + et_assunto_consulta.getText().toString());
                } else {
                    Toast.makeText(context, "Erro ao salvar consulta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Consulta> call, Throwable t) {
                Toast.makeText(context, "Falha ao salvar consulta", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviaMensagem() {
        Mensagem mensagem = new Mensagem();
        mensagem.setConsulta(consulta);
        mensagem.setData(new Date());
        mensagem.setFromPaciente(SaveEmailOnMemory.loadEmail(context).getTipo_usuario().equals("paciente"));
        mensagem.setTexto(et_consulta_mensagem.getText().toString());

        Requests requests = Connection.createService(Requests.class);
        Call<Void> call = requests.salvarMesagem(mensagem);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "msg enviada.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


}
