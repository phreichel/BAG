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

module axis(e) {
	color("#a08000") {
		cyl(13,6.5);
		tx(e)
		cyl(6,7.5);
		tz(5)
		tx(-e)
		cyl(6,7.5);
	}
}

rz(-(1/N)*$t*360*N)
color("#0000ff")
tz(-1)
union() {
	tz(-1)
	difference() {
		union() {
			hcyl(1,R+e,9.5);
			tz(12) hcyl(1,R+e,9.5);
			for (i=[0:5]) {				
				rz(i*(360/6))
				tx(R/3*2) {
					tz(-3) color("#00ff00") screwM3(16);
					tz(13) color("#00ff00") nutM3();
					cyl(11,2.4);
					tz(1) hcyl(4,4,2.5);
					tz(6) hcyl(4,4,2.5);
				}
			}
		}
		tz (-1)
		for (i=[0:5]) {
			rz(i*(360/6))
			tx(R/3*2) {
				cyl(25,1.5);
			}
		}
	}
	for (i=[0:5]) {				
		rz(i*(360/6))
		tx(R/3*2) {
			tz(-3) color("#00ff00") screwM3(16);
			tz(13) color("#00ff00") nutM3();
		}
	}
}

rz($t*360*N) {
	tz(-2)
	axis(e);
	tx(e)
	rz(-(1+1/N)*$t*360*N) {
		bearing_21x15x4();
		disk(R,N);
	}
}

color("#00ff00")
tz(5)
rz(180+$t*360*N)
tx(e)
rz(-(1+1/N)*$t*360*N) {
	bearing_21x15x4();
	disk(R,N);
}

color("#ff0000") {
	tz(-1)
	for (i=[0:N]) {
		r=R/N;
		rz((i*(360/(N+1))-(360/(N+1))*.25)*N)
		tx(R+e)
		cyl(11,r);
	}
	tz(-1)
	hcyl(1,R+2*e,R-e);
	tz(4)
	hcyl(1,R+2*e,R-e);
	tz(9)
	hcyl(1,R+2*e,R-e);
}