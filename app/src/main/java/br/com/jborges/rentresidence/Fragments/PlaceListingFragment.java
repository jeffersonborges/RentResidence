package br.com.jborges.rentresidence.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.jborges.rentresidence.Activity.MenuActivity;
import br.com.jborges.rentresidence.Adapter.ImovelAdapter;
import br.com.jborges.rentresidence.Entidade.Imovel;
import br.com.jborges.rentresidence.Persistencia.ImovelDAO;
import br.com.jborges.rentresidence.R;

/**
 * Jefferson Borges - 2019
 */

public class PlaceListingFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TextView tvEmptyList;
    private List<Imovel> listaImoveis = new ArrayList<>();
    ImovelDAO imovelDAO;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_place_listing, container, false);

        imovelDAO = new ImovelDAO(getActivity());

        listaImoveis = imovelDAO.listar();

        tvEmptyList = view.findViewById(R.id.empty_list);
        if(listaImoveis.isEmpty()){
            tvEmptyList.setVisibility(View.VISIBLE);
        }else{
            tvEmptyList.setVisibility(View.GONE);
        }

        //Configuração do Recycler
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_place_listing);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                ImovelAdapter adapter = (ImovelAdapter) mRecyclerView.getAdapter();

            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        ImovelAdapter adapter = new ImovelAdapter(listaImoveis);
        adapter.setContext((MenuActivity)getActivity());
        mRecyclerView.setAdapter(adapter);

        return view;
    }

}
