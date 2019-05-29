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

import android.util.Log;

import com.example.diego.arsp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Estacao implements Serializable{

    private String id;
    private String nome;
    private Coordenada localizacao;
    private String endereco;
    private int posdopiorindice;
    private ArrayList<Poluente> poluentes;
    private String data;
    private String horas;
    private boolean indisponivel;
    private double distanciaparaGPS;
    private String cor_do_texto;
    private static final long serialVersionUID = 46543445; // Serializable

    Estacao(String id, String nome, Coordenada localizacao, String endereco, int posdopiorindice, ArrayList<Poluente> poluentes, String cor_do_texto){
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.endereco = endereco;
        this.posdopiorindice = posdopiorindice;
        this.poluentes = poluentes;
        this.data = " ";
        this.horas = " ";
        this.indisponivel = false;
        this.cor_do_texto = cor_do_texto;
    }

    public int ImagemdoPiorIndice(){

        if(!this.getIndisponivel()){
            if(this.getPosdopiorindice() >= 0)
                return this.getPoluentes().get(this.getPosdopiorindice()).getImagem();
            else
                return R.drawable.erro;
        }
        return R.drawable.off;
    }

    public void AtualizaEstacao(){

        int posicaodopior = this.AtualizaIndice();
        Log.d("Estacao:",this.getNome());
        Log.d("cortexto:",this.getCor_do_texto());
        if(posicaodopior >= 0) {
            this.setPosdopiorindice(posicaodopior);
            this.setCor_do_texto(AtualizaCordeTexto());
        }
    }

    private String AtualizaCordeTexto(){

        switch (this.getPoluentes().get(this.getPosdopiorindice()).getQualidade()) {
                case "Boa":
                    return "#51fe4b";
                case "Mediana":
                    return "#fff13b";
                case "Ruim":
                    return "#fe8913";
                case "Muito Ruim":
                    return "#ff3b3b";
                case "Péssima":
                    return "#bd3bff";
                default:
                    return "#afe6e6"; // BRANCO
        }
    }

    private int AtualizaIndice() {

        int pior = -1;
        if(!this.getPoluentes().isEmpty()) pior = this.PiorCaso();
        return pior;
    }

    private int PiorCaso(){

        int k = -1;
        int piorIndice = -1;
        for(int i = 0; i < this.getPoluentes().size(); i++){
            if(this.getPoluentes().get(i).getIndice() > piorIndice){
                k = i;
                piorIndice = this.getPoluentes().get(i).getIndice();
            }
        }
        return k;
    }

    public void AtualizaPoluente(int I, int i){

        Log.d(this.getPoluentes().get(i).getNome(), String.valueOf(I));
        this.getPoluentes().get(i).setIndice(I);
    }

    public void AtualizaTempo(String data, String horas){

        this.setData(data);
        this.setHoras(horas);
    }

    // GETTERS & SETTERS (alguns nao utilizados em comentarios)

    public void setId(String id){ this.id = id;}
    // public void setNome(String nome){ this.nome = nome;}
    // public void setCoordenada(Coordenada l){this.localizacao = l;}
    // public void setEndereco(String e){this.endereco = e;}
    public void setPosdopiorindice(int i){this.posdopiorindice= i;}
    // public void setPoluentes(ArrayList<Poluente> p) {this.poluentes = p; }
    public void setData(String data){ this.data = data;}
    public void setHoras(String horas){ this.horas = horas; }
    public void setIndisponivel(boolean indisponivel) { this.indisponivel = indisponivel; }
    public void setDistanciaparaGPS(double distanciaparaGPS) { this.distanciaparaGPS = distanciaparaGPS; }
    public void setCor_do_texto(String cor){ this.cor_do_texto = cor;}
    public String getId(){return this.id;}
    public String getNome(){return this.nome;}
    public Coordenada getCoordenada(){return this.localizacao;}
    public String getEndereco(){return this.endereco;}
    public int getPosdopiorindice(){ return this.posdopiorindice; }
    public ArrayList<Poluente> getPoluentes(){ return this.poluentes; }
    // public String getData(){ return this.data; }
    // public String getHoras(){ return this.horas;}
    public boolean getIndisponivel(){ return  this.indisponivel; }
    public double getDistanciaparaGPS() {return this.distanciaparaGPS; }
    public String getCor_do_texto(){ return this.cor_do_texto;}
}
