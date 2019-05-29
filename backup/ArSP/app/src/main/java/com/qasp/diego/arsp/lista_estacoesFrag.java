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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.diego.arsp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 */
public class lista_estacoesFrag extends Fragment{

    public lista_estacoesFrag() {
        // Required empty public constructor
    }

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview = inflater.inflate(R.layout.fragment_lista_estacoes, container, false);
        ListView list;
        CustomListAdapter adapter;
        Log.d("lista_estacoes", "onCreateView");
        if(Global.tabSelecionado == 0) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            int prefdeExibicao = Integer.parseInt(preferences.getString("exibicao","0"));
            ArrayList<Estacao> estacoesExibicao = (ArrayList<Estacao>)Global.estacoes.clone();
            if(prefdeExibicao != 1){
                // SP + ABC
                if(prefdeExibicao == 2){
                    // Baseado na ordem definida em "EstacoesValidas"
                    estacoesExibicao.remove(10);
                    estacoesExibicao.remove(5);
                    estacoesExibicao.remove(3);
                    estacoesExibicao.remove(2);
                    estacoesExibicao.remove(0);
                }
                // SP
                else {
                    for(int i = 0; i < 12; i++){
                        estacoesExibicao.remove(0);
                    }
                }
            }
            // Ordem Alfabetica
            Collections.sort(estacoesExibicao, new Comparator<Estacao>() {
                @Override
                public int compare(final Estacao object1, final Estacao object2) {
                    return object1.getNome().compareTo(object2.getNome());
                }
            });
            // Construcao da lista na tela
            Log.d("estacoes(tamanho)", String.valueOf(Global.estacoes.size()));
            adapter = new CustomListAdapter(getActivity(), 0, estacoesExibicao);
            Log.d("lista_estacoes", "tabSelecionado");
            list = (ListView) rootview.findViewById(R.id.list);
            list.setAdapter(adapter);
        }
        return rootview;
    }

    // ENVIO PRO ADAPTER
    // adapter = new CustomListAdapter(getActivity(), 0, Global.estacoes);
    // Log.d("lista_estacoes", "tabSelecionado");
    // list = (ListView) rootview.findViewById(R.id.list);
    // list.setAdapter(adapter);

}
