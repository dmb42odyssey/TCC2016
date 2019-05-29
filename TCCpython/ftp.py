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
from ftplib import FTP
import sys

if __name__ == "__main__":
    try:
        ftp = FTP('37.187.45.24')   # IP
        ftp.login('diego','Jogador5') # LOGIN|SENHA                      
        ftp.cwd('/httpdocs/tcc/TCCpython')
        ftp.storbinary('STOR data.dat', open('data.dat', 'rb'))
        ftp.close()
        print("Sucesso")
    except:
        print ("ERRO!")
        sys.exit(1)
    sys.exit(0)
