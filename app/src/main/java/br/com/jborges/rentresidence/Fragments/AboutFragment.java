package br.com.jborges.rentresidence.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.jborges.rentresidence.R;

/**
 * Jefferson Borges - 2019
 */

public class AboutFragment extends Fragment {

    ImageButton btnFazer_Ligacao;
    TextView telefone;
    TextView VersionCode;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_about, container, false);
        telefone = view.findViewById(R.id.about_telephone_number);

        VersionCode = view.findViewById(R.id.about_version2);
        VersionCode.setText(String.valueOf(1.0));

        btnFazer_Ligacao = view.findViewById(R.id.fazerligacao);
        btnFazer_Ligacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarLigacao();
            }
        });

        return view;
    }

    public void realizarLigacao() {
        String numero = telefone.getText().toString().replaceAll("[^0-9]", "");
        Uri uri = Uri.parse("tel:+" + numero);

        Intent intent = new Intent(Intent.ACTION_CALL, uri);

        if (ActivityCompat.checkSelfPermission(getView().getContext(), Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) getView().getContext(), new String[] {Manifest.permission.CALL_PHONE},1);
            return;
        }
        startActivity(intent);
    }
}
