package com.example.showdomilhao.dao;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;


import com.example.showdomilhao.MainActivity;
import com.example.showdomilhao.controller.ControllerQuestao;
import com.example.showdomilhao.model.Questao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JsonTask extends AsyncTask <String, String,String>{
    StringBuffer sMilhaoJSONBuffer = new StringBuffer();
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        MainActivity.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {


        try {
            URL url =  new URL(params[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream stream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line="";
            while(line!=null){
                line = reader.readLine();
                sMilhaoJSONBuffer.append(line);
            }


            return sMilhaoJSONBuffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        MainActivity.progressBar.setVisibility(View.INVISIBLE);
//        Log.i("chave",sMilhaoJSONBuffer.toString());

        try {
            JSONObject listaJson = new JSONObject(s);
            String titulo = listaJson.getString("titulo");
            JSONArray jSOnquestionario = listaJson.getJSONArray("questionario");
            ArrayList<Questao> questoes = new ArrayList<>();

            for (int i=0; i<jSOnquestionario.length(); i++){

                JSONObject jSONquestao = jSOnquestionario.getJSONObject(i);
//                Log.i("chave","\n"+jSONquestao.toString()+"|------|");
                Questao questao = new Questao(jSONquestao.getString("Pergunta"),jSONquestao.getString("respA"),jSONquestao.getString("respB"),jSONquestao.getString("respC"),jSONquestao.getInt("correta"));
                questoes.add(questao);
                questao = null;

                MainActivity.txtTitulo.setText(titulo);
            }


            ControllerQuestao controllerQuestao  = new ControllerQuestao();
            controllerQuestao.setQuestoes(questoes);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
