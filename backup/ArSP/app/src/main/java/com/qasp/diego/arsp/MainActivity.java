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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.diego.arsp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements RespostadoAsync{

    ViewPager viewPager;
    PagerAdapter adapter;
    TabLayout tablayout;
    static final String ESTADO_ESTACOES = "estacoesEstado";
    static final String ESTADO_GPS = "GPSEstado";
    private static final int WR = 2;
    private static final int GPS_ACESS = 1;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // Activity was brought to front and not created,
            // Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }

        // Opcoes padrao para Preferencias.
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        setContentView(R.layout.activity_main);

        // Salva dados se nao for mais visivel
        if(savedInstanceState != null) {
            Global.estacoes = (ArrayList<Estacao>)savedInstanceState.getSerializable(ESTADO_ESTACOES);
            Global.GPS = (Coordenada) savedInstanceState.getSerializable(ESTADO_GPS);
        }
        else InicializaEstacoesValidas(Global.estacoes);

        // Colorização da barra de serviços.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#202020"));
        }

        // PERMISSOES
        verifyStoragePermissions(this);
        if(PermissaodoGPS(this)) {
            // Se a permissão ja foi aprovada anterioremente.
            Intent intent = new Intent(this, LocalizacaoGPSService.class);
            startService(intent);
        }

        // Action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TABS.
        tablayout = (TabLayout) findViewById(R.id.tab_layout);
        tablayout.addTab(tablayout.newTab().setText("Lista Estações"));
        tablayout.addTab(tablayout.newTab().setText("Indice Local"));
        tablayout.addTab(tablayout.newTab().setText("Feedback"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // PAGER (Deslize) e Funcionamento das Tabs
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Notificar eventuais mudanças ao mudar de aba.
                Global.tabSelecionado = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // vazio de proposito
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Vazio de proposito
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meumenu, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putSerializable(ESTADO_ESTACOES,Global.estacoes);
        savedInstanceState.putSerializable(ESTADO_GPS, Global.GPS);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.configs:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.contato:
                AlertDialog.Builder bcontato = new AlertDialog.Builder(MainActivity.this);
                bcontato.setTitle("Informações para contato");
                bcontato.setMessage("Envie um e-mail para:" + "\n\n" + "diego.buoro@usp.br" + "\n\n" + "com" + " " + " '[IQSP]'" + " " + "no título");
                bcontato.setCancelable(true);
                bcontato.setNeutralButton("OK",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alerta_contato = bcontato.create();
                alerta_contato.show();
                return true;
            case R.id.about:
                AlertDialog.Builder babout = new AlertDialog.Builder(MainActivity.this);
                babout.setTitle("Sobre o programa");
                babout.setMessage("O IQSP é um programa para a exibição da qualidade do ar nas estações, e na obtenção da qualidade no local do usuário."
                + "\n\n" + "- Use o ícone da seta para baixo para atualizar as estações (Necessita de Internet)." +"\n" + "- Indice local é calculado assim que a aba é selecionada (Necessita GPS)." + "\n\n" + "Versão 1.0");
                babout.setCancelable(true);
                babout.setNeutralButton("OK",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alerta_about = babout.create();
                alerta_about.show();
                return true;
            case R.id.action_update:
                if(PermissaodeEscrita(this))
                    atualizarClick();
                else {
                    AlertDialog.Builder bPERERR = new AlertDialog.Builder(MainActivity.this);
                    bPERERR.setTitle("Sem permissão");
                    bPERERR.setMessage("Sem permissão para escrita dos dados, atualização impossível. Deseja permitir?");
                    bPERERR.setCancelable(true);
                    bPERERR.setPositiveButton("Sim",  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            verifyStoragePermissions(MainActivity.this);
                        }
                    });
                    bPERERR.setNegativeButton("Não",  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alerta_permissao = bPERERR.create();
                    alerta_permissao.show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Metodos

    // TO DO
    /**
    public boolean InternetDisponivel(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo infodainternetativa = cm.getActiveNetworkInfo();
        return infodainternetativa != null && infodainternetativa.isConnected();
    } **/

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int WRpermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(WRpermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    activity,
                    new String [] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WR
            );
        }
    }

    public void InicializaEstacoesValidas(List<Estacao> estacoes) {

        for(EstacoesValidas.Estacao_valida e: EstacoesValidas.Estacao_valida.values()) {
            estacoes.add(new Estacao(e.id(),e.nome(),new Coordenada(e.latitude(),e.longitude()), e.endereco(),-1, new ArrayList<Poluente>(), "#afe6e6"));
            Log.d("Objeto Criado:",e.nome());
        }
        for(Estacao e: estacoes){
            InicializaPoluentes(e);
        }
    }

    public void InicializaPoluentes(Estacao e) {

        e.getPoluentes().add(new Poluente("SO2"));
        e.getPoluentes().add(new Poluente("NO2"));
        e.getPoluentes().add(new Poluente("CO"));
        e.getPoluentes().add(new Poluente("O3"));
        e.getPoluentes().add(new Poluente("MP2.5"));
        e.getPoluentes().add(new Poluente("MP10"));
    }

    // home.activity

    public void atualizarClick(){
        new DownloadArquivoAsync(this,MainActivity.this).execute("");
    }

    public class DownloadArquivoAsync extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        public RespostadoAsync delegate = null;
        public String mensagem = "Atualizando medições. Aguarde...";

        public DownloadArquivoAsync(RespostadoAsync delegate, Context context){
            this.progressDialog = new ProgressDialog(context);
            this.delegate = delegate;
        }

        @Override
        protected String doInBackground(String... params){
            try {
                Atualiza.DownloadFTP();
                return("OK");
            }
            catch (Exception e){
                e.printStackTrace();
                return("FALHOU");
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            delegate.processoTerminado(result);
            if(result.equals("OK")) {
                Global.tenhodados = true;
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "data.dat");
                if(f != null) {
                    try {
                        Atualiza.RenovaDados(Global.estacoes);
                        AtualizaEstacoes(Global.estacoes);
                        viewPager.getAdapter().notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Dados obtidos com sucesso.", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Arquivo corrompido. Tente novamente.", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else {
                Toast.makeText(MainActivity.this, "Falha na obtenção do arquivo de dados. Tente novamente.", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage(mensagem);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void processoTerminado(String output){

    }

    public void AtualizaEstacoes(List<Estacao> estacoes){

        Log.d("Em ","AtualizaEstacoes");
        for(Estacao e: estacoes) {
            if(!e.getIndisponivel()) {
                for (Poluente p : e.getPoluentes()) {
                    p.DeterminaQualidade();
                    p.AtualizaImagem(Imagens.getFontedasImagens());
                }
                // Pior caso para estacao
                e.AtualizaEstacao();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],@NonNull int[] grantResults) {
        switch (requestCode) {
            case GPS_ACESS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSAO CONCEDIDA
                    Intent intent = new Intent(this, LocalizacaoGPSService.class);
                    startService(intent);
                }
            }
            default: {
                // Nada a fazer
            }
        }
    }

    public boolean PermissaodeEscrita(Activity activity){
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean PermissaodoGPS(Activity activity){
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}