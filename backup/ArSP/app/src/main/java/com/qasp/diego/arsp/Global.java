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

import java.util.ArrayList;

public class Global {

    public static ArrayList<Estacao> estacoes = new ArrayList<>();
    public static Coordenada GPS = new Coordenada(-1.0,-1.0);
    public static boolean tenhodados = false;
    public static boolean GPSok = false;
    public static int tabSelecionado = 0;
    public static String endereco = "Endereço: ---";
}
