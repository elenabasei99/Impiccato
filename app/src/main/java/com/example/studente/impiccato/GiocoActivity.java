package com.example.studente.impiccato;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Random;

public class GiocoActivity extends AppCompatActivity {
    private ArrayList<Character> lettere = new ArrayList<>(),lettereU=new ArrayList<>();
    String parola="",lettereu="";
    private int lett=0,sbagli=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioco);

        if(savedInstanceState!=null){
            lett=savedInstanceState.getInt("lett");
            sbagli=savedInstanceState.getInt("sbagli");
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
        else {

            String json = null;
            try {

                InputStream is = this.getAssets().open("parole.json");
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
            int i = r.nextInt(1160 - 0) + 0;

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
                } else if (lettere.get(index).equals(lettere.get(lettere.size() - 1))) {
                    TextView t = (TextView) findViewById(index);
                    t.setText("" + lettere.get(lettere.size() - 1));
                }
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
            sbagli=0;
        }
        else if(sbagli==12){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setContentView(R.layout.activity_risultato);
            sbagli=0;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("lett",lett);
        outState.putInt("sbagli",sbagli);
        outState.putString("parola",parola);
        outState.putString("lettereU",lettereu);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = getSharedPreferences("prova",MODE_PRIVATE).edit();
        editor.putInt("lett",lett);
        editor.putInt("sbagli",sbagli);
        editor.putString("parola",parola);
        editor.putString("lettereU",lettereu);
        editor.apply();
    }
}
