#!/usr/bin/env python

import csv
import sys

if __name__ == "__main__":
    f = open(sys.argv[1], 'r')
    o = open('dados.dat', 'w')
    csvf = csv.reader(f)
    cnt = 0
    for row in csvf:
        if(str(row[5]) == 'pm25'):
            o.write(str(row[6]))
            o.write('\n')
        cnt = cnt + 1
        if(cnt == 6295):
            break
    f.close()
    o.close()
