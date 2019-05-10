package br.com.jborges.rentresidence.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.jborges.rentresidence.R;

/**
 * Jefferson Borges - 2019
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_Login, button_Cadastrar, button_Deslogar;
    private FirebaseAuth auth;
    private FirebaseUser user;

    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_Login = (Button) findViewById(R.id.button_login);
        button_Cadastrar = (Button) findViewById(R.id.button_AddUser);
        button_Deslogar = (Button) findViewById(R.id.button_Deslogar);

        button_Login.setOnClickListener(this);
        button_Cadastrar.setOnClickListener(this);
        button_Deslogar.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        estadoAuntenticacao();
    }

    private void estadoAuntenticacao() {

        final TextView usuario = (TextView) findViewById(R.id.TextView_UsuarioLogado);
        final String mensagem = usuario.getText().toString();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    usuario.setText("Usuário " + user.getEmail() + " está logado");
                } else {
                    // Para o nome do usuário conectado
                    usuario.setText("");
                }
            }
        };
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //Abre tela de login
            case R.id.button_login:
                user = auth.getCurrentUser();

                if (user == null) {
                    startActivity(new Intent(this, LoginEmailActivity.class));
                } else {
                    startActivity(new Intent(this, MenuActivity.class));
                }
                break;

            //Abre tela de adicionar novo usuário
            case R.id.button_AddUser:
                startActivity(new Intent(this, AddActivity.class));
                break;

            //Desloga usuário do aplicativo
            case R.id.button_Deslogar:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getBaseContext(), "Usuário deslogado com sucesso!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

}
