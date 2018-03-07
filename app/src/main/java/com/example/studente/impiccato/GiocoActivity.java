package com.example.studente.impiccato;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioco);

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

        ArrayList<String> parole=new ArrayList<>();

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

        TextView text = (TextView) findViewById(R.id.lettereU);
        text.setText(parole.get(i));

        ArrayList<Character> lettere = new ArrayList<>();

        boolean flag=true;
        int j=0;

        while(flag){
            try{
                lettere.add((parole.get(i).charAt(j)));
                j++;
            }
            catch (Exception e){
                flag=false;
            }
        }

        LinearLayout layout1= (LinearLayout) findViewById(R.id.layout1);

        for(int index=0; index<lettere.size();index++){
            TextView t=new TextView(null);
            t.setText(""+lettere.get(index));
            t.setWidth(40);
            t.setHeight(0);
            t.setId(index);
            layout1.addView(t);
        }
    }
}
