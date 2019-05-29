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

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Prevencao {

    private final static int NORMAL =  1;
    private final static int SENSIVEL =  2;
    private final static int ESPORTISTA =  3;
    private static int prefdeGrupo;

    // POLUENTE -> QUALIDADE -> PERFIL DO GRUPO
    public static String TextoDePrevencao(Activity v, Poluente p){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(v);
        prefdeGrupo = Integer.parseInt(preferences.getString("perfilsaude","1"));
        if(p.getNome().equals("SO2")) {
            return prevencaoSO2(p.getQualidade());
        }
        else if(p.getNome().equals("NO2")){
            return prevencaoNO2(p.getQualidade());
        }
        else if(p.getNome().equals("CO")){
            return prevencaoCO(p.getQualidade());
        }
        else if(p.getNome().equals("O3")){
            return prevencaoO3(p.getQualidade());
        }
        else if(p.getNome().equals("MP2.5")){
            return prevencaoPM25(p.getQualidade());
        }
        else {
            return prevencaoPM10(p.getQualidade());
        }
    }

    public static String prevencaoSO2(String qualidade){

        if(qualidade.equals("Ruim")){
            return prevencaoSO2ruim();
        }
        else if(qualidade.equals("Muito Ruim")){
            return prevencaoSO2muitoruim();
        }
        else if(qualidade.equals("Péssima")){
            return prevencaoSO2pessima();
        }
        return "Nenhuma.";
    }
    public static String prevencaoSO2ruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Reduzir a prática de exercícios físicos ao ar livre.");
            case ESPORTISTA:
                return ("Sem efeitos, mas considere fazer os exercícios em um ambiente interno.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoSO2muitoruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Reduzir a prática de exercícios físicos ao ar livre, evite se puder.");
            case ESPORTISTA:
                return ("Potencial comprometimento no desempenho e saúde. Prefira um ambiente interno.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoSO2pessima(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Reduza qualquer tipo de esforço físico ao ar livre");
            case SENSIVEL:
                return ("Evite qualquer tipo de exercício físico ao ar livre");
            case ESPORTISTA:
                return ("Comprometimento no desemepenho e saúde. Evite exercícios físicos ao ar livre.");
            default:
                return ("Reduza qualquer tipo de esforço físico ao ar livre");
        }
    }
    public static String prevencaoNO2(String qualidade){
        if(qualidade.equals("Ruim")){
            return prevencaoNO2ruim();
        }
        else if(qualidade.equals("Muito Ruim")){
            return prevencaoNO2muitoruim();
        }
        else if(qualidade.equals("Péssima")){
            return prevencaoNO2pessima();
        }
        return "Nenhuma.";
    }
    public static String prevencaoNO2ruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Reduzir a prática de exercícios físicos ao ar livre.");
            case ESPORTISTA:
                return ("Nenhuma.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoNO2muitoruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Reduzir a prática de exercícios físicos ao ar livre.");
            case ESPORTISTA:
                return ("Nenhuma.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoNO2pessima(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Reduzir o esforço físico ao ar livre.");
            case SENSIVEL:
                return ("Evitar a prática de exercícios físicos ao ar livre.");
            case ESPORTISTA:
                return ("Potencial comprometimento no desempenho e saúde. Prefira um ambiente interno.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoCO(String qualidade){
        if(qualidade.equals("Ruim")){
            return prevencaoCOruim();
        }
        else if(qualidade.equals("Muito Ruim")){
            return prevencaoCOmuitoruim();
        }
        else if(qualidade.equals("Péssima")){
            return prevencaoCOpessima();
        }
        return "Nenhuma.";
    }
    public static String prevencaoCOruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Pessoas com problemas cardiovasculares devem reduzir a prática de exercícios físicos e evitar locais com alto tráfego");
            case ESPORTISTA:
                return ("Nenhuma.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoCOmuitoruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Pessoas com problemas cardiovasculares devem reduzir a prática de exercícios físicos e evitar locais com alto tráfego.");
            case ESPORTISTA:
                return ("Procure fazer exercícios em um local afastado de ruas com alto tráfego.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoCOpessima(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Evite o esforço físico em ruas com alto tráfego.");
            case SENSIVEL:
                return ("Pessoas com problemas cardiovasculares devem evitar a prática de exercícios físicos e locais com alto tráfego.");
            case ESPORTISTA:
                return ("Procure fazer exercícios em um local afastado de ruas com alto tráfego.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoO3(String qualidade){
        if(qualidade.equals("Mediana")){
            return prevencaoO3mediana();
        }
        else if(qualidade.equals("Ruim")){
            return prevencaoO3ruim();
        }
        else if(qualidade.equals("Muito Ruim")){
            return prevencaoO3muitoruim();
        }
        else if(qualidade.equals("Péssima")){
            return prevencaoO3pessima();
        }
        return "Nenhuma.";
    }
    public static String prevencaoO3mediana(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Considere a redução de esforço físico pesado e da prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Nenhuma.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoO3ruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Redução de esforço físico e da prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Potencial compromentimento, redução de esforço físico pesado e da prática de exercícios de forma prolongada ao ar livre");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoO3muitoruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Evite esforços físicos de forma prolongada");
            case SENSIVEL:
                return ("Redução de esforço físico e da prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Redução de esforço físico e da prática de exercícios de forma prolongada ao ar livre");
            default:
                return ("Evite esforços físicos de forma prolongada");
        }
    }
    public static String prevencaoO3pessima(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Evitar esforço físico");
            case SENSIVEL:
                return ("Evitar esforço físico e da prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Evitar esforço físico e da prática de exercícios de forma prolongada ao ar livre");
            default:
                return ("Evitar esforço físico");
        }
    }
    public static String prevencaoPM25(String qualidade){
        if(qualidade.equals("Mediana")){
            return prevencaoPM25mediana();
        }
        else if(qualidade.equals("Ruim")){
            return prevencaoPM25ruim();
        }
        else if(qualidade.equals("Muito Ruim")){
            return prevencaoPM25muitoruim();
        }
        else if(qualidade.equals("Péssima")){
            return prevencaoPM25pessima();
        }
        return "Nenhuma.";
    }
    public static String prevencaoPM25mediana(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Redução de esforço físico e da prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Nenhuma.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoPM25ruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Redução de esforço físico e da prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Sem efeitos, mas considere fazer os exercícios em um ambiente interno.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoPM25muitoruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Redução de esforço físico ao ar livre");
            case SENSIVEL:
                return ("Evitar esforço físico e a prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Redução de esforço físico e da prática de exercícios de forma prolongada ao ar livre");
            default:
                return ("Redução de esforço físico ao ar livre");
        }
    }
    public static String prevencaoPM25pessima(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Evitar esforço físico ao ar livre");
            case SENSIVEL:
                return ("Evitar esforço físico e a prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Evitar esforço físico e a prática de exercícios de forma prolongada ao ar livre");
            default:
                return ("Evitar esforço físico");
        }
    }
    public static String prevencaoPM10(String qualidade){
        if(qualidade.equals("Mediana")){
            return prevencaoPM10mediana();
        }
        else if(qualidade.equals("Ruim")){
            return prevencaoPM10ruim();
        }
        else if(qualidade.equals("Muito Ruim")){
            return prevencaoPM10muitoruim();
        }
        else if(qualidade.equals("Péssima")){
            return prevencaoPM10pessima();
        }
        return "Nenhuma.";
    }
    public static String prevencaoPM10mediana(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Redução de esforço físico e da prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Nenhuma.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoPM10ruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Nenhuma.");
            case SENSIVEL:
                return ("Redução de esforço físico e da prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Sem efeitos, mas considere fazer os exercícios em um ambiente interno.");
            default:
                return ("Nenhuma.");
        }
    }
    public static String prevencaoPM10muitoruim(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Redução de esforço físico ao ar livre");
            case SENSIVEL:
                return ("Evitar esforço físico e a prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Redução de esforço físico e da prática de exercícios de forma prolongada ao ar livre");
            default:
                return ("Redução de esforço físico ao ar livre");
        }
    }
    public static String prevencaoPM10pessima(){
        switch(prefdeGrupo) {
            case NORMAL:
                return ("Evitar esforço físico ao ar livre");
            case SENSIVEL:
                return ("Evitar esforço físico e a prática de exercícios de forma prolongada ao ar livre");
            case ESPORTISTA:
                return ("Evitar esforço físico e a prática de exercícios de forma prolongada ao ar livre");
            default:
                return ("Evitar esforço físico");
        }
    }
}
