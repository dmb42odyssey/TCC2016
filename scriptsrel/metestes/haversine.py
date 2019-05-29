#!/usr/bin/env python

import sys
from math import radians, cos, sin, asin, sqrt

if __name__ == "__main__":

    lng1 = float(sys.argv[1])
    lng2 = float(sys.argv[3])
    lat1 = float(sys.argv[2])
    lat2 = float(sys.argv[4])
    AVG_EARTH_RADIUS = 6371  # in km

    # convert all latitudes/longitudes from decimal degrees to radians
    lat1, lng1, lat2, lng2 = map(radians, (lat1, lng1, lat2, lng2))

    # calculate haversine
    lat = lat2 - lat1
    lng = lng2 - lng1
    d = sin(lat * 0.5) ** 2 + cos(lat1) * cos(lat2) * sin(lng * 0.5) ** 2
    h = 2 * AVG_EARTH_RADIUS * asin(sqrt(d))
    print h  # in kilometers
