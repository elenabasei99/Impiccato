package com.example.studente.impiccato;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
    private ArrayList<Character> lettere = new ArrayList<>();


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
            TextView t=new TextView(GiocoActivity.this);

            if(index==0){
                t.setText(""+lettere.get(index));
            }
            else if(index==lettere.size()-1){
                t.setText(""+lettere.get(index));
            }
            else{
                t.setText("_");
            }

            t.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.MATCH_PARENT));
            t.setWidth(90);
            t.setId(index);
            t.setTextSize(22);
            t.setAllCaps(true);

            layout1.addView(t);
        }

        for(int index=1; index<lettere.size()-1;index++){
            if(lettere.get(index).equals(lettere.get(0))){
                TextView t= (TextView)findViewById(index);
                t.setText(""+lettere.get(0));
            }
            else if(lettere.get(index).equals(lettere.get(lettere.size()-1))){
                TextView t= (TextView)findViewById(index);
                t.setText(""+lettere.get(lettere.size()-1));
            }
        }

    }

    public void lettere(View view) {
        TextView t= (TextView)findViewById(R.id.lettereU);
        Button b= (Button) findViewById(view.getId());
        t.setText(t.getText()+"   "+b.getText());

        for(int index=1; index<lettere.size()-1;index++){
            if(lettere.get(index).equals(""+b.getText())){
                TextView t1= (TextView)findViewById(index);
                t.setText(""+b.getText());
            }
        }

        LinearLayout layout2= (LinearLayout) findViewById(R.id.layout2);
        layout2.removeView(view);
    }
}
