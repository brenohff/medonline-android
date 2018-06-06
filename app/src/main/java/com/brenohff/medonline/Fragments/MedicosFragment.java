package com.brenohff.medonline.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brenohff.medonline.Adapters.MedicosAdapter;
import com.brenohff.medonline.Others.ItemClickSupport;
import com.brenohff.medonline.R;

public class MedicosFragment extends Fragment {

    private Context context;

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicos, container, false);
        context = view.getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.medico_recyclerView);
        inflateRecyclerView();

        return view;
    }

    private void inflateRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        });
        recyclerView.setAdapter(new MedicosAdapter());
    }

}
