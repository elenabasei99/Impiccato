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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton buttonGoTo = findViewById(R.id.btnGioca);
        buttonGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GiocoActivity.class);
                i.putExtra("numParole",1);
                startActivity(i);
            }
        });

    }

    public void setting(View v) {
        setContentView(R.layout.activity_setting);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lingue, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void salva(View view){
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        if((String.valueOf(spinner.getSelectedItem())).equals("Italiano")){
            Locale locale = new Locale("it");
            Locale.setDefault(locale);

            Resources res = this.getResources();
            Configuration config = new Configuration(res.getConfiguration());
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        else{
            Locale locale = new Locale("en");
            Locale.setDefault(locale);

            Resources res = this.getResources();
            Configuration config = new Configuration(res.getConfiguration());
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
