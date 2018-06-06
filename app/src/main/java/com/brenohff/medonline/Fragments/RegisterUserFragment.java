package com.brenohff.medonline.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brenohff.medonline.Activities.MainActivity;
import com.brenohff.medonline.Connections.Connection;
import com.brenohff.medonline.Connections.Requests;
import com.brenohff.medonline.Domain.Endereco;
import com.brenohff.medonline.Domain.Paciente;
import com.brenohff.medonline.Domain.TipoSexo;
import com.brenohff.medonline.FirebaseConfig.FirebaseConfig;
import com.brenohff.medonline.Memory.MainData;
import com.brenohff.medonline.Others.MaskEditUtil;
import com.brenohff.medonline.Others.SaveEmailOnMemory;
import com.brenohff.medonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.rey.material.widget.Switch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserFragment extends Fragment implements View.OnClickListener {

    private Paciente paciente = new Paciente();
    private FirebaseAuth mAuth;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


    private Context context;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    private ViewGroup placeholder;

    private Button bt_proximo1, bt_proximo2, bt_voltar2, bt_voltar3, bt_register;
    private EditText et_register_email, et_register_password, et_register_password_confirm, et_register_cep, et_register_complemento, et_register_logradouro, et_register_nascimento, et_register_nome;
    private Switch switch_register_sexo;
    private boolean masc = true;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        mContainer = container;

        View view = inflater.inflate(R.layout.fragment_register_user_step1, container, false);
        placeholder = (ViewGroup) view;
        context = view.getContext();

        bt_proximo1 = (Button) view.findViewById(R.id.bt_proximo1);
        et_register_email = (EditText) view.findViewById(R.id.et_register_email);
        et_register_password = (EditText) view.findViewById(R.id.et_register_password);
        et_register_password_confirm = (EditText) view.findViewById(R.id.et_register_password_confirm);

        bt_proximo1.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        boolean b = true;
        String msg_erro = "Campo obrigatório";
        switch (view.getId()) {
            case R.id.bt_proximo1:
                //region verificaDados
                if (et_register_email.getText().toString().isEmpty()) {
                    et_register_email.setError(msg_erro);
                    b = false;
                }

                if (et_register_password.getText().toString().isEmpty()) {
                    et_register_password.setError(msg_erro);
                    b = false;
                }

                if (et_register_password_confirm.getText().toString().isEmpty()) {
                    et_register_password_confirm.setError(msg_erro);
                    b = false;
                }

                if (!et_register_password.getText().toString().equals(et_register_password_confirm.getText().toString())) {
                    et_register_password.setError("Os campos devem ser iguais");
                    et_register_password_confirm.setError("Os campos devem ser iguais");
                    b = false;
                }

                if (b) {
                    tela1To2();
                }
                //endregion
                break;

            case R.id.bt_proximo2:
                //region verificaDados
                if (et_register_nome.getText().toString().isEmpty()) {
                    et_register_nome.setError(msg_erro);
                    b = false;
                }

                if (et_register_nascimento.getText().toString().isEmpty()) {
                    et_register_nascimento.setError(msg_erro);
                    b = false;
                }

                if (b) {
                    tela2To3();
                }
                //endregion
                break;

            case R.id.bt_voltar2:
                tela2To1();
                break;

            case R.id.bt_voltar3:
                tela3To2();
                break;

            case R.id.bt_register:
                //region verificaDados
                if (et_register_cep.getText().toString().isEmpty()) {
                    et_register_cep.setError(msg_erro);
                    b = false;
                }

                if (et_register_complemento.getText().toString().isEmpty()) {
                    et_register_complemento.setError(msg_erro);
                    b = false;
                }

                if (et_register_logradouro.getText().toString().isEmpty()) {
                    et_register_logradouro.setError(msg_erro);
                    b = false;
                }

                if (b) {
                    Endereco endereco2 = new Endereco();
                    endereco2.setCep(et_register_cep.getText().toString());
                    endereco2.setComplemento(et_register_complemento.getText().toString());
                    endereco2.setLogradouro(et_register_logradouro.getText().toString());
                    paciente.setEndereco(endereco2);

                    registrarPaciente();
                }

                //endregion
                break;

            case R.id.switch_register_sexo:
                if (!masc) {
                    masc = true;
                } else if (masc) {
                    masc = false;
                }
                break;
        }
    }

    private void registrarPaciente() {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Registrando...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth = FirebaseConfig.getmAuth();

        mAuth.createUserWithEmailAndPassword(paciente.getEmail(), et_register_password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final Requests requests = Connection.createService(Requests.class);
                            Call<Void> call = requests.salvarPaciente(paciente);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        SaveEmailOnMemory saveEmailOnMemory = new SaveEmailOnMemory(paciente.getEmail());
                                        saveEmailOnMemory.saveEmail(context);

                                        MainData.getInstance().setPaciente(paciente);

                                        ((MainActivity) context).pushFragmentWithStack(new ProfileFragment(), "ProfileFragment");
                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(context, "Erro ao registrar usuário", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(context, "Falha ao registrar usuário", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        } else {
                            String erro = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erro = "Escolha uma senha que contenha, letras e números.";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erro = "Email indicado não é válido.";
                            } catch (FirebaseAuthUserCollisionException e) {
                                erro = "Já existe uma conta com esse e-mail.";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            progressDialog.dismiss();
                            Toast.makeText(context, "Erro ao cadastrar usuário: " + erro, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void tela1To2() {
        paciente.setEmail(et_register_email.getText().toString());

        View view2 = mInflater.inflate(R.layout.fragment_register_user_step2, mContainer, false);
        bt_proximo2 = (Button) view2.findViewById(R.id.bt_proximo2);
        bt_voltar2 = (Button) view2.findViewById(R.id.bt_voltar2);
        et_register_nome = (EditText) view2.findViewById(R.id.et_register_nome);
        et_register_nascimento = (EditText) view2.findViewById(R.id.et_register_nascimento);
        switch_register_sexo = (Switch) view2.findViewById(R.id.switch_register_sexo);

        if (paciente.getNome() != null && paciente.getIdade() != null && paciente.getSexo() != null) {
            et_register_nome.setText(paciente.getNome());
            et_register_nascimento.setText(dateFormat.format(paciente.getIdade()));
            if (paciente.getSexo() == TipoSexo.FEM) {
                switch_register_sexo.setChecked(true);
            }
        }

        et_register_nascimento.addTextChangedListener(MaskEditUtil.mask(et_register_nascimento, MaskEditUtil.FORMAT_DATE));

        switch_register_sexo.setOnClickListener(this);
        bt_proximo2.setOnClickListener(this);
        bt_voltar2.setOnClickListener(this);

        placeholder.removeAllViews();
        placeholder.addView(view2);
    }

    private void tela2To3() {
        paciente.setNome(et_register_nome.getText().toString());

        try {
            paciente.setIdade(dateFormat.parse("22/05/1996"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (masc) {
            paciente.setSexo(TipoSexo.MASC);
        } else {
            paciente.setSexo(TipoSexo.FEM);
        }

        View view3 = mInflater.inflate(R.layout.fragment_register_user_step3, mContainer, false);
        bt_voltar3 = (Button) view3.findViewById(R.id.bt_voltar3);
        bt_register = (Button) view3.findViewById(R.id.bt_register);
        et_register_cep = (EditText) view3.findViewById(R.id.et_register_cep);
        et_register_cep.addTextChangedListener(MaskEditUtil.mask(et_register_cep, MaskEditUtil.FORMAT_CEP));
        et_register_complemento = (EditText) view3.findViewById(R.id.et_register_complemento);
        et_register_logradouro = (EditText) view3.findViewById(R.id.et_register_logradouro);
        bt_voltar3.setOnClickListener(this);
        bt_register.setOnClickListener(this);

        if (paciente.getEndereco() != null) {
            et_register_cep.setText(paciente.getEndereco().getCep());
            et_register_complemento.setText(paciente.getEndereco().getComplemento());
            et_register_logradouro.setText(paciente.getEndereco().getLogradouro());
        }

        placeholder.removeAllViews();
        placeholder.addView(view3);

    }

    private void tela3To2() {
        Endereco endereco = new Endereco();
        endereco.setCep(et_register_cep.getText().toString());
        endereco.setComplemento(et_register_complemento.getText().toString());
        endereco.setLogradouro(et_register_logradouro.getText().toString());
        paciente.setEndereco(endereco);

        View view2_2 = mInflater.inflate(R.layout.fragment_register_user_step2, mContainer, false);
        bt_proximo2 = (Button) view2_2.findViewById(R.id.bt_proximo2);
        bt_voltar2 = (Button) view2_2.findViewById(R.id.bt_voltar2);
        et_register_nome = (EditText) view2_2.findViewById(R.id.et_register_nome);
        et_register_nascimento = (EditText) view2_2.findViewById(R.id.et_register_nascimento);
        et_register_nascimento.addTextChangedListener(MaskEditUtil.mask(et_register_nascimento, MaskEditUtil.FORMAT_DATE));
        switch_register_sexo = (Switch) view2_2.findViewById(R.id.switch_register_sexo);
        bt_proximo2.setOnClickListener(this);
        bt_voltar2.setOnClickListener(this);
        switch_register_sexo.setOnClickListener(this);

        if (paciente.getSexo() == TipoSexo.FEM) {
            switch_register_sexo.setChecked(true);
        }
        et_register_nome.setText(paciente.getNome());
        et_register_nascimento.setText(dateFormat.format(paciente.getIdade()));

        placeholder.removeAllViews();
        placeholder.addView(view2_2);
    }

    private void tela2To1() {
        paciente.setNome(et_register_nome.getText().toString());
        paciente.setIdade(new Date());
        if (masc) {
            paciente.setSexo(TipoSexo.MASC);
        } else {
            paciente.setSexo(TipoSexo.FEM);
        }

        View view1 = mInflater.inflate(R.layout.fragment_register_user_step1, mContainer, false);

        bt_proximo1 = (Button) view1.findViewById(R.id.bt_proximo1);
        et_register_email = (EditText) view1.findViewById(R.id.et_register_email);
        et_register_password = (EditText) view1.findViewById(R.id.et_register_password);
        et_register_password_confirm = (EditText) view1.findViewById(R.id.et_register_password_confirm);
        bt_proximo1.setOnClickListener(this);

        et_register_email.setText(paciente.getEmail());

        placeholder.removeAllViews();
        placeholder.addView(view1);
    }
}
