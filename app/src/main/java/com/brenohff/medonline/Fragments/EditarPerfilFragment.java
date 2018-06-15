package com.brenohff.medonline.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brenohff.medonline.FirebaseConfig.FirebaseConfig;
import com.brenohff.medonline.Memory.MainData;
import com.brenohff.medonline.R;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditarPerfilFragment extends Fragment {

    private Context context;

    private CircleImageView editar_perfil_imagem;
    private EditText editar_perfil_dt_nascimento, editar_perfil_nome;
    private Button editar_perfil_salvar;

    private List<Image> imagem;
    private List<InputStream> inputStream;
    private List<String> imageLinks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);
        context = view.getContext();

        editar_perfil_dt_nascimento = (EditText) view.findViewById(R.id.editar_perfil_dt_nascimento);
        editar_perfil_nome = (EditText) view.findViewById(R.id.editar_perfil_nome);
        editar_perfil_imagem = (CircleImageView) view.findViewById(R.id.editar_perfil_imagem);
        editar_perfil_salvar = (Button) view.findViewById(R.id.editar_perfil_salvar);

        editar_perfil_imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImageChooserActivity();
            }
        });

        editar_perfil_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        castFields();

        return view;
    }

    private void castFields() {
        if (MainData.getInstance().getPaciente() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            editar_perfil_nome.setText(MainData.getInstance().getPaciente().getNome());
            editar_perfil_dt_nascimento.setText(simpleDateFormat.format(MainData.getInstance().getPaciente().getDtNascimento()));
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            editar_perfil_nome.setText(MainData.getInstance().getMedico().getNome());
            editar_perfil_dt_nascimento.setText(simpleDateFormat.format(MainData.getInstance().getMedico().getDtNascimento()));
        }
    }

    //region IMAGEM
    //--------------------------------- IMAGEM -----------------------------------

    private void startImageChooserActivity() {

        Intent intent = new Intent(context, AlbumSelectActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
        startActivityForResult(intent, Constants.REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imagem = new ArrayList<>();
        inputStream = new ArrayList<>();

        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imagem = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
        }

        for (int i = 0; i < imagem.size(); i++) {
            try {
                inputStream.add(new FileInputStream(new File(imagem.get(i).path)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        confirmaEnvio();
    }

    private void confirmaEnvio() {
        if (inputStream != null && inputStream.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Deseja carregar esta imagem?");
            builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    uploadFile();
                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });

            AlertDialog alerta = builder.create();
            alerta.show();
        }
    }

    private void uploadFile() {

        if (inputStream != null) {

            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Enviando...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            imageLinks = new ArrayList<>();

            for (int x = 0; x < inputStream.size(); x++) {

                StorageReference riversRef = FirebaseConfig.getStorage().getReference().child("imagens_usuarios/" +
                        FirebaseConfig.getmAuth().getCurrentUser().getUid() + ".jpg");

                riversRef.putStream(inputStream.get(x))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

//                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                                imageLinks.add(taskSnapshot.getDownloadUrl().toString());
//
//                                if (imageLinks.size() == inputStream.size()) {
//                                    progressDialog.dismiss();
//                                    user.setImagem(imageLinks.get(0));
//                                    Toast.makeText(context, "Imagem atualizada!", Toast.LENGTH_SHORT).show();
//                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                            }
                        });
            }
        }
    }

    //endregion

}
