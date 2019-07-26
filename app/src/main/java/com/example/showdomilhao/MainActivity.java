package com.example.showdomilhao;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.Application;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
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


public class MainActivity extends AppCompatActivity {


    TextView txtTitulo;
    Button confirmar;
    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    TextView txtPergunta;
    ArrayList<Questao> questoes;
    ProgressBar progressBar;
    int resposta;
    Questao questao;
    String url;
    int rodada;
    Animation some;
    Animation aparece;
    int pontuacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        progressBar = findViewById(R.id.progressBar_SM);
        radioGroup = findViewById(R.id.rG_peguntas);
        radioButton1 = findViewById(R.id.rB1);
        radioButton2 = findViewById(R.id.rB2);
        radioButton3 = findViewById(R.id.rB3);
        confirmar = findViewById(R.id.btn_confirma);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtPergunta = findViewById(R.id.txtPergunta);
        invisibleView();
        pontuacao = 0;



        some = new AlphaAnimation(1, 0);
        aparece = new AlphaAnimation(0, 1);


        some.setDuration(1000);
        aparece.setDuration(1000);

        questoes = new ArrayList<>();
        url = "https://next.json-generator.com/api/json/get/4kOPU2-zD";
        resposta = 0;
        rodada = 0;
        confirmar.setEnabled(false);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {

                    case R.id.rB1:
                        Log.i("meuLog", "Botão A");
                        resposta = 1;
                        confirmar.setEnabled(true);


                        break;

                    case R.id.rB2:
                        Log.i("meuLog", "Botão B");
                        resposta = 2;
                        confirmar.setEnabled(true);
                        break;

                    case R.id.rB3:
                        Log.i("meuLog", "Botão C");
                        resposta = 3;
                        confirmar.setEnabled(true);
                        break;
                }
            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (resposta != 0) {

                    if (resposta == questao.getCorreta()) {
                        Toast.makeText(MainActivity.this, "Acertou! +1 Ponto", Toast.LENGTH_SHORT).show();
                        pontuacao++;
                        rodada += 1;
                        desapareceView();
                        radioGroup.clearCheck();
                        confirmar.setEnabled(false);
                        atualizaView(rodada);

                    } else {
                        Toast.makeText(MainActivity.this, "Errou!", Toast.LENGTH_SHORT).show();

                        radioGroup.clearCheck();
                        confirmar.setEnabled(false);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "MARQUE UMA RESPOSTA!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        new JsonTask().execute(url);

        aparece.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                invisibleView();

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                visibleView();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        some.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                visibleView();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                invisibleView();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private class JsonTask extends AsyncTask<String, String, String> {
        StringBuffer sMilhaoJSONBuffer = new StringBuffer();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {


            try {
                URL url = new URL(params[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream stream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line = "";
                while (line != null) {
                    line = reader.readLine();
                    sMilhaoJSONBuffer.append(line);

                }
                if (connection != null) {
                    connection.disconnect();
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);


            Log.i("chave", sMilhaoJSONBuffer.toString());


            try {
                JSONObject listaJson = new JSONObject(result);
                txtTitulo.setText(listaJson.getString("titulo"));
                JSONArray jSOnArrayQuestionario = listaJson.getJSONArray("questionario");


                for (int i = 0; i < jSOnArrayQuestionario.length(); i++) {

                    JSONObject jSONquestao = jSOnArrayQuestionario.getJSONObject(i);
                    //                Log.i("chave","\n"+jSONquestao.toString()+"|------|");
                    Questao questao = new Questao(jSONquestao.getString("Pergunta"), jSONquestao.getString("respA"), jSONquestao.getString("respB"), jSONquestao.getString("respC"), jSONquestao.getInt("correta"));
                    questoes.add(questao);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                atualizaView(rodada);
            }


        }

    }

    void atualizaView(int i) {

        if (i < questoes.size()) {
            txtPergunta.setText(questoes.get(i).getPergunta());
            radioButton1.setText(questoes.get(i).getRespA());
            radioButton2.setText(questoes.get(i).getRespB());
            radioButton3.setText(questoes.get(i).getRespC());
            questao = questoes.get(i);
            apareceView();

        } else {
            criaAlerta();
        }


    }

    void visibleView() {
        txtPergunta.setVisibility(View.VISIBLE);
        txtTitulo.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.VISIBLE);
    }

    void apareceView() {
        txtPergunta.startAnimation(aparece);
        txtTitulo.startAnimation(aparece);
        radioGroup.startAnimation(aparece);
    }

    void desapareceView() {
        txtPergunta.startAnimation(some);
        txtTitulo.startAnimation(some);
        radioGroup.startAnimation(some);
    }

    void invisibleView() {
        txtPergunta.setVisibility(View.INVISIBLE);
        txtTitulo.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.INVISIBLE);
    }
    void criaAlerta(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Fim de Jogo");
        builder.setMessage("Você marcou "+pontuacao+" Pontos!");
        builder.setIcon(R.drawable.bau_de_ouro);
        builder.setPositiveButton("Jogar Novamente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pontuacao = 0;
                rodada = 0;
                atualizaView(rodada);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}

