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

import android.os.Environment;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by diego on 30/09/16.
 */
public class Atualiza {

    private static int DATA = 0;
    private static int HORAS = 1;

    // Metodos para Download FTP do arquivo.

    public static boolean DownloadFTP() throws IOException{

        final String FTPURL = "37.187.45.24";
        final String USUARIO = "diego";
        final String SENHA = "Jogador5";
        final String ARQUIVO = "data.dat";
        FTPClient ftp = new FTPClient();

        try {
            ftp.connect(FTPURL);
            ftp.login(USUARIO,SENHA);
            ftp.changeWorkingDirectory("/httpdocs/tcc/TCCpython");
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            OutputStream outputStream = null;
            boolean downloadcomsucesso = false;
            try {
                File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), ARQUIVO);
                f.createNewFile();
                outputStream = new BufferedOutputStream(new FileOutputStream(f));
                downloadcomsucesso = ftp.retrieveFile(ARQUIVO,outputStream);
            }
            finally {
                if (outputStream != null) outputStream.close();
            }
            return downloadcomsucesso;
        }
        finally {
            if (ftp != null) {
                ftp.logout();
                ftp.disconnect();
            }
        }
    }

    // Metodos para a atualizacao das estacoes

    public static void RenovaDados(ArrayList<Estacao> estacoes) throws Exception {
        // DownloadArquivo() TO DO
        try {
            InputStream in = getArquivodeDados();
            try {
                LeituraeAtualizacao(in,estacoes);
            }
            catch (Exception e){
                if(e instanceof NullPointerException)
                    throw new NullPointerException();
                else if(e instanceof IOException)
                    throw new IOException();
            }
        }
        catch (IOException e){
            throw new IOException();
        }
    }

    private static InputStream getArquivodeDados() throws IOException{

        final String NOME = "data.dat";
        File arquivo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), NOME);
        return (new FileInputStream(arquivo));
    }

    private static void LeituraeAtualizacao(InputStream in, ArrayList<Estacao> estacoes) throws Exception{

        InputStreamReader isr = new InputStreamReader(in, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        int size = estacoes.size();
        for(int e = 0; e < size; e++) {
            String line = br.readLine();
            // Detectar estacoes defeituosas
            while(!line.equals(estacoes.get(e).getId())) {
                estacoes.get(e).setIndisponivel(true);
                e++;
            }
            // Leitura e adicao dos poluentes
            line = br.readLine();
            String[] poluentes = line.split("[ ]");
            int NOxcol = -1;
            int NOcol = -1;
            int ERT = -1;
            int colcnt = 0;
            Log.d("Estacao", estacoes.get(e).getNome());
            for(String p: poluentes) {
                    Log.d("Poluentes (String p):",p);
                    if(p.equals("NO")) NOcol = colcnt + 2;
                    else if(p.equals("NOx")) NOxcol = colcnt + 2;
                    else if(p.equals("ERT")) ERT = colcnt + 2;
                    colcnt++;
            }
            // Leitura das medicoes das ultimas tres horas
            int maxlin = 3;
            boolean horamaisrecente = true;
            for(int i = 0; i < maxlin; i++){
                line = br.readLine();
                String[] cols = line.split("[ ]");
                colcnt = 0;
                String datatemp = " ";
                String horastemp = " ";
                // DATA HORA MED1 MED2 ...
                for(String c: cols) {
                    if(colcnt != NOxcol && colcnt != NOcol && colcnt != ERT) {
                        if(colcnt == DATA) datatemp = c;
                        else if(colcnt == HORAS) horastemp = c;
                        else {
                            if(c.equals("--")) { horamaisrecente = false; break;}
                            else {
                                estacoes.get(e).AtualizaPoluente(Integer.parseInt(c), PoluentesIndices.PoluenteproIndice(poluentes[colcnt - 2]));
                            }
                        }
                    }
                    if(horamaisrecente && colcnt > 1) estacoes.get(e).AtualizaTempo(datatemp,horastemp);
                    colcnt++;
                }
            }
        }
    }
}
