package be.bxl.formation.tfedeux.ListeActivite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.location.LocationListener;
import android.widget.TextView;
import android.widget.Toast;

import be.bxl.formation.tfedeux.Adapter.ActiviteAdapters;
import be.bxl.formation.tfedeux.R;
import be.bxl.formation.tfedeux.donnees.steetArt.StreetArt;
import be.bxl.formation.tfedeux.jsonStreetArt.StreetArtApi;

public class AfficherListe extends AppCompatActivity {
    private Spinner spListeAAfficher;
    private Button btAfficherListe;
    private ArrayList<StreetArt> datatache = new ArrayList<>();
    private RecyclerView rvActivite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_liste);
        try {
            List<StreetArt> streetArts = StreetArtApi.getStreetArts(this.getResources().openRawResource(R.raw.data));
            ActiviteAdapters adapater = new ActiviteAdapters(streetArts, this::onStreetArtClickListener);
            rvActivite.setAdapter(adapater);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(RecyclerView.VERTICAL);
            rvActivite.setLayoutManager(llm);
        } catch (IOException e) {
            e.printStackTrace();
        }

        spListeAAfficher = findViewById(R.id.sp_afficherliste_liste);
        btAfficherListe = findViewById(R.id.bt_afficherliste_AfficherListe);

        List<String> choixListe = new ArrayList<>();
        choixListe.add(getString(R.string.choix_liste_StreetArt));
        choixListe.add(getString(R.string.choix_liste_Bd));
        choixListe.add(getString(R.string.choix_liste_Resto));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                android.R.id.text1,
                choixListe
                );

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spListeAAfficher.setAdapter(spinnerAdapter);

        btAfficherListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActiviteAdapters activiteAdapters = new ActiviteAdapters(
                        getApplicationContext(),
                        datatache
                );
                if (datatache.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pas d'evenement aujourd'hui", Toast.LENGTH_LONG).show();


                }

//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
//                3, StaggeredGridLayoutManager.VERTICAL
//        );
//        rvActivite.setLayoutManager(layoutManager);

                rvActivite.setAdapter(activiteAdapters);
                rvActivite.setHasFixedSize(true);




            }
            }
        });
    }

    private void afficherrecyclerview() {
        ActiviteAdapters activiteAdapters = new ActiviteAdapters(
                getApplicationContext(),
                datatache
        );
        if (datatache.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Pas d'evenement aujourd'hui", Toast.LENGTH_LONG).show();
        }
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
//                3, StaggeredGridLayoutManager.VERTICAL
//        );
//        rvActivite.setLayoutManager(layoutManager);

        rvActivite.setAdapter(activiteAdapters);
        rvActivite.setHasFixedSize(true);

    }

    private void onStreetArtClickListener(View v) {
        String lat = ((TextView)v.findViewById(R.id.item_activite_coordonnee_lat)).getText().toString();
        String lon = ((TextView)v.findViewById(R.id.item_activite_coordonnee_long)).getText().toString();
        Log.d("ITEM_CLICKED", "Lat: "+ lat+ "; Lon: "+ lon);
    }
}