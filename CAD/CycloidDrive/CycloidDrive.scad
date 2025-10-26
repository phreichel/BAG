/*

	Modul (m):
		Maß fuer die Groeße der Zaehne, definiert als der Quotient aus
		Teilkreisdurchmesser und Zaehnezahl. m = d / z

	Zaehnezahl (z):
		Anzahl der Zaehne eines Zahnrades.

	Teilkreisdurchmesser (d):
		Der Durchmesser des Kreises, auf dem die Zahnteilung basiert.
		d = m * z

	Grundkreisdurchmesser (d_b):
		Basis für die Evolventenkurve, gegeben durch:
		d_b = d * cos(alpha) mit dem Eingriffswinkel
		alpha (typischerweise 20°).

	Fußkreisdurchmesser (d_f):
		Durchmesser des Radfußes, gegeben durch:
		d_f = d - 2.5 * m

	Kopfkreisdurchmesser (d_a):
		Maximale Hoehe der Zaehne, gegeben durch:
		d_a = d + 2 * m

	Zahnhoehe:
		h	= h_a + h_f = 2.25 * m
	
	Zahnkopfhoehe (h_a):
		h_a = m

	Zahnfusshoehe (h_f):
		h_f = 1.25 * m

	Grundkreisradius (r_b):
		r_b = d_b / 2
		
	Evolventeninkel (theta):
	
	Grundkreisradius (r_b):
		r_b = d_b / 2
	
	Exzentrizitaet:
		Offset zwischen der Rotationsachse des inneren Zahnrades
		und der Exzenterscheibe.
	
*/

function evolventen_profil(r_b,theta) = [
	r_b * (sin(theta) + theta * cos(theta)),
	r_b * (cos(theta) - theta * sin(theta))
];

module evolventen_scheibe(r_b,hoehe) {
	linear_extrude(hoehe)
	polygon( [ for (i=[0:3600]) evolventen_profil(r_b,i*.1) ] );
}

evolventen_scheibe(50.,10);