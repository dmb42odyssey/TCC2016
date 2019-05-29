#!/bin/bash
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
SHELL=/bin/sh
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin

echo "... serverDataUpdate - Comecando ..."
echo "... serverDataUpdate - Pegando Informacao ..."
./fetchData.py
retfetch=$?
i=1
while [ $retfetch -ne 0 -a $i -le 3 ]; do
    echo "... serverDataUpdate - Falhou! Tentando Novamente ($i de 3)..."
    let i=$i+1
    ./fetchData.py
    retfetch=$?
    continue
done
echo "... serverDataUpdate - Informacao pega! ... "
i=1
echo "... serverDataUpdate - Transferindo via FTP ..."
./ftp.py
retftp=$?
while [ $retftp -ne 0 -a $i -le 5 ]; do
    echo "... serverDataUpdate - Falhou! Tentando Novamente ($i de 5)..."
    let i=$i+1
    ./ftp.py
    retftp=$?
    continue
done
echo "... serverDataUpdate - Transferido com sucesso! ..."
echo "... serverDataUpdate - Encerrando ..."
