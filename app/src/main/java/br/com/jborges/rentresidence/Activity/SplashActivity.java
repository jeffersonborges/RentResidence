package br.com.jborges.rentresidence.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import br.com.jborges.rentresidence.R;

/**
 * Jefferson Borges - 2019
 */

public class SplashActivity extends AppCompatActivity {

    //Tempo que nosso splashscreen ficará visivel
    private final int SPLASH_DISPLAY_LENGTH = 2500;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Executando o método que iniciará a animação
        carregar();
    }

    private void carregar() {
        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.animacao_splash);
        anim.reset();

        //Pegando o nosso objeto criado no layout
        ImageView imageView_Splash = (ImageView) findViewById(R.id.imageView_Splash);
        if (imageView_Splash != null) {

            imageView_Splash.clearAnimation();
            imageView_Splash.startAnimation(anim);
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                //Após o tempo definido irá executar a próxima tela
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}