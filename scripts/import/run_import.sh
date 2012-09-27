#!/bin/bash

wget http://www.gutenberg.org/feeds/catalog.rdf.zip && unzip -o catalog.rdf.zip
rm catalog.rdf.zip

python import_catalog.py catalog.rdf
