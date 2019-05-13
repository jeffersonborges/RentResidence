package br.com.jborges.rentresidence.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jborges.rentresidence.Activity.MenuActivity;
import br.com.jborges.rentresidence.Entidade.Imovel;
import br.com.jborges.rentresidence.Persistencia.ImovelDAO;
import br.com.jborges.rentresidence.R;

/**
 * Jefferson Borges - 2019
 */

public class AddPlaceFragment extends Fragment {

    String operacao;
    int posicao;
    Long id;
    EditText etEndereco, etNumero, etBairro, etCidade, etEstado, etImobiliaria, etTelefone;
    Button btnSalvar, btnFechar, btnExcluir;

    private AlertDialog alerta;

    ImovelDAO imovelDAO;

    public AddPlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_place, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            posicao = bundle.getInt("POSICAO", 0);
            id = bundle.getLong("ID", 0);
            operacao = bundle.getString("operacao");
        } else {
            operacao = "inserir";
        }

        if (operacao.equalsIgnoreCase("inserir"))
            ((MenuActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.inserir_imovel));
        else
            ((MenuActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.alterar_dados));


        etEndereco = view.findViewById(R.id.etEndereco);
        etNumero = view.findViewById(R.id.etNumero);
        etBairro = view.findViewById(R.id.etBairro);
        etCidade = view.findViewById(R.id.etCidade);
        etEstado = view.findViewById(R.id.etEstado);
        etImobiliaria = view.findViewById(R.id.etImobiliaria);
        etTelefone = view.findViewById(R.id.etTelefone);
        btnSalvar = view.findViewById(R.id.btn_salvar);
        btnExcluir = view.findViewById(R.id.btn_excluir);
        btnFechar = view.findViewById(R.id.btn_fechar);

        if (operacao.equals("alterar")) {
            Imovel imovel = ImovelDAO.listaImoveis.get(posicao);

            etEndereco.setText(imovel.endereco);
            etNumero.setText(imovel.numero);
            etBairro.setText(imovel.bairro);
            etCidade.setText(imovel.cidade);
            etEstado.setText(imovel.estado);
            etImobiliaria.setText(imovel.imobiliaria);
            etTelefone.setText(imovel.telefone);
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluir();
            }
        });

        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fechar();
            }
        });

        imovelDAO = new ImovelDAO(getActivity());

        return view;
    }

    public void salvar() {

        if (etEndereco.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.endereco_obrigatorio), Toast.LENGTH_SHORT).show();
        } else if (etNumero.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.campo_numero_obrigario), Toast.LENGTH_SHORT).show();
        } else if (etTelefone.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.campo_telefone_obrigatorio), Toast.LENGTH_SHORT).show();
        } else if (operacao.equals("inserir")) {
            Imovel umImovel = new Imovel();

            umImovel.endereco = etEndereco.getText().toString();
            umImovel.numero = etNumero.getText().toString();
            umImovel.bairro = etBairro.getText().toString();
            umImovel.cidade = etCidade.getText().toString();
            umImovel.estado = etEstado.getText().toString();
            umImovel.imobiliaria = etImobiliaria.getText().toString();
            umImovel.telefone = etTelefone.getText().toString();

            if (imovelDAO.inserir(umImovel)) {
                Toast.makeText(getActivity(), getString(R.string.gravado_sucesso), Toast.LENGTH_SHORT).show();
                fechar();
            } else {
                Toast.makeText(getActivity(), getString(R.string.operacao_nao_realizada), Toast.LENGTH_SHORT).show();
            }
        } else if (operacao.equals("alterar")) {
            Imovel umImovel = new Imovel();
            umImovel.id = id;
            umImovel.endereco = etEndereco.getText().toString();
            umImovel.numero = etNumero.getText().toString();
            umImovel.bairro = etBairro.getText().toString();
            umImovel.cidade = etCidade.getText().toString();
            umImovel.estado = etEstado.getText().toString();
            umImovel.imobiliaria = etImobiliaria.getText().toString();
            umImovel.telefone = etTelefone.getText().toString();

            if (imovelDAO.alterar(umImovel)) {
                Toast.makeText(getActivity(), getString(R.string.gravado_sucesso), Toast.LENGTH_SHORT).show();
                fechar();
            } else {
                Toast.makeText(getActivity(), getString(R.string.operacao_nao_realizada), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void excluir() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());//Cria o gerador do AlertDialog
        builder.setTitle(getString(R.string.confirmacao_exclusao));//define o titulo
        builder.setMessage(getString(R.string.deseja_excluir_registro));//define a mensagem

        //define um botão como positivo
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                if (operacao.equals("alterar")) {
                    if (imovelDAO.excluir(id)) {
                        Toast.makeText(getActivity(), R.string.excluido_sucesso, Toast.LENGTH_SHORT).show();
                        fechar();
                    } else {
                        Toast.makeText(getActivity(), R.string.operacao_nao_realizada, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alerta = builder.create();//cria o AlertDialog
        alerta.show();//Exibe
    }

    public void fechar() {
        if (getActivity() != null) {
            ((MenuActivity) getActivity()).openPlaceListingFragment();
        }

    }
}
