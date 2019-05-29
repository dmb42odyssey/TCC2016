#!/usr/bin/python
# -*- coding: UTF-8 -*-
# Copyright 2016 Diego Martos Buoro
# This file is part of QASP.
#
# QASP is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# QASP is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with QASP.  If not, see <http://www.gnu.org/licenses/>.

from pyvirtualdisplay import Display
from selenium import webdriver
from bs4 import BeautifulSoup
from shutil import copyfile
import time
from collections import OrderedDict
import urllib
import sys

def initializeSource (namevaluelist):
    namevaluelist['Carapicuiba'] = 263
    namevaluelist['Diadema'] = 92
    namevaluelist['Guarulhos-Paco-Municipal'] = 264
    namevaluelist['Guarulhos-Pimenta'] = 279
    namevaluelist['Maua'] = 65
    namevaluelist['Osasco'] = 120
    namevaluelist['Santo-Andre-Capuava'] = 100
    namevaluelist['Santo-Andre-Centro'] = 254
    namevaluelist['Sao-Bernardo-Pauliceia'] = 102
    namevaluelist['Sao-Bernardo-Centro'] = 272
    namevaluelist['Taboao'] = 103
    namevaluelist['Sao-Caetano'] = 86
    # Cidade de Sao Paulo
    namevaluelist['Capao-Redondo'] = 269
    namevaluelist['Cerqueira-Cesar'] = 91
    namevaluelist['Congonhas'] = 73
    namevaluelist['Ibirapuera'] = 83
    namevaluelist['Cid-Universitaria-USP-Ipen'] = 95
    namevaluelist['Interlagos'] = 262
    namevaluelist['Itaim-Paulista'] = 266
    namevaluelist['Itaquera'] = 97
    namevaluelist['Marginal-Tiete'] = 270
    namevaluelist['Mooca'] = 85
    namevaluelist['Nossa-Senhora-do-O'] = 96
    namevaluelist['Parelheiros'] = 98
    namevaluelist['Parque-D-Pedro-II'] = 72
    namevaluelist['Pinheiros'] = 99
    namevaluelist['Santana'] = 63
    namevaluelist['Santo-Amaro'] = 64

if __name__ == "__main__":

    display = Display(visible=0, size=(800, 600))
    display.start()

    url_home = "http://qualar.cetesb.sp.gov.br/qualar/home.do"
    url_temporeal = "http://qualar.cetesb.sp.gov.br/qualar/conQualidadeArTempoReal.do?method=gerarRelatorio"
    url_pesuisa = "http://qualar.cetesb.sp.gov.br/qualar/conQualidadeArTempoReal.do?method=pesquisarInit"
    url_imprime = "http://qualar.cetesb.sp.gov.br/qualar/conQualidadeArTempoReal.do?method=executarImprimir"

    url2_pesquisa = "http://qualar.cetesb.sp.gov.br/qualar/conDadosHorarios.do?method=pesquisarInit"
    url2_imprime = "http://qualar.cetesb.sp.gov.br/qualar/conDadosHorarios.do?method=executarImprimir"

    profile = webdriver.FirefoxProfile()
    profile.set_preference("xpinstall.signatures.required", False);
    driver = webdriver.Firefox(profile)

    driver.get(url_home)
    driver.add_cookie({'name': 'foo', 'value': 'bar'})
    driver.find_element_by_name("cetesb_login").send_keys("jklports@gmail.com")
    driver.find_element_by_name("cetesb_password").send_keys("pokemon")
    driver.find_element_by_name("enviar").click()

    # PARTE 2

    namevaluelist = OrderedDict()
    initializeSource(namevaluelist)
    ERRODOSERVIDOR = 1

    # Crio um arquivo por estacao? Nah
    f = open('temp.dat','w')
    for k,v in namevaluelist.items():
        # Configura as opcoes de pesquisas
        print str(k),
        driver.get(url2_pesquisa)
        driver.find_element_by_xpath("//input[@name='nestcasMontoSelecionadas' and @value='" + str(v) + "']").click()
        driver.find_element_by_name("btnConsultar").click()


        # Salvo o HTML da pagina com os dados que abriu por pop up
        old_handle = driver.window_handles[0]
        time.sleep(5)
        # TO DO: Pular estacoes defeituosas
        if(len(driver.window_handles) > 1):
            driver.switch_to_window(driver.window_handles[1])
        else:
            print ("INDISPONIVEL")
            time.sleep(2)
            alert = driver.switch_to.alert
            if(alert.text.encode('utf-8') == "Dados indisponíveis"):
                alert.accept()
                continue
            else:
                ERRODOSERVIDOR = 0
                print ("Interrompendo...")
                break
            #try:
            #    alert = driver.switchTo().alert();
            #    alert.accept();
            #    continue;
            #except:
            #    print("Erro: nao achei o Alerta!")

        f.write(k.encode('utf-8') + '\n')
        content = driver.page_source
        driver.close()
        driver.switch_to_window(old_handle)

        #Configuro paramentros para Salvar no arquivo
        cnt = -21 #Offset
        size = 0
        readline = 2

        # SAlvo os Poluentes medidos, e sua quantidade
        tokens = []
        soup = BeautifulSoup(content,"html.parser")
        for th in soup.find_all('th', { 'colspan' : '3'}):
            tokens = th.text.split()
            f.write(tokens[0].encode('utf-8') + ' ')
            size = size + 1
        f.write("\n")

        # Pegar data, horario, e os indices.
        col = 3*size
        for td in soup.find_all('td'):
            cnt = cnt + 1
            if (cnt >= 0):
                # Checando se data&horario ou Indice
                if(cnt != col and (cnt % 3 ) == 0):
                    # Caso seja data e horario, remover espacos desnecessarios
                    if(cnt == 0 and readline >= 23): f.write(td.text.strip().encode('utf-8') + ' ')
                    elif(readline >= 23): f.write(td.text.encode('utf-8') + ' ')
                elif cnt == col:
                    if(readline >= 23): f.write(td.text.encode('utf-8') + '\n')
                    readline = readline + 1
                    cnt = -1
        tokens[:] = []
        print ("OK")
    f.close()
    driver.quit()
    display.stop()
    if(ERRODOSERVIDOR == 1):
        copyfile("temp.dat","data.dat")
        sys.exit(0)
    else: sys.exit(1)
