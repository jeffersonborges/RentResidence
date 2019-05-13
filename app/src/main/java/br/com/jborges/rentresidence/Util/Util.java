package br.com.jborges.rentresidence.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import br.com.jborges.rentresidence.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Jefferson Borges - 2019
 */

public class Util {

    public static boolean verificarInternet(Context context) {

        ConnectivityManager conexao = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo informacao = conexao.getActiveNetworkInfo();

        if (informacao != null && informacao.isConnected()) {

            return true;
        } else {
            return false;
        }
    }

    public static void opcoesErro(Context context, String resposta) {

        if (resposta.contains("least 6 characters")) {
            Toast.makeText(context, context.getString(R.string.senha_maior_6_caracteres), Toast.LENGTH_LONG).show();
        } else if (resposta.contains("address is badly")) {
            Toast.makeText(context, context.getString(R.string.email_invalido), Toast.LENGTH_LONG).show();
        } else if (resposta.contains("interrupted connection")) {
            Toast.makeText(context, context.getString(R.string.sem_conexao_firebase), Toast.LENGTH_LONG).show();
        } else if (resposta.contains("password is invalid")) {
            Toast.makeText(context, context.getString(R.string.senha_invalida), Toast.LENGTH_LONG).show();
        } else if (resposta.contains("There is no user")) {
            Toast.makeText(context, context.getString(R.string.email_nao_cadastrado), Toast.LENGTH_LONG).show();
        } else if (resposta.contains("address is already")) {
            Toast.makeText(context, context.getString(R.string.email_ja_cadastrado), Toast.LENGTH_LONG).show();
        } else if (resposta.contains("INVALID_EMAIL")) {
            Toast.makeText(context, context.getString(R.string.email_invalido), Toast.LENGTH_LONG).show();
        } else if (resposta.contains("EMAIL_NOT_FOUND")) {
            Toast.makeText(context, context.getString(R.string.email_nao_cadastrado), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
        }
    }

}
