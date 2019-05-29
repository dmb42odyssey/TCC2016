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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.diego.arsp.R;
import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Estacao> {

    public static class ViewHolder {
        private ImageView status;
        private TextView nomedaestacao;
        private TextView indiceres;
        private TextView qualidaderes;
        private TextView endereco;
    }

    public CustomListAdapter(Activity context, int textViewResourceId, ArrayList<Estacao> estacoes) {
        super(context, textViewResourceId, estacoes);
    }

    public View getView(int position,View convertView,ViewGroup parent) {

        ViewHolder viewholder;

        if(convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.list_view,parent,false);
            viewholder = new ViewHolder();
            viewholder.status = (ImageView) convertView.findViewById(R.id.icon);
            viewholder.nomedaestacao = (TextView) convertView.findViewById(R.id.Itemname);
            viewholder.indiceres = (TextView) convertView.findViewById(R.id.indres);
            viewholder.endereco = (TextView) convertView.findViewById(R.id.des);
            viewholder.qualidaderes = (TextView) convertView.findViewById(R.id.quares);
            convertView.setTag(viewholder);
        }
        else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        Estacao item = getItem(position);
        if(item != null) {
            viewholder.status.setImageResource(item.ImagemdoPiorIndice());
            viewholder.nomedaestacao.setText(item.getNome());
            // Estação funcionando e dados da estações obtidos.
            if(item.getPosdopiorindice() != -1) {
                int indice = item.getPoluentes().get(item.getPosdopiorindice()).getIndice();
                viewholder.indiceres.setText(String.valueOf(indice));
                viewholder.qualidaderes.setText(item.getPoluentes().get(item.getPosdopiorindice()).getQualidade());
            }
            // Nao houve ainda a obtenção de dados ou a estação não está funcionando.
            else {
                viewholder.indiceres.setText("---");
                viewholder.qualidaderes.setText("---");
            }
            viewholder.nomedaestacao.setTextColor(Color.parseColor(item.getCor_do_texto()));
            viewholder.endereco.setText(item.getEndereco());
        }

        return convertView;
    }
}
