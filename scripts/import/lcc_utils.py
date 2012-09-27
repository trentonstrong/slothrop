# Ladder lookup table for LC classifcation heading.
# From: http://www.docstoc.com/docs/40410096/The-Library-of-Congress-Classification-System
LCC_HEADINGS = {
	'A': 'General Works',
	'AC': 'Collected Works',
	'AE': 'Encyclopedias',
	'AG': 'Dictionaries; general reference works',
	'AY': 'Yearbooks, Almanacs, Directories',

	'B': 'Philosophy, Psychology, Religion',
	'BF': 'Psychology',
	'BL': 'Religions, Mythology, Rationalism',
	'BM': 'Judaism',
	'BP': 'Islam, Bahaism, Theosophy',
	'BQ': 'Buddhism',
	'BR': 'Christianity',
	'BS': 'The Bible',

	'C': 'Auxiliary Sciences of History',
	'CB': 'History of Civilization and Culture',
	'CC': 'Archaelogy (General)',
	'CR': 'Heraldry',
	'CS': 'Genealogy',
	'CT': 'Biography (General)',

	'D': 'History',
	'DA': 'Great Britain',
	'DAW': 'Central Europe',
	'DC': 'France',
	'DD': 'Germany',
	'DE': 'Mediterranean, Greco-Roman World',
	'DG': 'Italy',
	'DJK': 'Eastern Europe',
	'DK': 'Russian and Poland',
	'DL': 'Northern Europe and Scandinavia',
	'DP': 'Spain and Portugal',
	'DR': 'Eastern Europe and Turkey',
	'DS': 'Asia',
	'DT': 'Africa',
	'DU': 'Oceania, Australia, New Zealand',

	'E': 'History: America and United States',

	'F': 'History: America and United States',

	'G': 'Geography, Anthropology',
	'GB': 'Physical Geography',
	'GE': 'Environmental Sciences',
	'GN': 'Anthropology, Ethnology, Ethnography',
	'GR': 'Folklore',
	'GT': 'Manners and Customs',
	'GV': 'Sports and Recreation',

	'H': 'Social Sciences',
	'HA': 'Statistics',
	'HB': 'Economic Theory',
	'HM': 'Sociology',
	'HN': 'Social History',
	'HT': 'Communities, Classes, Races',
	'HV': 'Social Pathology, Criminology, Welfare',
	'HX': 'Communism, Socialism, Anarchism',

	'J': 'Political Science',
	'JA': 'Collections and General Works',
	'JC': 'Political Theory',
	'JK': 'Consitutional History and Administration',
	'JS': 'Local Government',
	'JV': 'Colonization, Emigration, and Immigration',
	'JX': 'International Law, International Relations',

	'K': 'Law',
	'KF': 'United States Law',

	'L': 'Education',

	'M': 'Music',
	'ML': 'Literature of Music',
	'MT': 'Music Instruction and Study',

	'N': 'Fine Arts',
	'NA': 'Architecture',
	'NB': 'Sculpture',
	'NC': 'Graphic Arts (Drawing, Design, Illustration)',
	'ND': 'Painting',
	'NK': 'Decorative and Applied Arts',
	'NX': 'Arts in General',

	'P': 'Language and Literature',
	'PB': 'Celtic & Modern European Languages',
	'PC': 'Romance Languages',
	'PD': 'Old Germanic Scandinavian Languages',
	'PE': 'English Language',
	'PF': 'Dutch, Flemish, and German Languages',
	'PG': 'Slavic Languages and Literature',
	'PJ': 'Oriental Languages',
	'PK': 'Oriental Languages',
	'PL': 'Oriental Languages',
	'PM': 'American Indian and Artifical Languages',
	'PN': 'Literature, Literary History and Collections',
	'PQ': 'Romance Literature',
	'PR': 'English Literature',
	'PS': 'American Literature',
	'PT': 'German Literature',

	'Q': 'Science',
	'QA': 'Mathematics',
	'QC': 'Physics',
	'QD': 'Chemistry',
	'QH': 'Natural History, Biology',
	'QM': 'Human Anatomy',
	'QP': 'Physiology',
	'QR': 'Microbiology',

	'R': 'Medicine',
	'RJ': 'Pediatrics',
	'RT': 'Nursing',

	'S': 'Agriculture',

	'T': 'Technology and Engineering',
	'TP': 'Chemical Technology',
	'TR': 'Photography',

	'U': 'Military Science',

	'V': 'Naval Science',

	'Z': 'Books, Library Science'
}


def get_lcc_subject(lcc):
	pieces = filter(
		None,
		[LCC_HEADINGS.get(lcc[0:(idx + 1)]) for idx in xrange(len(lcc))])

	if len(pieces) == 0:
		raise Exception('Unknown LCC heading: %s' % lcc) 

	return ' -- '.join(pieces)