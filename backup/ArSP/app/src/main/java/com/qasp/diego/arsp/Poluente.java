// Copyright 2016 Diego Martos Buoro
// This file is part of QASP.
//
// QASP is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// QASP is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with QASP.  If not, see <http://www.gnu.org/licenses/>.
package com.qasp.diego.arsp;

import android.support.annotation.NonNull;

import com.example.diego.arsp.R;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;


public class Poluente implements Serializable,Comparable<Poluente>{

    private String nome;
    private int indice;
    private String qualidade;
    private Hashtable<String,Integer[]> classificacao;
    private int imagem;

    // Inicializacao ao criar o app.
    Poluente(String nome){
        this.setNome(nome);
        this.indice = -1;
        this.classificacao = new Hashtable<String,Integer[]>();
        InicializaTabeladeClassificao(this.classificacao);
        this.imagem = R.drawable.erro;
    }

    // Utilizado ao calcular o indice no local do usuario
    Poluente(String nome, int I){
        this.nome = nome;
        this.indice = I;
        this.classificacao = new Hashtable<String,Integer[]>();
        InicializaTabeladeClassificao(this.classificacao);
    }

    public void AtualizaImagem(Integer[] imagem) {

        if(this.getIndice() != -1) {
            // NUMERO DE QUALIDADE*INDICE
            int poluenteOffset = 5 * PoluentesIndices.PoluenteproIndice(this.getNome());
            if (this.getQualidade().equals("Boa")) {
                this.setImagem(imagem[poluenteOffset]);
            } else if (this.getQualidade().equals("Mediana")) {
                this.setImagem(imagem[1 + poluenteOffset]);
            } else if (this.getQualidade().equals("Ruim")) {
                this.setImagem(imagem[2 + poluenteOffset]);
            } else if (this.getQualidade().equals("Muito Ruim")) {
                this.setImagem(imagem[3 + poluenteOffset]);
            } else if (this.getQualidade().equals("Pessima")) {
                this.setImagem(imagem[4 + poluenteOffset]);
            }
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setIndice(int indice){
        this.indice = indice;
    }
    public void setQualidade(String qualidade) { this.qualidade = qualidade; }
    public void setImagem(int i){
        this.imagem = i;
    }
    public String getNome() {return this.nome; }
    public int getIndice(){
        return this.indice;
    }
    public String getQualidade(){ return this.qualidade; }
    public Hashtable<String,Integer[]> getClassficacao() {return this.classificacao; }
    public int getImagem(){
        return this.imagem;
    }

    public void DeterminaQualidade(){

        if(this.getIndice() != -1) {
            Set<String> keys = this.getClassficacao().keySet();
                for (String k : keys) {
                    Integer[] limtemp = this.getClassficacao().get(k);
                    int Ii = limtemp[0];
                    int If = limtemp[1];
                    if (Ii <= this.getIndice() && If >= this.getIndice()) {
                        this.setQualidade(k);
                        break;
                }
            }
        }
    }

    private void InicializaTabeladeClassificao (Hashtable<String,Integer[]> classificacao){

       for(PoluentesIndices.IndiceLimite c: PoluentesIndices.IndiceLimite.values()) {
            Integer[] limites = new Integer[2];
            limites[0] = c.Ii();
            limites[1] = c.If();
            classificacao.put(c.qualidade(), limites);
       }
    }

    // Para ordenar por ordem descrescente a lista de poluentes
    @Override
    public int compareTo(@NonNull Poluente poluente) {

        int indicepoluente = poluente.getIndice();
        return indicepoluente - this.getIndice();
    }
}
