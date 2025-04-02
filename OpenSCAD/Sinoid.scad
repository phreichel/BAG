use <util.scad>;
use <bearings.scad>;
use <screw.scad>;

R=30;
N=29;
e=1;

function cyclo(R,N) = [
	let (r=R/N)
	for (i=[0:N-1])
	for (j=[0:360-1])
	let (a=(i+j*(1/360))*(360/N))
	[
		cos(a)*R + r * sin(j) * cos(a),
		sin(a)*R + r * sin(j) * sin(a)
	]
];

module disk(R,N) {
	C=cyclo(R,N);
	bearing_21x15x4();
	difference() {
		linear_extrude(4)
		polygon(C);
		tz(-1) {
			cyl(6,10.5);
			for (i=[0:5]) {
				rz(i*(360/6))
				tx(R/3*2)
				cyl(6,4+e);
			}
		}
	}
}

rz(-(1/N)*$t*360*N)
color("#0000ff")
tz(-1)
union() {
	for (i=[0:5]) {
		rz(i*(360/6))
		tx(R/3*2)
		hcyl(16,4,2.5);
	}
}

rz($t*360*N)
tx(e)
rz(-(1+1/N)*$t*360*N)
disk(R,N);

color("#00ff00")
tz(7)
rz(180+$t*360*N)
tx(e)
rz(-(1+1/N)*$t*360*N)
disk(R,N);

color("#ff0000")
for (i=[0:N]) {
	r=R/N;
	rz((i*(360/(N+1))-(360/(N+1))*.25)*N)
	tx(R+e)
	cyl(14,r);
}

