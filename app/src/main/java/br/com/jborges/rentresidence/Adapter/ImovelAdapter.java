package br.com.jborges.rentresidence.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.jborges.rentresidence.Activity.MenuActivity;
import br.com.jborges.rentresidence.Entidade.Imovel;
import br.com.jborges.rentresidence.R;

/**
 * Jefferson Borges - 2019
 */

public class ImovelAdapter extends RecyclerView.Adapter<ImovelAdapter.ViewHolder> {
    private List<Imovel> imoveis;

    public Context context;


    public ImovelAdapter(List<Imovel> imoveis) {

        this.imoveis = imoveis;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.linha_imoveis, parent, false);
        return new ViewHolder(view);
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

        private ViewHolder(final View itemView) {
            super(itemView);

            tvEndereco = (TextView) itemView.findViewById(R.id.tvEndereco);

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

            tvEndereco.setText("Local: " + imoveis.endereco);

        }
    }
}