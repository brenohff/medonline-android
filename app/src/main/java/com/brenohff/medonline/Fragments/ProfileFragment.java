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

import com.brenohff.medonline.Activities.MainActivity;
import com.brenohff.medonline.Domain.Medico;
import com.brenohff.medonline.Domain.Paciente;
import com.brenohff.medonline.Domain.TipoSexo;
import com.brenohff.medonline.FirebaseConfig.FirebaseConfig;
import com.brenohff.medonline.Memory.MainData;
import com.brenohff.medonline.Others.SaveEmailOnMemory;
import com.brenohff.medonline.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;

public class ProfileFragment extends Fragment {

    private Context context;
    private FirebaseAuth mAuth;
    private Paciente paciente;
    private Medico medico;

    private Button bt_logout, bt_profile_minhas_consultas, bt_meus_exames;
    private TextView tv_profile_sexo, tv_profile_nascimento, tv_profile_nome;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MainData.getInstance().getPaciente() != null) {
            paciente = MainData.getInstance().getPaciente();
        } else {
            medico = MainData.getInstance().getMedico();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (paciente != null) {
            View view = inflater.inflate(R.layout.fragment_profile_paciente, container, false);
            context = view.getContext();

            castFields(view);

            return view;
        } else {
            View view = inflater.inflate(R.layout.fragment_profile_medico, container, false);
            context = view.getContext();

            castMedicoFields(view);
            return view;
        }
    }

    private void castFields(View view) {
        tv_profile_nome = (TextView) view.findViewById(R.id.tv_profile_nome);
        tv_profile_sexo = (TextView) view.findViewById(R.id.tv_profile_sexo);
        tv_profile_nascimento = (TextView) view.findViewById(R.id.tv_profile_nascimento);
        bt_profile_minhas_consultas = (Button) view.findViewById(R.id.bt_profile_minhas_consultas);
        bt_logout = (Button) view.findViewById(R.id.bt_logout);
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseConfig.getmAuth();
                mAuth.signOut();
                SaveEmailOnMemory.removeEmail(context);
                ((MainActivity) context).setFragment(2);
            }
        });

        bt_profile_minhas_consultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).pushFragmentWithStack(new MinhasConsultasFragment(), "MinhasConsultasFragment");
            }
        });

        tv_profile_nome.setText(paciente.getNome());
        if (paciente.getSexo() == TipoSexo.MASC) {
            tv_profile_sexo.setText("Masculino");
        } else {
            tv_profile_sexo.setText("Feminino");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tv_profile_nascimento.setText(simpleDateFormat.format(paciente.getIdade()));
    }

    private void castMedicoFields(View view) {

        tv_profile_nome = (TextView) view.findViewById(R.id.tv_profile_nome);
        tv_profile_sexo = (TextView) view.findViewById(R.id.tv_profile_sexo);
        tv_profile_nascimento = (TextView) view.findViewById(R.id.tv_profile_nascimento);
        bt_profile_minhas_consultas = (Button) view.findViewById(R.id.bt_profile_minhas_consultas);
        bt_logout = (Button) view.findViewById(R.id.bt_logout);

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseConfig.getmAuth();
                mAuth.signOut();
                SaveEmailOnMemory.removeEmail(context);
                ((MainActivity) context).setFragment(2);
            }
        });

        bt_profile_minhas_consultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).pushFragmentWithStack(new MinhasConsultasFragment(), "MinhasConsultasFragment");
            }
        });

        tv_profile_nome.setText(medico.getNome());
        if (medico.getSexo() == TipoSexo.MASC) {
            tv_profile_sexo.setText("Masculino");
        } else {
            tv_profile_sexo.setText("Feminino");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tv_profile_nascimento.setText(simpleDateFormat.format(medico.getDtNascimento()));
    }

}
