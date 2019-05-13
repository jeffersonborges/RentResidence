package br.com.jborges.rentresidence.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import br.com.jborges.rentresidence.Activity.MainActivity;
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
    private AlertDialog alerta;

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

    public void excluir(final Long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());//Cria o gerador do AlertDialog
        builder.setTitle(getString(R.string.confirmacao_exclusao));//define o titulo
        builder.setMessage(getString(R.string.deseja_excluir_registro));//define a mensagem

        //define um botão como positivo
        builder.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if (imovelDAO.excluir(id)) {
                    Toast.makeText(getContext(), getString(R.string.excluido_sucesso), Toast.LENGTH_SHORT).show();
                    listaImoveis = imovelDAO.listar();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.operacao_nao_realizada), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton(getString(R.string.nao), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alerta = builder.create();//cria o AlertDialog
        alerta.show();//Exibe
    }

    public void compartilhar(Long id) {

        String mensagemEnviar;

        mensagemEnviar = "";
        mensagemEnviar = mensagemEnviar + getString(R.string.compartilhado_rent_residence) + "\n" +
                getString(R.string.endereco) + imovelDAO.localizaImovelPorId(id).endereco.trim() + ", " +
                imovelDAO.localizaImovelPorId(id).numero.trim() + "\n" +
                getString(R.string.bairro) + imovelDAO.localizaImovelPorId(id).bairro.trim() + "\n" +
                getString(R.string.cidade_uf) + imovelDAO.localizaImovelPorId(id).cidade.trim() + " - " +
                imovelDAO.localizaImovelPorId(id).estado.trim() + "\n" +
                getString(R.string.imobiliaria) + imovelDAO.localizaImovelPorId(id).imobiliaria.trim() + "\n" +
                getString(R.string.telefone_imobiliaria) + imovelDAO.localizaImovelPorId(id).telefone.trim();

        enviarWhatsApp(mensagemEnviar);

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
            Toast.makeText(getActivity(), getString(R.string.whatsapp_nao_instalado), Toast.LENGTH_SHORT).show();
        }
    }

}