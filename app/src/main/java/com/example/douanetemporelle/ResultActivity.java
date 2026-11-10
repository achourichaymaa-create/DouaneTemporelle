package com.example.douanetemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    LinearLayout layoutResultat;
    TextView txtResultat;
    Button btnNouveau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        layoutResultat = findViewById(R.id.layout_resultat_ca);
        txtResultat = findViewById(R.id.txt_resultat_ca);
        btnNouveau = findViewById(R.id.btn_nouveau_ca);

        boolean verdict = getIntent().getBooleanExtra(
                MainActivity.creerCle("7561", "VERDICT"), false);

        String nom = getIntent().getStringExtra(
                MainActivity.creerCle("7561", "NOM_VOYAGEUR"));

        if (verdict) {
            layoutResultat.setBackgroundColor(
                    Color.rgb(200, 235, 210));
            txtResultat.setText("Bon voyage " + nom + " !");
        } else {
            layoutResultat.setBackgroundColor(
                    Color.rgb(245, 205, 205));
            txtResultat.setText(
                    nom + " est en état d'arrestation temporelle !");
        }

        btnNouveau.setOnClickListener(v -> {

            Intent intent = new Intent(
                    ResultActivity.this,
                    MainActivity.class
            );

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            intent.putExtra(
                    MainActivity.creerCle("7561", "NOUVEAU"),
                    true
            );

            startActivity(intent);
            finish();
        });
    }
}
