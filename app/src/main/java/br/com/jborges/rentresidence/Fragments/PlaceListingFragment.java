package br.com.jborges.rentresidence.Fragments;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    ImovelAdapter adapter;
    ImovelDAO imovelDAO;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_place_listing, container, false);

        imovelDAO = new ImovelDAO(getActivity());

        listaImoveis = imovelDAO.listar();

        tvEmptyList = view.findViewById(R.id.empty_list);
        if (listaImoveis.isEmpty()) {
            tvEmptyList.setVisibility(View.VISIBLE);
        } else {
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

        adapter = new ImovelAdapter(listaImoveis, this);
        adapter.setContext((MenuActivity) getActivity());
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    public void excluir(Long id) {
        if (imovelDAO.excluir(id)) {
            Toast.makeText(this.getActivity(), "Excluído com sucesso!", Toast.LENGTH_SHORT).show();
            listaImoveis = imovelDAO.listar();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "Operação não realizada!", Toast.LENGTH_SHORT).show();
        }
    }

    public void compartilhar(Long id) {
//        Toast.makeText(getActivity(), "Vou compartilhar!", Toast.LENGTH_SHORT).show();
//        enviarWhatsApp("Teste de WhatsApp Jefferson");

        listaImoveis = imovelDAO.listar();
        String mensagemEnviar;
    }

    public void enviarWhatsApp(String mensagem) {
        PackageManager pm = getContext().getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = mensagem;

            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(waIntent);

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getActivity(), "WhatsApp não instalado", Toast.LENGTH_SHORT).show();
        }
    }

}
