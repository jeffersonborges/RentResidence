package br.com.jborges.rentresidence.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jborges.rentresidence.R;

import static br.com.jborges.rentresidence.Util.Util.verificarInternet;

/**
 * Jefferson Borges - 2019
 */

public class EnviarEmailActivity extends AppCompatActivity {

    EditText txtNome, txtEmail;
    Button btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_email);

        txtNome = (EditText)findViewById(R.id.txtNome);
        txtEmail = (EditText)findViewById(R.id.txtEmail);

        btnEmail = (Button)findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                enviarEmail();
            }
        });
    }

    private void enviarEmail(){

        final String nome = txtNome.getText().toString();
        final String email = txtEmail.getText().toString();
        final String subject = "assunto do email";
        final String body = "corpo do email do " + nome;

        if(!verificarInternet(this)) {
            Toast.makeText(getApplicationContext(), "Não estava online para enviar e-mail!", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }

        new Thread(new Runnable(){
            @Override
            public void run() {
                Mail m = new Mail();

                String[] toArr = {email};
                m.setTo(toArr);

                //m.setFrom("seunome@seuemail.com.br"); //caso queira enviar em nome de outro
                m.setSubject(subject);
                m.setBody(body);

                try {
                    //m.addAttachment("pathDoAnexo");//anexo opcional
                    m.send();
                }
                catch(RuntimeException rex){ }//erro ignorado
                catch(Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }

                Toast.makeText(getApplicationContext(), "Email enviado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        }).start();
    }
}
