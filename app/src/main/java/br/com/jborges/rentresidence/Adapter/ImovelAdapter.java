package br.com.jborges.rentresidence.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.jborges.rentresidence.Activity.MenuActivity;
import br.com.jborges.rentresidence.Entidade.Imovel;
import br.com.jborges.rentresidence.Persistencia.ImovelDAO;
import br.com.jborges.rentresidence.R;

/**
 * Jefferson Borges - 2019
 */

public class ImovelAdapter extends RecyclerView.Adapter<ImovelAdapter.ViewHolder> {

    private List<Imovel> imoveis;
    public Context context;

    String operacao;
    int posicao;
    Long id;

    ImageButton btnExcluir;

    ImovelDAO imovelDAO;

    public ImovelAdapter(List<Imovel> imoveis) {

        this.imoveis = imoveis;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.linha_imoveis, parent, false);

//        Bundle bundle = this.getArguments();
//
//        if(bundle != null){
//            posicao = bundle.getInt("POSICAO", 0);
//            id = bundle.getLong("ID",0);
//            operacao = bundle.getString("operacao");
//        }else{
//            operacao = "inserir";
//        }


        btnExcluir = view.findViewById(R.id.ib_deletar);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluir();
            }
        });

        return new ViewHolder(view);
    }

    public void excluir() {
        operacao = "excluir";
        if(operacao.equals("excluir")){
            if ( imovelDAO.excluir(id)) {
                Toast.makeText(context, "Excluído com sucesso!", Toast.LENGTH_SHORT).show();
                fechar();
            } else {
                Toast.makeText(context, "Operação não realizada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void fechar(){
        if(context != null){
            ((MenuActivity)context).openPlaceListingFragment();
        }

        //Toast.makeText(getActivity(), "Cliquei no botão fechar!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(imoveis.get(position));

    }

    @Override
    public int getItemCount() {

        return imoveis.size();
    }

    public void setContext(Context context){
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEndereco;
        private  TextView tvBairro;
        private TextView tvCidadeEstado;
        private TextView tvImobiliaria;
        private TextView tvTelefoneImobiliaria;

        private ViewHolder(final View itemView) {
            super(itemView);

            tvEndereco = (TextView) itemView.findViewById(R.id.tvEndereco);
            tvBairro = (TextView) itemView.findViewById(R.id.tvBairro);
            tvCidadeEstado = (TextView) itemView.findViewById(R.id.tvCidadeEstado);
            tvImobiliaria = (TextView) itemView.findViewById(R.id.tvImobiliaria);
            tvTelefoneImobiliaria = (TextView) itemView.findViewById(R.id.tvTelefoneImobiliaria);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(context != null && context.getClass().equals(MenuActivity.class)){
                        ((MenuActivity) context).openPlaceDetailsFragment(imoveis.get(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }

        private void setData(Imovel imoveis) {

            tvEndereco.setText("Endereço: " + imoveis.endereco + ", " + imoveis.numero);
            tvBairro.setText("Bairro: " + imoveis.bairro);
            tvCidadeEstado.setText("Cidade/UF: " +imoveis.cidade + " - " + imoveis.estado);
            tvImobiliaria.setText("Imobiliária: " + imoveis.imobiliaria);
            tvTelefoneImobiliaria.setText("Telefone Imobiliária: " +imoveis.telefone);
        }
    }
}