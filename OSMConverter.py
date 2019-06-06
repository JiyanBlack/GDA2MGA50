import pyproj
source_proj = pyproj.Proj(init = 'epsg:4283')
dest_proj = pyproj.Proj(init = 'epsg:28350')

traner = pyproj.Transformer.from_proj(4283, 28350)

def process(alinestr):
    if not alinestr:
        return ""
    latstart = alinestr.find(r'lat="')+5
    if latstart == 4:
        return alinestr
    latend = alinestr.find(r'"', latstart)
    lat = alinestr[latstart:latend]
    lonstart = alinestr.find(r'lon="', latend)+5
    lonend = alinestr.find(r'"', lonstart)
    lon = alinestr[lonstart:lonend]
    x,y = traner.transform(float(lat), float(lon))
    alinestr = alinestr.replace(lat, str(x))
    alinestr = alinestr.replace(lon, str(y))
    return alinestr

import multiprocessing
from itertools import zip_longest

def grouper(n, iterable, padvalue=None):
	"""grouper(3, 'abcdefg', 'x') -->
	('a','b','c'), ('d','e','f'), ('g','x','x')"""

	return zip_longest(*[iter(iterable)]*n, fillvalue=padvalue)

if __name__ == '__main__':
    p = multiprocessing.Pool(14) 
    with open('perth-map.osm', 'r', encoding='utf8') as f:
        with open('perth-map_mga50.osm','a',encoding='utf8') as o:
            for chunk in grouper(1000, f):
                results = p.map(process, chunk)
                for r in results:
                    o.write(r) 	# replace with outfile.write()