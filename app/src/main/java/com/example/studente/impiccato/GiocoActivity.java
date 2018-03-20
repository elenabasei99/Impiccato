package com.example.studente.impiccato;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class GiocoActivity extends AppCompatActivity {
    private ArrayList<Character> lettere = new ArrayList<>(),lettereU=new ArrayList<>();
    private String parola="",lettereu="",lang="en";
    private int lett=0,sbagli=1,numParole=1, minimo=4, paroleInd=0, paroleNonInd=0;
    private Boolean prossima=false,risultato=false,timer=false;
    private Bundle savedInstanceState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioco);

        this.savedInstanceState=savedInstanceState;

        Intent intent= getIntent();
        numParole=intent.getIntExtra("numParole",1);
        minimo=intent.getIntExtra("minimo",4);
        paroleInd=intent.getIntExtra("indovinato",0);
        paroleNonInd=intent.getIntExtra("nonIndovinato",0);
        lang=intent.getStringExtra("lingua");

        try{
            risultato=savedInstanceState.getBoolean("risultato");
            parola=savedInstanceState.getString("parola");
            lettereu=savedInstanceState.getString("lettereu");
        }
        catch (Exception e){
            risultato=false;
        }

        try {
            paroleInd = savedInstanceState.getInt("indovinato");
            paroleNonInd = savedInstanceState.getInt("nonIndovinato");
        }
        catch (Exception e){

        }

        if(risultato){
            setContentView(R.layout.activity_risultato);
            TextView parola= (TextView)findViewById(R.id.parola);
            parola.setText(parola.getText()+" "+this.parola);
            TextView tentativi= (TextView)findViewById(R.id.parola);
            tentativi.setText(tentativi.getText()+" "+lettereu.length());

            if(sbagli!=12){
                TextView titolo=(TextView)findViewById(R.id.titolo);
                titolo.setText(R.string.hai_indovinato);
            }
            else{
                TextView titolo=(TextView)findViewById(R.id.titolo);
                titolo.setText(R.string.non_hai_indovinato);
            }
        }
        else {
            inizia();
        }
    }

    private void inizia(){

        try{
            TextView titolo= (TextView)findViewById(R.id.titolo);
            titolo.setText(titolo.getText()+" "+savedInstanceState.getInt("numParole"));
            prossima=savedInstanceState.getBoolean("prossima");
        }
        catch (Exception e){

        }

        TextView titolo= (TextView)findViewById(R.id.titolo);
        titolo.setText(titolo.getText()+" "+numParole);

        if(savedInstanceState!=null && !prossima){
            inCorso();
        }
        else {
            prossima=false;
            nuovo();
        }
    }

    private void inCorso(){
        lett=savedInstanceState.getInt("lett");
        sbagli=savedInstanceState.getInt("sbagli");
        minimo=savedInstanceState.getInt("minimo");
        parola=savedInstanceState.getString("parola");
        lettereu=savedInstanceState.getString("lettereU");


        boolean flag = true;
        int j = 0;

        while (flag) {
            try {
                lettere.add((parola.charAt(j)));
                j++;
            } catch (Exception e) {
                flag = false;
            }
        }

        flag=true;
        j=0;
        while (flag) {
            try {
                lettereU.add((lettereu.charAt(j)));
                j++;
            } catch (Exception e) {
                flag = false;
            }
        }

        int cont = 0;

        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        for (int index = 0; index < 26 - cont; index++) {
            Button b = (Button) layout2.getChildAt(index);
            if (("" + lettere.get(0)).equals("" + b.getText())) {
                layout2.removeViewAt(index);
                index--;
                cont++;
            } else if (!("" + lettere.get(0)).equals(("" + lettere.get(lettere.size() - 1)))) {
                if (("" + lettere.get(lettere.size() - 1)).equals("" + b.getText())) {
                    layout2.removeViewAt(index);
                    index--;
                    cont++;
                }
            }
        }


        LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout1);


        for (int index = 0; index < lettere.size(); index++) {
            TextView t = new TextView(GiocoActivity.this);

            if (index == 0) {
                t.setText("" + lettere.get(index));
            } else if (index == lettere.size() - 1) {
                t.setText("" + lettere.get(index));
            } else {
                t.setText("_");
            }

            t.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT));
            t.setWidth(90);
            t.setId(index);
            t.setTextSize(22);
            t.setAllCaps(true);

            layout1.addView(t);
        }

        for (int index = 1; index < lettere.size() - 1; index++) {
            if (lettere.get(index).equals(lettere.get(0))) {
                TextView t = (TextView) findViewById(index);
                t.setText("" + lettere.get(0));
            } else if (lettere.get(index).equals(lettere.get(lettere.size() - 1))) {
                TextView t = (TextView) findViewById(index);
                t.setText("" + lettere.get(lettere.size() - 1));
            }
        }

        for(int ind=0;ind<lettereU.size();ind++){
            TextView t= (TextView)findViewById(R.id.lettereU);
            t.setText(t.getText()+"   "+lettereU.get(ind));

            for(int index=1; index<lettere.size()-1;index++){

                if((""+lettere.get(index)).equals(""+lettereU.get(ind))){
                    layout1= (LinearLayout)findViewById(R.id.layout1);
                    TextView t1= (TextView)layout1.getChildAt(index);
                    t1.setText(""+lettereU.get(ind));
                }
            }

            for (int index = 0; index < 26 - cont; index++) {
                Button b = (Button) layout2.getChildAt(index);
                if ((""+lettereU.get(ind)).equals("" + b.getText())) {
                    layout2.removeViewAt(index);
                    index--;
                    cont++;
                }
            }
        }

        ImageView img=(ImageView)findViewById(R.id.imageView);
        img.setBackgroundResource(getResources().getIdentifier("imp"+sbagli, "drawable",  getPackageName()));
    }

    private void nuovo(){
        String json = null;
        try {

            InputStream is = this.getAssets().open("parole_"+ lang+".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ArrayList<String> parole = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("parole");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                parole.add(jo_inside.getString("parola"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Random r = new Random();
        int i=0;

        do{
            i= r.nextInt(1160 - 0) + 0;
        }while(parole.get(i).length()<minimo);

        parola = parole.get(i);

        boolean flag = true;
        int j = 0;

        while (flag) {
            try {
                lettere.add((parole.get(i).charAt(j)));
                j++;
            } catch (Exception e) {
                flag = false;
            }
        }

        int cont = 0;

        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        for (int index = 0; index < 26 - cont; index++) {
            Button b = (Button) layout2.getChildAt(index);
            if (("" + lettere.get(0)).equals("" + b.getText())) {
                layout2.removeViewAt(index);
                index--;
                cont++;
            } else if (!("" + lettere.get(0)).equals(("" + lettere.get(lettere.size() - 1)))) {
                if (("" + lettere.get(lettere.size() - 1)).equals("" + b.getText())) {
                    layout2.removeViewAt(index);
                    index--;
                    cont++;
                }
            }
        }


        LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout1);


        for (int index = 0; index < lettere.size(); index++) {
            TextView t = new TextView(GiocoActivity.this);

            if (index == 0) {
                t.setText("" + lettere.get(index));
            } else if (index == lettere.size() - 1) {
                t.setText("" + lettere.get(index));
            } else {
                t.setText("_");
                lett++;
            }

            t.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT));
            t.setWidth(90);
            t.setId(index);
            t.setTextSize(22);
            t.setAllCaps(true);

            layout1.addView(t);
        }

        for (int index = 1; index < lettere.size() - 1; index++) {
            if (lettere.get(index).equals(lettere.get(0))) {
                TextView t = (TextView) findViewById(index);
                t.setText("" + lettere.get(0));
                lett--;
            } else if (lettere.get(index).equals(lettere.get(lettere.size() - 1))) {
                TextView t = (TextView) findViewById(index);
                t.setText("" + lettere.get(lettere.size() - 1));
                lett--;
            }
        }
    }

    public void lettere(View view) {
        boolean flag=false;
        TextView t= (TextView)findViewById(R.id.lettereU);
        Button b= (Button) findViewById(view.getId());
        t.setText(t.getText()+"   "+b.getText());
        lettereu=lettereu+""+b.getText();

        for(int index=1; index<lettere.size()-1;index++){

            if((""+lettere.get(index)).equals(""+b.getText())){
                LinearLayout layout1= (LinearLayout)findViewById(R.id.layout1);
                TextView t1= (TextView)layout1.getChildAt(index);
                t1.setText(""+b.getText());
                lett--;
                flag=true;
            }
        }

        if(!flag){
            ImageView img=(ImageView)findViewById(R.id.imageView);
            img.setBackgroundResource(getResources().getIdentifier("imp"+sbagli, "drawable",  getPackageName()));
            sbagli++;
        }

        LinearLayout layout2= (LinearLayout) findViewById(R.id.layout2);
        layout2.removeView(view);

        if(lett==0){
            setContentView(R.layout.activity_risultato);
            risultato=true;
            risultato();
        }
        else if(sbagli==12){
            Thread timer=new Timer(this);

            risultato=true;
            risultato();
        }
    }

    public void setTimer(Boolean val){
        timer=val;
    }

    private void risultato(){

        setContentView(R.layout.activity_risultato);

        if(sbagli!=12){
            TextView titolo=(TextView)findViewById(R.id.titolo);
            titolo.setText(R.string.hai_indovinato);
            paroleInd++;
        }
        else{
            TextView titolo=(TextView)findViewById(R.id.titolo);
            titolo.setText(R.string.non_hai_indovinato);
            paroleNonInd++;
        }

        TextView parola=(TextView)findViewById(R.id.parola);
        parola.setText(parola.getText()+" "+this.parola);

        TextView tentativi=(TextView)findViewById(R.id.tentativi);
        tentativi.setText(tentativi.getText()+" "+lettereu.length());

        TextView ind=(TextView)findViewById(R.id.indovinato);
        ind.setText(ind.getText()+" "+this.paroleInd);

        TextView nonInd=(TextView)findViewById(R.id.nonIndovinato);
        nonInd.setText(nonInd.getText()+" "+this.paroleNonInd);
    }

    public void menu(View view){
        Intent i = new Intent(GiocoActivity.this, MainActivity.class);
        i.putExtra("lingua",lang);
        i.putExtra("minimo",minimo);
        startActivity(i);
    }

    public void prossima(View view){
        sbagli=0;
        numParole++;
        prossima=true;
        risultato=false;
        Activity a=GiocoActivity.this;
        Intent i = new Intent(GiocoActivity.this, GiocoActivity.class);
        i.putExtra("numParole",numParole);
        i.putExtra("indovinato",paroleInd);
        i.putExtra("nonIndovinato",paroleNonInd);
        i.putExtra("lingua",lang);
        i.putExtra("minimo",minimo);
        startActivity(i);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("lett",lett);
        outState.putInt("sbagli",sbagli);
        outState.putInt("minimo",minimo);
        outState.putInt("numParole",numParole);
        outState.putInt("indovinato",paroleInd);
        outState.putInt("nonIndovinato",paroleNonInd);
        outState.putString("parola",parola);
        outState.putString("lettereU",lettereu);
        outState.putBoolean("prossima",prossima);
        outState.putBoolean("risultato",risultato);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = getSharedPreferences("prova",MODE_PRIVATE).edit();
        editor.putInt("lett",lett);
        editor.putInt("sbagli",sbagli);
        editor.putInt("minimo",minimo);
        editor.putInt("numParole",numParole);
        editor.putInt("indovinato",paroleInd);
        editor.putInt("nonIndovinato",paroleNonInd);
        editor.putString("parola",parola);
        editor.putString("lettereU",lettereu);
        editor.putBoolean("prossima",prossima);
        editor.putBoolean("risultato",risultato);
        editor.apply();
    }
}
