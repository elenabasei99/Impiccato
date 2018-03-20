package com.example.studente.impiccato;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private int minimo=4;
    private String lang="en";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            Intent i = getIntent();
            lang=i.getStringExtra("lingua");
            minimo=i.getIntExtra("minimo",4);
        }
        catch(Exception e){

        }

    }

    public void gioca(View view){
        Intent i = new Intent(MainActivity.this, GiocoActivity.class);
        i.putExtra("numParole",1);
        i.putExtra("minimo",minimo);
        i.putExtra("lingua",lang);
        startActivity(i);
    }

    public void setting(View v) {
        setContentView(R.layout.activity_setting);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lingue, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.lunghezza, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
    }

    public void salva(View view){
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        if((spinner.getSelectedItem().toString()).equals("Italiano")  ||  (spinner.getSelectedItem().toString()).equals("Italian")){
            Locale locale = new Locale("it");
            Locale.setDefault(locale);
            lang="it";

            Resources res = this.getResources();
            Configuration config = new Configuration(res.getConfiguration());
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        else{
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            lang="en";

            Resources res = this.getResources();
            Configuration config = new Configuration(res.getConfiguration());
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner);
        if((String.valueOf(spinner.getSelectedItem())).equals("4")){
            minimo=4;
        }
        else if((String.valueOf(spinner.getSelectedItem())).equals("6")){
            minimo=6;
        }
        else{
            minimo=8;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
