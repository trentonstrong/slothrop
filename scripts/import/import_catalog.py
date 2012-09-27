#! /usr/bin/env python

import sys
import logging
import json
from datetime import datetime
from lxml import etree
from lcc_utils import get_lcc_subject

XML_NAMESPACES = {
	"rdf": "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
	"rdfs":"http://www.w3.org/2000/01/rdf-schema#",
	"xsd": "http://www.w3.org/2001/XMLSchema#",
	"dc": "http://purl.org/dc/elements/1.1/",
	"dcterms": "http://purl.org/dc/terms/",
	"dcmitype": "http://purl.org/dc/dcmitype/",
	"cc": "http://web.resource.org/cc/",
	"pgterms": "http://www.gutenberg.org/rdfterms/",
	"xml:base": "http://www.gutenberg.org/feeds/catalog.rdf"
}

def get_subjects(etext):
	"""
	Returns subjects as a list of plaintext
	Resolves any special subject encodings such as Library of Congress Classification
	"""
	# LCSH headings are plaintext already
	subjects = etext.xpath(".//dcterms:LCSH//rdf:value/text()", namespaces=XML_NAMESPACES)
	# LCC requires a lookup
	for lcc in etext.xpath(".//dcterms:LCC/rdf:value/text()", namespaces=XML_NAMESPACES):
		subjects.append(get_lcc_subject(lcc))

	return subjects

def _first(l):
	return (l or [None])[0]


def main(args):
	if len(args) != 1:
		raise Exception("Expected a single argument: path to catalog.rdf")

	catalog_path = args[0]
	catalog = open(catalog_path)
	catalog_tree = etree.parse(catalog)
	rdf_root = catalog_tree.getroot()
	
	logging.info("Starting etext processing...")
	etexts = {}
	for etext in rdf_root.iterfind('{http://www.gutenberg.org/rdfterms/}etext'):
		etext_id = _first(etext.values())
		etexts[etext_id] = {
			'publisher': _first(etext.xpath('./dc:publisher/text()',
			 namespaces=XML_NAMESPACES)),
			'creator': _first(etext.xpath('./dc:creator/text()',
			 namespaces=XML_NAMESPACES)),
			'title': _first(etext.xpath('./dc:title/text()',
			 namespaces=XML_NAMESPACES)),
			'friendly_title': _first(etext.xpath('./pgterms:friendlyTitle/text()',
			 namespaces=XML_NAMESPACES)),
			'language':_first(etext.xpath('./dc:language//rdf:value/text()',
			 namespaces=XML_NAMESPACES)),
			'subjects': get_subjects(etext),
			'created': _first(etext.xpath('./dc:created//rdf:value/text()',
			 namespaces=XML_NAMESPACES)),
			'pg_downloads': int(_first(etext.xpath('./pgterms:downloads//rdf:value/text()',
			 namespaces=XML_NAMESPACES))),
			'files': []
		}
	logging.info("Processed %d etexts" % len(etexts))
	
	logging.info("Starting file format processing")
	for file_format in rdf_root.iterfind('{http://www.gutenberg.org/rdfterms/}file'):
		etext_id = _first(file_format.xpath('./dcterms:isFormatOf/@rdf:resource',
		 namespaces=XML_NAMESPACES))[1:]
		etexts[etext_id]['files'].append({
			'file': _first(file_format.values()),
			'formats': file_format.xpath('./dc:format//rdf:value/text()',
				namespaces=XML_NAMESPACES),
			'size': int(_first(file_format.xpath('./dcterms:extent/text()',
				namespaces=XML_NAMESPACES))),
			'modified': _first(file_format.xpath('./dcterms:modified//rdf:value/text()',
				namespaces=XML_NAMESPACES))
		})

	out_name = "dump_%s.json" % datetime.utcnow().strftime('%s')
	logging.info("Dumping JSON to %s" % out_name)
	out = open(out_name, 'w')
	json.dump(etexts, out)


if __name__ == "__main__":
	logging.basicConfig(level=logging.INFO)
	sys.exit(main(sys.argv[1:]))