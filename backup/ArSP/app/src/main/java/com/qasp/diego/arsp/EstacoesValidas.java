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

public class EstacoesValidas {

    public enum Estacao_valida{
        // PER: CARAPICUIBA(0), GUAR(2 e 3), OSASCO(5), TABOAO(10)
        /** RMSP (11 estacoes) **/
        CARAPICUIBA("Carapicuiba","Carapicuíba","Av. Inocêncio Será" +
                "fico, esquina Rua São Miguel",-23.521993,-46.8356492728411),
        DIADEMA("Diadema","Diadema","Rua Benjamin Constant, 3 " +
                "Vila Diadema - Diadema",-23.676413,-46.611505812659296),
        GUARULHOS_PACO_MUNICIPAL("Guarulhos-Paco-Municipal","Guarulhos - Paço Municipal","Rua João Bernardo Medeiros, 173 " +
                "Bom Clima - Guarulhos",-23.446163,-46.51842623902014),
        GUARULHOS_PIMENTA("Guarulhos-Pimenta","Guarulhos - Pimenta","Rua Prof.Maria Del Pilar Muñoz " +
                "Bononato, s/no Pq.CECAP",-23.430752,-46.409849411257476),
        MAUA("Maua","Mauá","Rua Vitorino Del’Antonia, 150 " +
                "Vila Noêmia - Mauá",-23.568831,-46.46489103134976),
        OSASCO("Osasco","Osasco","Av. dos Autonomistas, s/no - " +
                "esquina c/ Rua São Maurício " +
                "Vila Quitaúna - Osasco",-23.517322,-46.79195036734028),
        SANTO_ANDRE_CAPUAVA("Santo-Andre-Capuava","Santo André - Capuava","Rua Manágua, 02 - Parque Capuava " +
                "- Santo André",-23.630359,-46.49152973801866),
        SANTO_ANDRE("Santo-Andre-Centro","Santo André - Paço Municipal","Praça IV Centenário, s/no\n" +
                "Santo André",-23.647542,-46.530808739355884),
        SAO_BERNARDO_PAULICEIA("Sao-Bernardo-Pauliceia","São Bernardo - Pauliceia","Rua Xavier de Toledo, 521 " +
                "Vila Paulicéia - São " +
                "Bernardo do Campo",-23.661896,-46.584553857699056),
        SAO_BERNARDO("Sao-Bernardo-Centro","São Bernardo - Centro","Rua dos Vianas, 625 - Bairro Baeta Neves " +
                "São Bernardo do Campo",-23.689203,-46.54612065257485),
        TABOAO("Taboao","Taboão da Serra","Praça Nicola Vivilechio, 99 " +
                "Jd. Bom Tempo - Taboão da Serra",-23.599891,-46.758168542517645),
        SAO_CAETANO("Sao-Caetano","São Caetano do Sul","Av. Presidente Kennedy, 700 " +
                "Santa Paula - São Caetano do Sul",-23.618381,-46.556318),
        /** Cidade de Sao Paulo **/
        CAPAO_REDONDO("Capao-Redondo","Capão Redondo","Estrada de Itapecerica, 5859 " +
                "Capão Redondo - São Paulo",-23.6589,-46.779915324011505),
        CERQUEIRA_CESAR("Cerqueira-Cesar","Cerqueira Cesar","Av. Dr.Arnaldo, 725 " +
                "Sumaré - São Paulo",-23.544132,-46.67258567128479),
        CONGONHAS("Congonhas","Congonhas","Al. dos Tupiniquins, 1571 - Planalto 23k 330336 " +
                "Paulista - São Paulo",-23.606885,-46.66334641655653),
        IBIRAPUERA("Ibirapuera","Ibirapuera","Parque do Ibirapuera s/no - setor 25" + " São Paulo",-23.582416,-46.660568852204136),
        USP_IPEN("Cid-Universitaria-USP-Ipen","USP - IPEN","Av. Profo Lineu Prestes, 2242" +
                " Cidade Universitária - São Paulo",-23.556926,-46.73729043155511),
        INTERLAGOS("Interlagos","Interlagos","Rua Domingas Galleteri Blota, 171" +
                " Campo Grande - São Paulo",-23.671046,-46.674922524916724),
        ITAIM("Itaim-Paulista","Itaim Paulista","Rua Jaguar, 225" +
                "Itaim Paulista - São Paulo",-23.492157,-46.42063616185953),
        ITAQUERA("Itaquera","Itaquera","Av. Fernando do Espírito Santo Alves" +
                "de Matos,1000 Parque do Carmo -" +
                "São Paulo",-23.570594,-46.46654674127396),
        MARGINAL_TIETE("Marginal-Tiete","Marginal Tietê","Av. Embaixador Mace" +
                "do Soares, 12889" +
                " Vila Leopoldina - São Paulo",-23.509309,-46.743196301658266),
        MOOCA("Mooca","Moóca","Rua Bresser, 2341 " +
                "Moóca - São Paulo",-23.540325,-46.60030273576738),
        NOSSA_SENHORA_DO_O("Nossa-Senhora-do-O","Nossa Senhora do Ò","Rua Cap.José Amaral, 80" +
                " Vila Portuguesa - São Paulo",-23.470717,-46.69193224158239),
        PARELHEIROS("Parelheiros","Parelheiros","Av.Paulo Guilguer Reimberg, 2448" +
                " Jd. Novo Horizonte - São Paulo",-23.766767,-46.696837813630786),
        PARQUE_D_PEDRO_II("Parque-D-Pedro-II","Parque Dom pedro II","Parque D.Pedro II, s/no " +
                "Centro - São Paulo",-23.535439,-46.627559792785746),
        PINHEIROS("Pinheiros","Pinheiros","Av. Prof.Frederico Hermann Jr., 345 " +
                "Alto de Pinheiros - São Paulo",-23.552046,-46.701895235991444),
        SANTANA("Santana","Santana","Av. Santos Dumont, 1019" +
                "Santana - São Paulo",-23.496601,-46.62884481435009),
        SANTO_AMARO("Santo-Amaro","Santo Amaro","Rua Padre José Maria 555, " +
                "acesso pela Rua Humboldt " +
                "Santo Amaro - São Paulo",-23.645526,-46.7098755114558);

        private String id;
        private String nome;
        private String endereco;
        private double latitude;
        private double longitude;
        Estacao_valida(String id, String nome, String endereco, double latitude, double longtitude) {
            this.id = id;
            this.nome = nome;
            this.endereco = endereco;
            this.latitude = latitude;
            this.longitude = longtitude;
        }
        public String id() { return this.id; }
        public String nome() { return this.nome; }
        public String endereco() { return this.endereco; }
        public double latitude() { return this.latitude; }
        public double longitude() { return this.longitude; }
    }
}
