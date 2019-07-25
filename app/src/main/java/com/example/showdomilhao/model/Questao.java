package com.example.showdomilhao.model;

public class Questao {
    String Pergunta;
    String RespA;
    String RespB;
    String RespC;
    int correta;

    public Questao(String pergunta, String respA, String respB, String respC, int correta) {
        Pergunta = pergunta;
        RespA = respA;
        RespB = respB;
        RespC = respC;
        this.correta = correta;
    }

    public String getPergunta() {
        return Pergunta;
    }

    public void setPergunta(String pergunta) {
        Pergunta = pergunta;
    }

    public String getRespA() {
        return RespA;
    }

    public void setRespA(String respA) {
        RespA = respA;
    }

    public String getRespB() {
        return RespB;
    }

    public void setRespB(String respB) {
        RespB = respB;
    }

    public String getRespC() {
        return RespC;
    }

    public void setRespC(String respC) {
        RespC = respC;
    }

    public int getCorreta() {
        return correta;
    }

    public void setCorreta(int correta) {
        this.correta = correta;
    }
}
