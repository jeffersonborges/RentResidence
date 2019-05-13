package br.com.jborges.rentresidence.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.jborges.rentresidence.Activity.MainActivity;
import br.com.jborges.rentresidence.Activity.MenuActivity;
import br.com.jborges.rentresidence.Entidade.Imovel;
import br.com.jborges.rentresidence.Fragments.AddPlaceFragment;
import br.com.jborges.rentresidence.Fragments.PlaceListingFragment;
import br.com.jborges.rentresidence.Persistencia.ImovelDAO;
import br.com.jborges.rentresidence.R;

/**
 * Jefferson Borges - 2019
 */

public class ImovelAdapter extends RecyclerView.Adapter<ImovelAdapter.ViewHolder> {

    private List<Imovel> imoveis;
    public Context context;
    PlaceListingFragment fragment;
    private AlertDialog alerta;

    String operacao;
    int posicao;
    Long id;

    ImageButton btnExcluir;

    ImovelDAO imovelDAO;

    public ImovelAdapter(List<Imovel> imoveis, PlaceListingFragment fragment) {
        this.fragment = fragment;
        this.imoveis = imoveis;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.linha_imoveis, parent, false);

        return new ViewHolder(view);
    }

    public void fechar() {
        if (context != null) {
            ((MenuActivity) context).openPlaceListingFragment();
        }
        //Toast.makeText(getActivity(), "Cliquei no botão fechar!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setData(imoveis.get(position));

        holder.btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.excluir(imoveis.get(position).id);
            }
        });

        holder.btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.compartilhar(imoveis.get(position).id);
            }
        });

    }

    @Override
    public int getItemCount() {

        return imoveis.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEndereco;
        private TextView tvBairro;
        private TextView tvCidadeEstado;
        private TextView tvImobiliaria;
        private TextView tvTelefoneImobiliaria;
        private ImageButton btnExcluir;
        private ImageButton btnCompartilhar;

        private ViewHolder(final View itemView) {
            super(itemView);

            tvEndereco = (TextView) itemView.findViewById(R.id.tvEndereco);
            tvBairro = (TextView) itemView.findViewById(R.id.tvBairro);
            tvCidadeEstado = (TextView) itemView.findViewById(R.id.tvCidadeEstado);
            tvImobiliaria = (TextView) itemView.findViewById(R.id.tvImobiliaria);
            tvTelefoneImobiliaria = (TextView) itemView.findViewById(R.id.tvTelefoneImobiliaria);
            btnExcluir = itemView.findViewById(R.id.ib_deletar);
            btnCompartilhar = itemView.findViewById(R.id.ib_compartilhar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (context != null && context.getClass().equals(MenuActivity.class)) {
                        ((MenuActivity) context).openPlaceDetailsFragment(imoveis.get(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }

        private void setData(Imovel imoveis) {

            tvEndereco.setText(context.getString(R.string.endereco) + imoveis.endereco.trim() + ", " + imoveis.numero.trim());
            tvBairro.setText(context.getString(R.string.bairro) + imoveis.bairro.trim());
            tvCidadeEstado.setText(context.getString(R.string.cidade_uf) + imoveis.cidade.trim() + " - " + imoveis.estado.trim());
            tvImobiliaria.setText(context.getString(R.string.imobiliaria) + imoveis.imobiliaria.trim());
            tvTelefoneImobiliaria.setText(context.getString(R.string.telefone_imobiliaria) + imoveis.telefone.trim());
        }
    }
}