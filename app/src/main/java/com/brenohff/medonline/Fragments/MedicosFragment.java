package com.brenohff.medonline.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.brenohff.medonline.Activities.MainActivity;
import com.brenohff.medonline.Adapters.MedicosAdapter;
import com.brenohff.medonline.Connections.Connection;
import com.brenohff.medonline.Connections.Requests;
import com.brenohff.medonline.Domain.Especialidade;
import com.brenohff.medonline.Domain.Medico;
import com.brenohff.medonline.Others.ItemClickSupport;
import com.brenohff.medonline.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicosFragment extends Fragment {

    private Context context;
    private List<Especialidade> especialidadeList;
    private List<Medico> medicoList = new ArrayList<>();

    private TextView medico_noMedico;
    private MaterialSpinner spinner;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        buscaEspecialidades();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicos, container, false);
        context = view.getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.medico_recyclerView);
        medico_noMedico = (TextView) view.findViewById(R.id.medico_noMedico);
        spinner = (MaterialSpinner) view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position > 0) {
                    buscaMedicoPelaEspecialidade(position - 1);
                } else {
                    buscaMedicos();
                }
            }
        });
        buscaMedicos();

        return view;
    }

    private void inflateRecyclerView() {
        if (medicoList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            medico_noMedico.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setHasFixedSize(true);
            ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    MedicoFragmento medicoFragmento = new MedicoFragmento();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("medico", medicoList.get(position));
                    medicoFragmento.setArguments(bundle);
                    ((MainActivity) context).pushFragmentWithStack(medicoFragmento, "MedicoFragmento");
                }
            });
            recyclerView.setAdapter(new MedicosAdapter(medicoList));
        } else {
            recyclerView.setVisibility(View.GONE);
            medico_noMedico.setVisibility(View.VISIBLE);
        }
    }

    private void buscaEspecialidades() {
        final Requests requests = Connection.createService(Requests.class);
        Call<List<Especialidade>> call = requests.buscaTodos();
        call.enqueue(new Callback<List<Especialidade>>() {
            @Override
            public void onResponse(Call<List<Especialidade>> call, Response<List<Especialidade>> response) {
                if (response.isSuccessful()) {
                    especialidadeList = new ArrayList<>();
                    especialidadeList = response.body();

                    List<String> esp_nomes = new ArrayList<>();
                    esp_nomes.add("Todas as especialidades");

                    for (Especialidade especialidade : especialidadeList) {
                        esp_nomes.add(especialidade.getEspecialidade());
                    }
                    spinner.setItems(esp_nomes);

                } else {
                    Toast.makeText(context, "Não foi possível buscar especialidades", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Especialidade>> call, Throwable t) {
                Toast.makeText(context, "Erro ao buscar especialidades", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscaMedicoPelaEspecialidade(int position) {
        final Requests requests = Connection.createService(Requests.class);
        Call<List<Medico>> call = requests.buscaMedicoPelaEspecialidade(especialidadeList.get(position).getIdEspecialidade());
        call.enqueue(new Callback<List<Medico>>() {
            @Override
            public void onResponse(Call<List<Medico>> call, Response<List<Medico>> response) {
                if (response.isSuccessful()) {
                    medicoList = response.body();
                    inflateRecyclerView();
                } else {
                    Toast.makeText(context, "Não foi possível buscar medicos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Medico>> call, Throwable t) {
                Toast.makeText(context, "Erro ao buscar medicos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void buscaMedicos() {
        Requests requests = Connection.createService(Requests.class);
        Call<List<Medico>> call = requests.buscaTodosMedicos();
        call.enqueue(new Callback<List<Medico>>() {
            @Override
            public void onResponse(Call<List<Medico>> call, Response<List<Medico>> response) {
                if (response.isSuccessful()) {
                    medicoList = response.body();
                    inflateRecyclerView();
                } else {
                    Toast.makeText(context, "Não foi possível buscar medicos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Medico>> call, Throwable t) {
                Toast.makeText(context, "Erro ao buscar medicos", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
