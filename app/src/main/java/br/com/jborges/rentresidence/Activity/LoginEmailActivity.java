/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package br.com.jborges.rentresidence.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.jborges.rentresidence.R;
import br.com.jborges.rentresidence.Util.Util;

/**
 * Jefferson Borges - 2019
 */

public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_Email, editText_Senha;
    private Button button_Login, button_RecuperarSenha;
    private CheckBox checkBoxManterConectado;
    private FirebaseAuth auth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginemail);

        editText_Email = (EditText) findViewById(R.id.editText_Emaillogin);
        editText_Senha = (EditText) findViewById(R.id.editText_PasswordLogin);
        button_Login = (Button) findViewById(R.id.button_OkLogin);
        button_RecuperarSenha = (Button) findViewById(R.id.button_Recuperar);

        button_Login.setOnClickListener(this);
        button_RecuperarSenha.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_OkLogin:
                loginEmail();
                break;

            case R.id.button_Recuperar:
                recuperarSenha();
                break;
        }
    }

    private void recuperarSenha() {

        String email = editText_Email.getText().toString().trim();
        if (email.isEmpty()) {

            Toast.makeText(getBaseContext(), "Insira o seu e-mail para poder recuperar sua senha", Toast.LENGTH_LONG).show();
        } else {
            enviarEmail(email);
        }
    }

    private void enviarEmail(String email) {

        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getBaseContext(), "Enviamos uma mensagem para o seu email com um link para você redefinir a sua senha",
                        Toast.LENGTH_LONG).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String erro = e.toString();
                Util.opcoesErro(getBaseContext(), erro);
            }
        });
    }

    private void loginEmail() {
        String email = editText_Email.getText().toString().trim();
        String senha = editText_Senha.getText().toString().trim();

        dialog = ProgressDialog.show(this, "Autenticação", "Autenticando usuário, por favor, aguarde...",
                false, true);

        if (email.isEmpty() || senha.isEmpty()) {

            dialog.dismiss();
            Toast.makeText(getBaseContext(), "Insira os campos obrigatórios", Toast.LENGTH_LONG).show();
        } else {
            if (Util.verificarInternet(this)) {

                ConnectivityManager conexao = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                confirmarLoginEmail(email, senha);
            } else {
                Toast.makeText(getBaseContext(), "Erro - Verifique a conexão com a sua internet", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void confirmarLoginEmail(String email, String senha) {

        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    startActivity(new Intent(getBaseContext(), MenuActivity.class));
                    finish();
                    dialog.dismiss();
                } else {
                    String resposta = task.getException().toString();
                    Util.opcoesErro(getBaseContext(), resposta);
                    dialog.dismiss();
                }
            }
        });
    }
}