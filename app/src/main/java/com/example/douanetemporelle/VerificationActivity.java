package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    TextView txtNom, txtDifference, txtMotif;
    Button btnApprouver, btnRefuser;


    private boolean estPremier(int nombre) {
        if (nombre < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(nombre); i++) {
            if (nombre % i == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        txtNom = findViewById(R.id.txt_nom_ca);
        txtDifference = findViewById(R.id.txt_difference_ca);
        txtMotif = findViewById(R.id.txt_motif_ca);

        btnApprouver = findViewById(R.id.btn_approuver_ca);
        btnRefuser = findViewById(R.id.btn_refuser_ca);


        String nom = getIntent().getStringExtra(
                MainActivity.creerCle("7561", "NOM_VOYAGEUR"));

        int naissance = getIntent().getIntExtra(
                MainActivity.creerCle("7561", "ANNEE_NAISSANCE"), 0);

        int destination = getIntent().getIntExtra(
                MainActivity.creerCle("7561", "ANNEE_DESTINATION"), 0);

        String motif = getIntent().getStringExtra(
                MainActivity.creerCle("7561", "MOTIF_VOYAGE"));

        int difference = destination - naissance;


        txtNom.setText("Nom : " + nom);
        txtDifference.setText("Âge temporel : " + difference + " ans");
        txtMotif.setText("Motif : " + motif);


        if ("Modification du passé".equals(motif)
                && estPremier(difference)) {

            btnApprouver.setEnabled(false);
            btnApprouver.setText(
                    "Voyage interdit : risque de paradoxe");
        }

        btnApprouver.setOnClickListener(v -> {
            ouvrirResultat(true, nom);
        });

        btnRefuser.setOnClickListener(v -> {
            ouvrirResultat(false, nom);
        });
    }


    private void ouvrirResultat(boolean verdict, String nom) {

        Intent intent = new Intent(
                VerificationActivity.this,
                ResultActivity.class
        );

        intent.putExtra(
                MainActivity.creerCle("7561", "VERDICT"),
                verdict
        );

        intent.putExtra( 
                MainActivity.creerCle("7561", "NOM_VOYAGEUR"),
                nom
        );

        startActivity(intent);
    }
}
