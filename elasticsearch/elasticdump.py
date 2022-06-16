import sys
import csv

import pandas as pd
from elasticsearch import Elasticsearch, helpers

maxInt = sys.maxsize

while True:
    # decrease the maxInt value by factor 10
    # as long as the OverflowError occurs.
    try:
        csv.field_size_limit(maxInt)
        break
    except OverflowError:
        maxInt = int(maxInt / 10)

es = Elasticsearch("http://localhost:9200")

dataframe = pd.read_csv("./data/articles1.csv")
dataframe.to_csv("./data/articles1_mod.csv", index=False)

with open('./data/articles1_mod.csv') as f:
    reader = csv.DictReader(f)
    helpers.bulk(es, reader, index='news')
