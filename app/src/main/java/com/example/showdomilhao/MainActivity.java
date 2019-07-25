package com.example.showdomilhao;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.showdomilhao.controller.ControllerQuestao;
import com.example.showdomilhao.dao.JsonTask;
import com.example.showdomilhao.model.Questao;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static TextView txtTitulo;
    Button confirmar;
    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    ArrayList<Questao> questionarios;
    public static ProgressBar progressBar;
    int resposta;
    Questao questao;
    ControllerQuestao controllerQuestao;
    String url;


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

        url = "https://next.json-generator.com/api/json/get/4kOPU2-zD";

        carregarPergunta();


        controllerQuestao = new ControllerQuestao();

        questionarios = controllerQuestao.getQuestoes();







        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {

                    case R.id.rB1:
                        Log.i("meuLog", "Botão A");
                        resposta = 1;

                        break;

                    case R.id.rB2:
                        Log.i("meuLog", "Botão B");
                        resposta = 2;

                        break;

                    case R.id.rB3:
                        Log.i("meuLog", "Botão C");
                        resposta = 3;

                        break;
                }
            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

    }

    public void carregarPergunta(){
        new JsonTask().execute(url);
        int numQuestaoAleatoria = restornarQuestaoAleatoriaNum();
        questao = questionarios.get(numQuestaoAleatoria);

    }

    private int restornarQuestaoAleatoriaNum() {
        int tamanhoArray = questionarios.size();
        Log.i("chave", "tamanho array(qestionario.size) == "+tamanhoArray);
        return 0;
    }


}
