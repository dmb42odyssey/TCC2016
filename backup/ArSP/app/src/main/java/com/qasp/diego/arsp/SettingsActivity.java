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

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.diego.arsp.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#202020"));
        }

        // TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.gradientShadow).setVisibility(View.GONE);
            ViewCompat.setElevation(toolbar,8);
        }

        /* Setar ICONE */
        setSupportActionBar(toolbar);

        // Inflar frag
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new preferencesFrag())
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meumenu, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.configs:
                return true;
            case R.id.contato:
                AlertDialog.Builder bcontato = new AlertDialog.Builder(SettingsActivity.this);
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
                AlertDialog.Builder babout = new AlertDialog.Builder(SettingsActivity.this);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
