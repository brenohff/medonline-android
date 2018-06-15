package com.brenohff.medonline.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.brenohff.medonline.Connections.Connection;
import com.brenohff.medonline.Connections.Requests;
import com.brenohff.medonline.Domain.Medico;
import com.brenohff.medonline.Domain.Paciente;
import com.brenohff.medonline.Fragments.LoginFragment;
import com.brenohff.medonline.Fragments.MedicosFragment;
import com.brenohff.medonline.Fragments.ProfileFragment;
import com.brenohff.medonline.Memory.MainData;
import com.brenohff.medonline.Others.SaveEmailOnMemory;
import com.brenohff.medonline.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static Requests requests = Connection.createService(Requests.class);
    private BottomNavigationView navigation;
    boolean doubleBackToExitPressedOnce = false;

    //Verifica se o usuário realmente quer sair do aplicativo =D
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Pressione novamente para sair...", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            popFragment(1);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (SaveEmailOnMemory.loadEmail(MainActivity.this) == null) {
                        changeFragment(new LoginFragment(), "LoginFragment");
                    } else if (SaveEmailOnMemory.loadEmail(MainActivity.this) != null && MainData.getInstance().getPaciente() != null) {
                        changeFragment(new ProfileFragment(), "ProfileFragment");
                    } else {
                        if (SaveEmailOnMemory.loadEmail(MainActivity.this).getTipo_usuario().equals("paciente")) {
                            carregaPaciente();
                        } else {
                            carregaMedico();
                        }
                    }
                    return true;
                case R.id.navigation_dashboard:
                    changeFragment(new MedicosFragment(), "MedicosFragment");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        changeStatusBarColor(R.color.buttons_light_blue);
        setFragment(2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void changeStatusBarColor(Integer color) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            final Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(color));
        }
    }

    //region FRAGMENT

    public void setFragment(Integer position) {
        switch (position) {
            case 1:
                navigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 2:
                navigation.setSelectedItemId(R.id.navigation_dashboard);
                break;
        }
    }

    public void pushFragmentWithStack(Fragment fragment, String tag) {
        this.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.fragment_slide_left_enter,
                        R.animator.fragment_slide_left_exit,
                        R.animator.fragment_slide_right_enter,
                        R.animator.fragment_slide_right_exit)
                .replace(R.id.main_container, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    public void pushFragmentWithNoStack(Fragment fragment, String tag) {
        this.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.fragment_slide_left_enter,
                        R.animator.fragment_slide_left_exit,
                        R.animator.fragment_slide_right_enter,
                        R.animator.fragment_slide_right_exit)
                .replace(R.id.main_container, fragment, tag)
                .commit();
    }

    public void popFragment(Integer qtd) {
        final FragmentManager mFragmentManager = this.getSupportFragmentManager();
        for (int i = 0; i < qtd; i++) {
            mFragmentManager.popBackStack();
        }
    }

    private void changeFragment(Fragment fragment, String tag) {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            pushFragmentWithNoStack(fragment, tag);
        } else {
            for (int i = 0; i <= getSupportFragmentManager().getBackStackEntryCount(); i++) {
                if (i == getSupportFragmentManager().getBackStackEntryCount()) {
                    pushFragmentWithNoStack(fragment, tag);
                } else {
                    popFragment(1);
                }
            }
        }
    }

    //endregion

    private void carregaPaciente() {
        Requests requests = Connection.createService(Requests.class);
        Call<Paciente> call = requests.buscaPacientePeloEmail(SaveEmailOnMemory.loadEmail(MainActivity.this).getEmail());
        call.enqueue(new Callback<Paciente>() {
            @Override
            public void onResponse(Call<Paciente> call, Response<Paciente> response) {
                if (response.isSuccessful()) {
                    MainData.getInstance().setPaciente(response.body());
                    changeFragment(new ProfileFragment(), "ProfileFragment");
                } else {
                    changeFragment(new LoginFragment(), "LoginFragment");
                    SaveEmailOnMemory.removeEmail(MainActivity.this);
                }
            }

            @Override
            public void onFailure(Call<Paciente> call, Throwable t) {
                changeFragment(new LoginFragment(), "LoginFragment");
            }
        });
    }

    private void carregaMedico() {
        Requests requests = Connection.createService(Requests.class);
        Call<Medico> call = requests.buscaMedicoPeloEmail(SaveEmailOnMemory.loadEmail(MainActivity.this).getEmail());
        call.enqueue(new Callback<Medico>() {
            @Override
            public void onResponse(Call<Medico> call, Response<Medico> response) {
                if (response.isSuccessful()) {
                    MainData.getInstance().setMedico(response.body());
                    changeFragment(new ProfileFragment(), "ProfileFragment");
                } else {
                    changeFragment(new LoginFragment(), "LoginFragment");
                    SaveEmailOnMemory.removeEmail(MainActivity.this);
                }
            }

            @Override
            public void onFailure(Call<Medico> call, Throwable t) {
                changeFragment(new LoginFragment(), "LoginFragment");
            }
        });
    }

}
