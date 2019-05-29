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


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diego.arsp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class indice_localFrag extends Fragment {

    private static final int GPS_ACESS = 1;


    class Medicao {
        double valor;
        double distancia;
        int e;

        Medicao(double v, int i, double d){
            this.valor = v;
            this.e = i;
            this.distancia = d;
        }
    }

    public indice_localFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        Coordenada GPSLocal = Global.GPS;
        ArrayList<Poluente> poluenteslocal = new ArrayList<>();
        final View rootview = inflater.inflate(R.layout.fragment_indice_local, container, false);
        Log.d("indice_localfrag", "OnCreateView");
        Log.d(String.valueOf(Global.GPS.getLatitude()), String.valueOf(Global.GPS.getLongitude()));

        if(Global.tabSelecionado == 1) {
            if (Global.tenhodados && PermissaodoGPS(getActivity())) {
                if (Global.GPSok) {
                    DistanciasEstacaoGPS(Global.estacoes, GPSLocal);
                    DeterminarPiorMedia(Global.estacoes, poluenteslocal);

                    for (Poluente p : poluenteslocal) {
                        Log.d(p.getNome(), String.valueOf(p.getIndice()));
                    }

                    for (int i = poluenteslocal.size() - 1; i > 0 && poluenteslocal.get(i).getQualidade().equals("Boa"); i--)
                        poluenteslocal.remove(i);

                    for (Poluente p : poluenteslocal) {
                        p.AtualizaImagem(Imagens.getFontedasImagens());
                    }

                    ImageView imagemindice = (ImageView) rootview.findViewById(R.id.indicelocal);
                    imagemindice.setImageResource(poluenteslocal.get(0).getImagem());
                    TextView indiceresultado = (TextView) rootview.findViewById(R.id.resutladodoindice);
                    indiceresultado.setText(String.valueOf(poluenteslocal.get(0).getIndice()));
                    TextView qualidaderesultado = (TextView) rootview.findViewById(R.id.resultadodaQualidade);
                    qualidaderesultado.setText(String.valueOf(poluenteslocal.get(0).getQualidade()));
                    TextView prevencao = (TextView) rootview.findViewById(R.id.prevencao);
                    prevencao.setText(Prevencao.TextoDePrevencao(getActivity(), poluenteslocal.get(0)));
                    TextView local = (TextView) rootview.findViewById(R.id.local);
                    local.setText(EncontraLocalizacao());

                    Log.d("indice_localfrag", "TabSelecionado");

                    //DEBUG
                    Log.d("Latitude: ", String.valueOf(GPSLocal.getLatitude()));
                    Log.d("Longitude: ", String.valueOf(GPSLocal.getLongitude()));
                    for (Estacao e : Global.estacoes) {
                        Log.d(e.getId(), String.valueOf(e.getDistanciaparaGPS()));
                    }
                    for (Poluente p : poluenteslocal) {
                        Log.d(p.getNome(), String.valueOf(p.getIndice()));
                    }
                } else {
                    // Leitura do GPS ainda não foi feita.
                    AlertDialog.Builder aviso = new AlertDialog.Builder(rootview.getContext());
                    aviso.setMessage("Leitura do GPS ainda não foi feita. Tente novamente saindo e voltando para essa aba.");
                    aviso.setCancelable(true);
                    aviso.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alerta = aviso.create();
                    alerta.show();
                }
            }
            else if(!PermissaodoGPS(getActivity())){
                // Checo se eh uma issue de permissao e pergunto se quer ativar.
                AlertDialog.Builder bPERERR = new AlertDialog.Builder(getActivity());
                bPERERR.setTitle("Sem permissão");
                bPERERR.setMessage("Sem permissão no acesso ao GPS, atualização impossível. Deseja permitir? \n(Saia e volte para essa aba para atualizar.)");
                bPERERR.setCancelable(true);
                bPERERR.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        verifyGPSpermission(getActivity());
                    }
                });
                bPERERR.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alerta_permissao = bPERERR.create();
                alerta_permissao.show();
            }
        }
        return rootview;
    }

    public String EncontraLocalizacao(){

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(Global.GPS.getLatitude(), Global.GPS.getLongitude(), 1);
            try {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                Global.endereco = "Endereço: " + address + " - " + city;
            }
            catch (IndexOutOfBoundsException e){
                if(Global.endereco.equals(" "))
                    return ("Endereço: Falha em obter o endereço do local");
            }
        }
        catch (IOException e){
            if(Global.endereco.equals(" "))
                return ("Endereço: Falha em obter o endereço do local");
        }
        return Global.endereco;
    }

    public void DistanciasEstacaoGPS(ArrayList<Estacao> estacoes, Coordenada GPS){

        Log.d("CAlculando Haversine:"," Comecei");
        for(Estacao e: estacoes) {
            if(!e.getIndisponivel()) {
                e.setDistanciaparaGPS(FormuladHaversine(GPS.getLatitude(),GPS.getLongitude(),e.getCoordenada().getLatitude(),e.getCoordenada().getLongitude()));
            }
        }
        Log.d("Calculando Haversine:"," Acabei");
    }

    // OK!
    public double FormuladHaversine(double lat1, double long1, double lat2, double long2){

        final double RAIO_DA_TERRA = 6371; // em Km
        // Conversão
        double radlat1 = Math.toRadians(lat1);
        double radlong1 = Math.toRadians(long1);
        double radlat2 = Math.toRadians(lat2);
        double radlong2 = Math.toRadians(long2);
        //Parametros TO DO: BigDecimal?
        double radlat = radlat2 - radlat1;
        double radlong = radlong2 - radlong1;
        // Formula
        double d = Math.pow(Math.sin(radlat * 0.5),2) + Math.cos(radlat1)*Math.cos(radlat2)*Math.pow(Math.sin(radlong*0.5),2);
        return 2 * RAIO_DA_TERRA * Math.asin(Math.sqrt(d));
    }

    public void DeterminarPiorMedia(ArrayList<Estacao> estacoes, ArrayList<Poluente> poluenteslocal){


        final int POLUENTES_SIZE = 6;
        for(int p = 0; p < POLUENTES_SIZE; p++) {
            poluenteslocal.add(new Poluente(PoluentesIndices.IndiceproPoluente(p), CalculaIndiceLocal(estacoes,p)));
            poluenteslocal.get(p).DeterminaQualidade();
        }
        Collections.sort(poluenteslocal);
    }

    public int CalculaIndiceLocal(ArrayList<Estacao> estacoes, int poluente) {

        final double smoothing = 2.0;
        double Indice;

        ArrayList<Medicao> estacoescompoluente= new ArrayList<>();

        // 1) Incluir apenas as estacoes que de fato medem o poluente dado
        for(int e = 0; e < estacoes.size(); e++)
            if(estacoes.get(e).getPoluentes().get(poluente).getIndice() != -1)
                estacoescompoluente.add(new Medicao(estacoes.get(e).getPoluentes().get(poluente).getIndice(),e,estacoes.get(e).getDistanciaparaGPS()));

        // 2) IWD pro real local do usuario
        Indice = IWD(estacoescompoluente,smoothing);
        return (int) Indice;
    }

    public double IWD(ArrayList<Medicao> esperado, double p){

        double numerador = 0.0;
        double denominador = 0.0;
        for (Medicao m : esperado) {
            numerador += ((m.valor) * (w(m, p)));
            denominador += w(m, p);
        }
        return numerador/denominador;
    }

    public double d(Medicao m){
        return m.distancia;
    }
    public double w(Medicao m, double p){
        double valor;
        try {
            valor = 1.0/Math.pow(d(m),p);
        }
        catch (ArithmeticException e){ // Divisao por 0. (Rarissimo)
            valor = 1.0/Math.pow(Double.MIN_VALUE,p);
        }
        return valor;
    }

    public static void verifyGPSpermission(Activity activity){

        int GPSpermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if(GPSpermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    activity,
                    new String [] {Manifest.permission.ACCESS_FINE_LOCATION},
                    GPS_ACESS
            );
        }
        else {
            Intent intent = new Intent(activity, LocalizacaoGPSService.class);
            activity.startService(intent);
        }

    }

    public boolean PermissaodoGPS(Activity activity){
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
