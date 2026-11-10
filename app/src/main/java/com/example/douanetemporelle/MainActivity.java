package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtNom, edtNaissance, edtDestination;
    Spinner spnMotif;
    Button btnInspecter;


    public static String creerCle(String matricule, String nomCle) {
        return matricule + "_" + nomCle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNom = findViewById(R.id.edt_nom_ca);
        edtNaissance = findViewById(R.id.edt_naissance_ca);
        edtDestination = findViewById(R.id.edt_destination_ca);
        spnMotif = findViewById(R.id.spn_motif_ca);
        btnInspecter = findViewById(R.id.btn_inspecter_ca);

        String[] motifs = {
                "Tourisme",
                "Modification du passé",
                "Fuite"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                motifs
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spnMotif.setAdapter(adapter);

        btnInspecter.setOnClickListener(v -> {

            String nom = edtNom.getText().toString().trim();
            String naissanceTexte = edtNaissance.getText().toString().trim();
            String destinationTexte = edtDestination.getText().toString().trim();

            if (nom.isEmpty() || naissanceTexte.isEmpty()
                    || destinationTexte.isEmpty()) {
                Toast.makeText(this,
                        "Veuillez remplir tous les champs",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            int naissance;
            int destination;

            try {
                naissance = Integer.parseInt(naissanceTexte);
                destination = Integer.parseInt(destinationTexte);
            } catch (NumberFormatException e) {
                Toast.makeText(this,
                        "Veuillez saisir des années valides",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (destination <= naissance) {
                Toast.makeText(this,
                        "L'année de destination doit être supérieure à l'année de naissance",
                        Toast.LENGTH_LONG).show();
                return;
            }

            String motif = spnMotif.getSelectedItem().toString();

            Intent intent = new Intent(MainActivity.this,
                    VerificationActivity.class);

            intent.putExtra(creerCle("7561", "NOM_VOYAGEUR"), nom);
            intent.putExtra(creerCle("7561", "ANNEE_NAISSANCE"), naissance);
            intent.putExtra(creerCle("7561", "ANNEE_DESTINATION"), destination);
            intent.putExtra(creerCle("7561", "MOTIF_VOYAGE"), motif);

            startActivity(intent);
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        boolean nouveau = intent.getBooleanExtra(
                creerCle("7561", "NOUVEAU"), false);

        if (nouveau) {
            edtNom.setText("");
            edtNaissance.setText("");
            edtDestination.setText("");
            spnMotif.setSelection(0);

            intent.removeExtra(creerCle("7561", "NOUVEAU"));
        }
    }

}