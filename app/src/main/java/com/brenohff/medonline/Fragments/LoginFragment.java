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
import android.widget.TextView;
import android.widget.Toast;

import com.brenohff.medonline.Activities.MainActivity;
import com.brenohff.medonline.Connections.Connection;
import com.brenohff.medonline.Connections.Requests;
import com.brenohff.medonline.Domain.Paciente;
import com.brenohff.medonline.FirebaseConfig.FirebaseConfig;
import com.brenohff.medonline.Memory.MainData;
import com.brenohff.medonline.Others.SaveEmailOnMemory;
import com.brenohff.medonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "LOGIN-FRAGMENT";
    private Context context;
    private FirebaseAuth mAuth;

    private EditText et_login_email, et_login_password;
    private Button bt_login;
    private TextView bt_register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        context = view.getContext();

        et_login_email = (EditText) view.findViewById(R.id.et_login_email);
        et_login_password = (EditText) view.findViewById(R.id.et_login_password);
        bt_login = (Button) view.findViewById(R.id.bt_login);
        bt_register = (TextView) view.findViewById(R.id.bt_user_register);

        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.bt_user_register:
                ((MainActivity) context).pushFragmentWithStack(new RegisterUserFragment(), "RegisterUserFragment");
                break;
        }
    }

    private void login() {
        mAuth = FirebaseConfig.getmAuth();

        final String email = et_login_email.getText().toString();
        final String password = et_login_password.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Preencha os campos antes de continuar.", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Fazendo login...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final Requests requests = Connection.createService(Requests.class);
                                Call<Paciente> call = requests.buscaPacientePeloEmail(email);
                                call.enqueue(new Callback<Paciente>() {
                                    @Override
                                    public void onResponse(Call<Paciente> call, Response<Paciente> response) {
                                        if (response.isSuccessful()) {

                                            SaveEmailOnMemory saveEmailOnMemory = new SaveEmailOnMemory(response.body().getEmail());
                                            saveEmailOnMemory.saveEmail(context);

                                            MainData.getInstance().setPaciente(response.body());

                                            ((MainActivity) context).pushFragmentWithNoStack(new ProfileFragment(), "ProfileFragment");
                                            progressDialog.dismiss();
                                        } else {
                                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                            mAuth.signOut();
                                            progressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Paciente> call, Throwable t) {
                                        Toast.makeText(context, "Falha ao realizar login", Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();
                                        progressDialog.dismiss();
                                    }
                                });
                            } else {
                                Toast.makeText(context, "Falha ao realizar login", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }
}
