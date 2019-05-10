package br.com.jborges.rentresidence.Entidade;

/**
 * Jefferson Borges - 2019
 */

public class Imovel {
    public static final String TABELA = "imovel";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_ENDERECO = "endereco";
    public static final String CAMPO_NUMERO = "numero";
    public static final String CAMPO_BAIRRO = "bairro";
    public static final String CAMPO_CIDADE = "cidade";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_IMOBILIARIA = "imobiliaria";
    public static final String CAMPO_TELEFONE = "telefone";
    public static final String CAMPO_GEOLOCALIZACAO = "geolocalizacao";

    public Long id;
    public String endereco;
    public String numero;
    public String bairro;
    public String cidade;
    public String estado;
    public String imobiliaria;
    public String telefone;
    public String geolocalizacao;

}
