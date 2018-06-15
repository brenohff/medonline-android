package com.brenohff.medonline.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.brenohff.medonline.Activities.MainActivity;
import com.brenohff.medonline.Adapters.ConsultaAdapter;
import com.brenohff.medonline.Connections.Connection;
import com.brenohff.medonline.Connections.Requests;
import com.brenohff.medonline.Domain.Consulta;
import com.brenohff.medonline.Memory.MainData;
import com.brenohff.medonline.Others.ItemClickSupport;
import com.brenohff.medonline.Others.SaveEmailOnMemory;
import com.brenohff.medonline.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MinhasConsultasFragment extends Fragment {

    private List<Consulta> consultaList;
    private Context context;

    private RecyclerView recyclerView;


    @Override
    public void onStart() {
        super.onStart();

        if (SaveEmailOnMemory.loadEmail(context).getTipo_usuario().equals("paciente")) {
            buscaConsultasPaciente();
        } else {
            buscaConsultasMedico();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minhas_consultas, container, false);
        context = view.getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.minhas_consutas_recycler);

        return view;
    }

    private void buscaConsultasPaciente() {
        Requests requests = Connection.createService(Requests.class);
        Call<List<Consulta>> call = requests.buscaConsultaPorPaciente(MainData.getInstance().getPaciente().getIdPaciente());
        call.enqueue(new Callback<List<Consulta>>() {
            @Override
            public void onResponse(Call<List<Consulta>> call, Response<List<Consulta>> response) {
                if (response.isSuccessful()) {
                    consultaList = new ArrayList<>();
                    consultaList = response.body();

                    inflateRecyclerView();
                } else {
                    Toast.makeText(context, "Erro ao buscar consultas.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Consulta>> call, Throwable t) {
                Toast.makeText(context, "Falha ao buscar consultas.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscaConsultasMedico() {
        Requests requests = Connection.createService(Requests.class);
        Call<List<Consulta>> call = requests.buscaConsultaPorMedico(MainData.getInstance().getMedico().getIdMedico());
        call.enqueue(new Callback<List<Consulta>>() {
            @Override
            public void onResponse(Call<List<Consulta>> call, Response<List<Consulta>> response) {
                if (response.isSuccessful()) {
                    consultaList = new ArrayList<>();
                    consultaList = response.body();

                    inflateRecyclerView();
                } else {
                    Toast.makeText(context, "Erro ao buscar consultas.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Consulta>> call, Throwable t) {
                Toast.makeText(context, "Falha ao buscar consultas.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inflateRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Consulta c = consultaList.get(position);
                Consulta2Fragment consulta2Fragment = new Consulta2Fragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("consulta", c);
                consulta2Fragment.setArguments(bundle);
                ((MainActivity) context).pushFragmentWithStack(consulta2Fragment, "Consulta2Fragment");
            }
        });
        recyclerView.setAdapter(new ConsultaAdapter(consultaList));
    }

}
