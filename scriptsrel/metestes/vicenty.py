#!/usr/bin/env python

import sys
from vincenty import vincenty

if __name__ == "__main__":

    #Tudo ok nas coordenadas.
    lam1 = float(sys.argv[1])
    lam2 = float(sys.argv[3])
    phi1 = float(sys.argv[2])
    phi2 = float(sys.argv[4])

    city1 = (phi1,lam1)
    city2 = (phi2,lam2)

    print vincenty(city1,city2)
