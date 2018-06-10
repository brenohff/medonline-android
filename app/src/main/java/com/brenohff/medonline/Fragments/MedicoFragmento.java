package com.brenohff.medonline.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.brenohff.medonline.Domain.Medico;
import com.brenohff.medonline.Memory.MainData;
import com.brenohff.medonline.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class MedicoFragmento extends Fragment {

    private Context context;
    private Medico medico;

    private TextView medico_fragment_nome, medico_fragment_especialidade;
    private CircleImageView medico_imagem;
    private Button bt_iniciar_consulta;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            medico = (Medico) getArguments().getSerializable("medico");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medico_fragmento, container, false);
        context = view.getContext();

        castFields(view);

        return view;
    }

    private void castFields(View view) {

        bt_iniciar_consulta = (Button) view.findViewById(R.id.bt_iniciar_consulta);
        medico_fragment_especialidade = (TextView) view.findViewById(R.id.medico_fragment_especialidade);
        medico_fragment_nome = (TextView) view.findViewById(R.id.medico_fragment_nome);

        bt_iniciar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainData.getInstance().getPaciente() == null) {
                    Toast.makeText(context, "Faça login antes de iniciar uma consulta.", Toast.LENGTH_SHORT).show();
                }

                if (MainData.getInstance().getMedico() != null) {
                    Toast.makeText(context, "Não é possível realizar consulta com conta de médico..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        medico_fragment_especialidade.setText(medico.getEspecialidade().getEspecialidade());
        medico_fragment_nome.setText(medico.getNome());
    }

}
