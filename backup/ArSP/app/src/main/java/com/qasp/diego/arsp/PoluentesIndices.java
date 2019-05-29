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

public class PoluentesIndices {

    // Categoria de qualidade dos poluente por INDICE
    public enum IndiceLimite {
        BOA("Boa",0,40),
        MEDIANA("Mediana",41,80),
        RUIM("Ruim",81,120),
        MUITORUIM("Muito Ruim",121,200),
        PESSIMA("Péssima",201,1000);

        private String qualidade;
        private int Ii;
        private int If;
        IndiceLimite(String qualidade, int Ii, int If){
            this.qualidade = qualidade;
            this.Ii = Ii;
            this.If = If;
        }
        public String qualidade() {return this.qualidade; }
        public int Ii() { return this.Ii; }
        public int If() { return this.If; }
    }

    // Conversao de do nome do poluente pro indice onde esta armazenado
    public static int PoluenteproIndice(String p){

        int SO2POSITION = 0;
        int NO2POSITION = 1;
        int COPOSITION = 2;
        int O3POSITION = 3;
        int PMFPOSITION = 4;
        int PMGPOSITION = 5;

        if(p.equals("SO2")) return SO2POSITION;
        else if(p.equals("NO2")) return NO2POSITION;
        else if(p.equals("CO")) return COPOSITION;
        else if(p.equals("O3")) return O3POSITION;
        else if(p.equals("MP2.5")) return PMFPOSITION;
        else return PMGPOSITION;
    }

    public static String IndiceproPoluente(int i){

        int SO2POSITION = 0;
        int NO2POSITION = 1;
        int COPOSITION = 2;
        int O3POSITION = 3;
        int PMFPOSITION = 4;

        if(i == SO2POSITION) return "SO2";
        else if(i == NO2POSITION) return "NO2";
        else if(i == COPOSITION) return "CO";
        else if(i == O3POSITION) return "O3";
        else if(i == PMFPOSITION) return "MP2.5";
        else return "MP10";
    }
}
