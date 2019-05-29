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
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.diego.arsp.R;

public class homeFrag extends Fragment {

    private String avaliacao_escolhida = null;


    public homeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RadioGroup rgroup_avaliacao;
        Button button_enviar;


        final View rootview =  inflater.inflate(R.layout.fragment_home, container, false);
        rgroup_avaliacao = (RadioGroup) rootview.findViewById(R.id.radio_avaliacao);
        rgroup_avaliacao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobom:
                        avaliacao_escolhida = "Bom";
                        break;
                    case R.id.radiook:
                        avaliacao_escolhida = "OK";
                        break;
                    case R.id.radioruim:
                        avaliacao_escolhida = "Ruim";
                        break;
                }
                Log.d("avaliacao_escolhida", avaliacao_escolhida);
            }
        });

        button_enviar = (Button) rootview.findViewById(R.id.benviar);
        button_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Usuario nao escolheu uma avaliacao. Mostrar Dialogo de Erro.
                if(avaliacao_escolhida == null){
                    AlertDialog.Builder erro = new AlertDialog.Builder(v.getContext());
                    erro.setMessage("Você deve selecionar uma Avaliação.");
                    erro.setCancelable(true);
                    erro.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alerta = erro.create();
                    alerta.show();
                }
                else {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"iqspsuporte@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "ArSP - Feedback" +  " (" + avaliacao_escolhida + ")");
                    EditText et = (EditText) rootview.findViewById(R.id.editText);
                    i.putExtra(Intent.EXTRA_TEXT   , "AVALIAÇÃO:" + avaliacao_escolhida + "\n" + "MOTIVO:" + "\n" + et.getText().toString());
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Log.d("Deu","RUim");
                    }
                }
            }
        });

        return rootview;
    }
}
