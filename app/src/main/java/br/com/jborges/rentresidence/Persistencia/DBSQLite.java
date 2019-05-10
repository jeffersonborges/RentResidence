package br.com.jborges.rentresidence.Persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.jborges.rentresidence.Entidade.Imovel;

/**
 * Jefferson Borges - 2019
 */

public class DBSQLite extends SQLiteOpenHelper {
    private static final String NOME_BASE = "imovel.db";
    private static final int VERSAO = 1;

    public DBSQLite(Context context) {
        super(context, NOME_BASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CRIA_TABELA_IMOVEL = "CREATE TABLE " + Imovel.TABELA  + "("

                + Imovel.CAMPO_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Imovel.CAMPO_ENDERECO + " TEXT, "
                + Imovel.CAMPO_NUMERO + " TEXT, "
                + Imovel.CAMPO_BAIRRO + " TEXT, "
                + Imovel.CAMPO_CIDADE + " TEXT, "
                + Imovel.CAMPO_ESTADO + " TEXT, "
                + Imovel.CAMPO_IMOBILIARIA + " TEXT, "
                + Imovel.CAMPO_TELEFONE + " TEXT, "
                + Imovel.CAMPO_GEOLOCALIZACAO + " TEXT )";

        db.execSQL(CRIA_TABELA_IMOVEL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
