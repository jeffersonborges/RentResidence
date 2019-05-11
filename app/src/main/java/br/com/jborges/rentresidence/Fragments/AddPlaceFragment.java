/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package br.com.jborges.rentresidence.Fragments;


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

        if(bundle != null){
            posicao = bundle.getInt("POSICAO", 0);
            id = bundle.getLong("ID",0);
            operacao = bundle.getString("operacao");
        }else{
            operacao = "inserir";
        }

        if(operacao.equalsIgnoreCase("inserir"))
            ((MenuActivity)getActivity()).getSupportActionBar().setTitle("Inserir imóvel");
        else
            ((MenuActivity)getActivity()).getSupportActionBar().setTitle("Alterar dados");


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

        if(operacao.equals("alterar")){
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
        if (operacao.equals("inserir")) {

            Imovel umImovel = new Imovel();

            umImovel.endereco = etEndereco.getText().toString();
            umImovel.numero = etNumero.getText().toString();
            umImovel.bairro = etBairro.getText().toString();
            umImovel.cidade = etCidade.getText().toString();
            umImovel.estado = etEstado.getText().toString();
            umImovel.imobiliaria = etImobiliaria.getText().toString();
            umImovel.telefone = etTelefone.getText().toString();

            if (imovelDAO.inserir(umImovel)) {
                Toast.makeText(getActivity(), "Gravado com sucesso!", Toast.LENGTH_SHORT).show();
                fechar();
            } else {
                Toast.makeText(getActivity(), "Operação não realizada!", Toast.LENGTH_SHORT).show();
            }
        }else if(operacao.equals("alterar")){
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
                Toast.makeText(getActivity(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                fechar();
            } else {
                Toast.makeText(getActivity(), "Operação não realizada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void excluir() {
        if(operacao.equals("alterar")){
            if ( imovelDAO.excluir(id)) {
                Toast.makeText(getActivity(), "Excluído com sucesso!", Toast.LENGTH_SHORT).show();
                fechar();
            } else {
                Toast.makeText(getActivity(), "Operação não realizada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void fechar(){
        if(getActivity() != null){
            ((MenuActivity)getActivity()).openPlaceListingFragment();
        }

        //Toast.makeText(getActivity(), "Cliquei no botão fechar!", Toast.LENGTH_SHORT).show();
    }
}
